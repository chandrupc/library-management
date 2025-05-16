package com.library.management.service.impl;

import com.library.management.dto.BookDTO;
import com.library.management.exception.ConflictException;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowerRepository;
import com.library.management.repository.LedgerRepository;
import com.library.management.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param bookPayload
     * @throws Exception
     */
    @Override
    public void addBook(BookDTO bookPayload) throws Exception {
        Optional<Book> exists = bookRepository.findByIsbnNoAndTitleAndAuthor(bookPayload.getIsbnNo(), bookPayload.getTitle(), bookPayload.getAuthor());
        if (exists.isPresent()) {
            throw new ConflictException("Book Already Exists");
        }
        Book newBook = modelMapper.map(bookPayload, Book.class);
        bookRepository.save(newBook);
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public List<BookDTO> getBooks(PageRequest pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.stream().map(e -> modelMapper.map(e, BookDTO.class)).collect(Collectors.toList());
    }

}
