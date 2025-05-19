package com.library.management.controller;

import com.library.management.dto.BorrowerDTO;
import com.library.management.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing borrower-related operations in the library system.
 * <p>
 * This controller handles HTTP requests related to borrower management including
 * registering new borrowers in the system. It exposes RESTful endpoints that respond
 * to various HTTP methods for client applications to interact with the library's
 * borrower database.
 * </p>
 * <p>
 * The controller is responsible for validating incoming borrower data and delegating
 * business logic to the appropriate service layer components.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @see BorrowerService
 * @see BorrowerDTO
 * @since 2025-05-19
 */
@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    @Autowired
    private BorrowerService borrowerService;

    /**
     * Registers a new borrower in the library system.
     * <p>
     * This endpoint validates the incoming borrower data and stores it in the library's
     * database. The borrower information is expected to be provided as JSON in the request body.
     * Validation errors will trigger a BAD_REQUEST response with details about the validation failures.
     * </p>
     * <p>
     * Once successfully processed, the new borrower will be eligible to borrow books from
     * the library, subject to the system's borrowing policies.
     * </p>
     *
     * @param borrowerDTO The data transfer object containing the new borrower's details
     * @return A confirmation message indicating the borrower was successfully registered
     * @throws Exception If a borrower with the same identifier already exists or other processing errors occur
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addBorrower(@Valid @RequestBody BorrowerDTO borrowerDTO) throws Exception {
        borrowerService.addBorrower(borrowerDTO);
        return "Added new Borrower in your Library";
    }
}