package com.library.management.enums;

/**
 * Enumeration representing the possible statuses of a book in the library ledger system.
 * <p>
 * This enum defines the valid states that can be assigned to a book transaction
 * in the library management system. It is used to track whether a book is currently
 * borrowed by a user or has been returned to the library.
 * </p>
 * <p>
 * The status is a critical part of the library ledger system, as it determines
 * the availability of books and helps enforce borrowing policies.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @see com.library.management.service.LedgerService
 * @since 2025-05-19
 */
public enum LedgerStatus {
    /**
     * Indicates that a book has been borrowed by a user and is currently not available in the library.
     * <p>
     * When a book is marked with this status, it remains unavailable for other borrowers
     * until it is returned and its status is updated to RETURNED.
     * </p>
     */
    BORROWED,

    /**
     * Indicates that a previously borrowed book has been returned to the library and is available for borrowing.
     * <p>
     * When a book is marked with this status, it becomes available for other borrowers
     * to check out. This status represents the completion of a borrowing cycle.
     * </p>
     */
    RETURNED
}