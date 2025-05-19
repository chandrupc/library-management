package com.library.management.repository;

import com.library.management.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Borrower} entities.
 * <p>
 * Extends {@link JpaRepository} for CRUD operations and
 * {@link PagingAndSortingRepository} for pagination and sorting capabilities.
 * </p>
 *
 * <p>
 * Provides methods to query borrowers by their name and email.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long>, PagingAndSortingRepository<Borrower, Long> {

    /**
     * Finds a borrower by their name and email address.
     *
     * @param name  the name of the borrower
     * @param email the email address of the borrower
     * @return an {@link Optional} containing the matched {@link Borrower}, or empty if not found
     */
    Optional<Borrower> findByNameAndEmail(String name, String email);
}
