package com.library.management.model;

import com.library.management.enums.LedgerStatus;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity representing a ledger entry that tracks the borrowing status of books
 * in the library management system.
 * <p>
 * Each ledger record links a book and a borrower along with the current status
 * of the transaction (e.g., BORROWED, RETURNED).
 * </p>
 *
 * <p>
 * This class extends {@link AuditColumns} to automatically record creation and update timestamps.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@Entity
@Table(name = "ledger")
@Data
public class Ledger extends AuditColumns {

    /**
     * Unique identifier for the ledger entry.
     * This ID is auto-generated using a sequence.
     */
    @Id
    @GeneratedValue(generator = "ledger_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ledger_seq", sequenceName = "ledger_seq", allocationSize = 1)
    private Long id;

    /**
     * Identifier of the book associated with this ledger entry.
     */
    @Column(name = "book_id")
    private Long bookId;

    /**
     * Identifier of the borrower associated with this ledger entry.
     */
    @Column(name = "borrower_id")
    private Long borrowerId;

    /**
     * Current status of the ledger entry (e.g., BORROWED, RETURNED).
     */
    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private LedgerStatus status;
}
