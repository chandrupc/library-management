package com.library.management.service;

import com.library.management.dto.BorrowerDTO;
import com.library.management.exception.ConflictException;

/**
 * Service interface for managing borrower-related operations in the library management system.
 * <p>
 * Defines the contract for adding a new borrower to the system.
 * </p>
 *
 * <p>
 * This interface helps encapsulate business logic related to borrower creation and validation.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
public interface BorrowerService {

    /**
     * Adds a new borrower to the system.
     *
     * @param borrowerDTO the {@link BorrowerDTO} containing borrower details to be added
     * @throws ConflictException if a borrower with the same name and email already exists
     */
    void addBorrower(BorrowerDTO borrowerDTO) throws ConflictException;
}
