package com.library.management.service.impl;

import com.library.management.dto.BorrowerDTO;
import com.library.management.exception.ConflictException;
import com.library.management.model.Borrower;
import com.library.management.repository.BorrowerRepository;
import com.library.management.service.BorrowerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link BorrowerService} for handling operations related to borrowers.
 * <p>
 * This service provides logic for adding new borrowers to the system, ensuring no duplicates exist.
 * </p>
 *
 * <p>
 * Uses {@link ModelMapper} to convert between DTOs and entities.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@Service
public class BorrowerServiceImpl implements BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Adds a new borrower to the system if a borrower with the same name and email does not already exist.
     *
     * @param borrowerDTO the {@link BorrowerDTO} containing borrower details
     * @throws ConflictException if a borrower with the same name and email already exists
     */
    @Override
    public void addBorrower(BorrowerDTO borrowerDTO) throws ConflictException {
        Optional<Borrower> exists = borrowerRepository.findByNameAndEmail(
                borrowerDTO.getName(),
                borrowerDTO.getEmail()
        );
        if (exists.isPresent()) {
            throw new ConflictException("Borrower Already Exists");
        }
        Borrower borrower = modelMapper.map(borrowerDTO, Borrower.class);
        borrowerRepository.save(borrower);
    }
}
