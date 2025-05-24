package com.library.management;

import com.library.management.dto.BookDTO;
import com.library.management.exception.ConflictException;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import com.library.management.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link BookServiceImpl}, which handles book operations in the library management system.
 * <p>
 * This test class uses Mockito and JUnit 5 to mock dependencies and test the behavior of the service methods,
 * ensuring correctness and robustness.
 * </p>
 * <p>
 * Note: Allows multiple copies of the same book distinguished by version.
 *
 * @author Chandru
 * @version 1.1
 * @since 2025-05-19
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private BookServiceImpl bookService;

    private BookDTO bookDTO;
    private Book book;

    /**
     * Setup test data before each test case.
     */
    @BeforeEach
    void setUp() {
        bookDTO = new BookDTO();
        bookDTO.setIsbnNo("978-3-16-148410-0");
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor("Test Author");
        bookDTO.setVersion(1);

        book = new Book();
        book.setId(1L);
        book.setIsbnNo("978-3-16-148410-0");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setVersion(1);
    }

    /**
     * Test adding the first copy of a new book successfully.
     */
    @Test
    @DisplayName("Should add the first copy of a new book successfully")
    void shouldAddFirstCopySuccessfully() throws Exception {
        when(bookRepository.findByIsbnNo(anyString()))
                .thenReturn(List.of()); // No copies exist yet
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.addBook(bookDTO);

        verify(bookRepository, times(1)).findByIsbnNo(bookDTO.getIsbnNo());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    /**
     * Test adding a new copy of an existing book with the same ISBN, title, and author.
     */
    @Test
    @DisplayName("Should add new copy of an existing book by incrementing version")
    void shouldAddNewCopyWithIncrementedVersion() throws Exception {
        Book existingCopy = new Book();
        existingCopy.setId(2L);
        existingCopy.setIsbnNo(bookDTO.getIsbnNo());
        existingCopy.setTitle(bookDTO.getTitle());
        existingCopy.setAuthor(bookDTO.getAuthor());
        existingCopy.setVersion(1);

        when(bookRepository.findByIsbnNo(bookDTO.getIsbnNo()))
                .thenReturn(List.of(existingCopy));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        bookService.addBook(bookDTO);

        verify(bookRepository, times(1)).findByIsbnNo(bookDTO.getIsbnNo());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    /**
     * Test adding a book with the same ISBN but different title or author throws ConflictException.
     */
    @Test
    @DisplayName("Should throw ConflictException if ISBN matches but title or author differs")
    void shouldThrowConflictExceptionForMismatchedBookDetails() {
        Book mismatchedBook = new Book();
        mismatchedBook.setId(3L);
        mismatchedBook.setIsbnNo(bookDTO.getIsbnNo());
        mismatchedBook.setTitle("Different Title");
        mismatchedBook.setAuthor(bookDTO.getAuthor());
        mismatchedBook.setVersion(1);

        when(bookRepository.findByIsbnNo(bookDTO.getIsbnNo()))
                .thenReturn(List.of(mismatchedBook));

        ConflictException ex = assertThrows(ConflictException.class, () -> bookService.addBook(bookDTO));
        assertEquals("Book with same ISBN must have same title and author", ex.getMessage());

        verify(bookRepository, times(1)).findByIsbnNo(bookDTO.getIsbnNo());
        verify(bookRepository, never()).save(any(Book.class));
    }

    /**
     * Test retrieving a paginated list of books.
     */
    @Test
    @DisplayName("Should get books with pagination")
    void shouldGetBooksWithPagination() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Book> bookList = Arrays.asList(book);
        Page<Book> bookPage = new PageImpl<>(bookList, pageRequest, bookList.size());

        when(bookRepository.findAll(pageRequest)).thenReturn(bookPage);

        List<BookDTO> result = bookService.getBooks(pageRequest);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookDTO.getIsbnNo(), result.get(0).getIsbnNo());
        assertEquals(bookDTO.getTitle(), result.get(0).getTitle());
        assertEquals(bookDTO.getAuthor(), result.get(0).getAuthor());
        verify(bookRepository, times(1)).findAll(pageRequest);
    }

    /**
     * Test scenario when no books are found in the repository.
     */
    @Test
    @DisplayName("Should return empty list when no books found")
    void shouldReturnEmptyListWhenNoBooksFound() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> emptyPage = new PageImpl<>(List.of(), pageRequest, 0);

        when(bookRepository.findAll(pageRequest)).thenReturn(emptyPage);

        List<BookDTO> result = bookService.getBooks(pageRequest);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository, times(1)).findAll(pageRequest);
    }

    /**
     * Test correct mapping of book entities to DTOs.
     */
    @Test
    @DisplayName("Should map book entities to DTOs correctly")
    void shouldMapBookEntitiesToDTOsCorrectly() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Book book1 = new Book();
        book1.setId(1L);
        book1.setIsbnNo("978-3-16-148410-0");
        book1.setTitle("Test Book 1");
        book1.setAuthor("Test Author 1");
        book1.setVersion(1);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setIsbnNo("978-3-16-148410-0");
        book2.setTitle("Test Book 1");
        book2.setAuthor("Test Author 1");
        book2.setVersion(2);

        List<Book> bookList = Arrays.asList(book1, book2);
        Page<Book> bookPage = new PageImpl<>(bookList, pageRequest, bookList.size());

        when(bookRepository.findAll(pageRequest)).thenReturn(bookPage);

        List<BookDTO> result = bookService.getBooks(pageRequest);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(book1.getIsbnNo(), result.get(0).getIsbnNo());
        assertEquals(book1.getTitle(), result.get(0).getTitle());
        assertEquals(book1.getAuthor(), result.get(0).getAuthor());
        assertEquals(book1.getVersion(), result.get(0).getVersion());

        assertEquals(book2.getIsbnNo(), result.get(1).getIsbnNo());
        assertEquals(book2.getTitle(), result.get(1).getTitle());
        assertEquals(book2.getAuthor(), result.get(1).getAuthor());
        assertEquals(book2.getVersion(), result.get(1).getVersion());

        verify(bookRepository, times(1)).findAll(pageRequest);
    }
}
