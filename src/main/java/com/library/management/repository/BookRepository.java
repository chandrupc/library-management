package com.library.management.repository;

import com.library.management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link Book} entities.
 * <p>
 * Extends {@link JpaRepository} for full CRUD and {@link PagingAndSortingRepository}
 * for pagination and sorting capabilities.
 * </p>
 *
 * <p>
 * Custom query methods can be defined using Spring Data JPA naming conventions.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {
    /**
     * Retrieves all books with the specified ISBN number.
     * <p>
     * This method returns a list of {@link Book} entities that share the same ISBN.
     * It is used to identify multiple copies of the same book and to validate
     * consistency of title and author across copies.
     * </p>
     *
     * @param isbnNo the ISBN number of the books to retrieve
     * @return a list of {@link Book} entities with the matching ISBN number;
     * an empty list if no books are found
     */
    List<Book> findByIsbnNo(String isbnNo);

}
