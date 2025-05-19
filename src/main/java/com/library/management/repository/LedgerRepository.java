package com.library.management.repository;

import com.library.management.enums.LedgerStatus;
import com.library.management.model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Ledger} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 * </p>
 *
 * <p>
 * Includes a custom method to find a ledger entry by book ID and status.
 * This is typically used to determine if a book is currently borrowed.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {

    /**
     * Finds a ledger entry by the book ID and ledger status.
     *
     * @param bookId the ID of the book
     * @param status the status of the ledger entry (e.g., BORROWED)
     * @return an {@link Optional} containing the matched {@link Ledger} entry, or empty if not found
     */
    Optional<Ledger> findByBookIdAndStatus(Long bookId, LedgerStatus status);
}
