package com.library.management.service;

import com.library.management.dto.BookDTO;
import com.library.management.exception.ConflictException;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BookService {
    void addBook(BookDTO book) throws Exception;

    List<BookDTO> getBooks(PageRequest pageable);
}
