package com.library.management;

import com.library.management.dto.BookDTO;
import com.library.management.exception.ConflictException;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowerRepository;
import com.library.management.repository.LedgerRepository;
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

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private LedgerRepository ledgerRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private BookServiceImpl bookService;

    private BookDTO bookDTO;
    private Book book;

    @BeforeEach
    void setUp() {
        // Setup test data
        bookDTO = new BookDTO();
        bookDTO.setIsbnNo("978-3-16-148410-0");
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor("Test Author");

        book = new Book();
        book.setId(1L);
        book.setIsbnNo("978-3-16-148410-0");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
    }

    @Test
    @DisplayName("Should add a new book successfully")
    void shouldAddBookSuccessfully() throws Exception {
        // Given
        when(bookRepository.findByIsbnNoAndTitleAndAuthor(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        bookService.addBook(bookDTO);

        // Then
        verify(bookRepository, times(1)).findByIsbnNoAndTitleAndAuthor(
                bookDTO.getIsbnNo(), bookDTO.getTitle(), bookDTO.getAuthor());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw ConflictException when adding existing book")
    void shouldThrowConflictExceptionWhenAddingExistingBook() {
        // Given
        when(bookRepository.findByIsbnNoAndTitleAndAuthor(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(book));

        // When & Then
        assertThrows(ConflictException.class, () -> bookService.addBook(bookDTO));
        verify(bookRepository, times(1)).findByIsbnNoAndTitleAndAuthor(
                bookDTO.getIsbnNo(), bookDTO.getTitle(), bookDTO.getAuthor());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Should get books with pagination")
    void shouldGetBooksWithPagination() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Book> bookList = Arrays.asList(book);
        Page<Book> bookPage = new PageImpl<>(bookList, pageRequest, bookList.size());

        when(bookRepository.findAll(pageRequest)).thenReturn(bookPage);

        // When
        List<BookDTO> result = bookService.getBooks(pageRequest);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookDTO.getIsbnNo(), result.get(0).getIsbnNo());
        assertEquals(bookDTO.getTitle(), result.get(0).getTitle());
        assertEquals(bookDTO.getAuthor(), result.get(0).getAuthor());
        verify(bookRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Should return empty list when no books found")
    void shouldReturnEmptyListWhenNoBooksFound() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> emptyPage = new PageImpl<>(Arrays.asList(), pageRequest, 0);

        when(bookRepository.findAll(pageRequest)).thenReturn(emptyPage);

        // When
        List<BookDTO> result = bookService.getBooks(pageRequest);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Should map book entities to DTOs correctly")
    void shouldMapBookEntitiesToDTOsCorrectly() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);

        Book book1 = new Book();
        book1.setId(1L);
        book1.setIsbnNo("978-3-16-148410-0");
        book1.setTitle("Test Book 1");
        book1.setAuthor("Test Author 1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setIsbnNo("978-3-16-148410-1");
        book2.setTitle("Test Book 2");
        book2.setAuthor("Test Author 2");

        List<Book> bookList = Arrays.asList(book1, book2);
        Page<Book> bookPage = new PageImpl<>(bookList, pageRequest, bookList.size());

        when(bookRepository.findAll(pageRequest)).thenReturn(bookPage);

        // When
        List<BookDTO> result = bookService.getBooks(pageRequest);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(book1.getIsbnNo(), result.get(0).getIsbnNo());
        assertEquals(book1.getTitle(), result.get(0).getTitle());
        assertEquals(book1.getAuthor(), result.get(0).getAuthor());

        assertEquals(book2.getIsbnNo(), result.get(1).getIsbnNo());
        assertEquals(book2.getTitle(), result.get(1).getTitle());
        assertEquals(book2.getAuthor(), result.get(1).getAuthor());

        verify(bookRepository, times(1)).findAll(pageRequest);
    }
}