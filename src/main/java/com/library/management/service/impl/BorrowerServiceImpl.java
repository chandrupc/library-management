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

@Service
public class BorrowerServiceImpl implements BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param borrowerDTO
     * @throws ConflictException
     */
    @Override
    public void addBorrower(BorrowerDTO borrowerDTO) throws ConflictException {
        Optional<Borrower> exists = borrowerRepository.findByNameAndEmail(borrowerDTO.getName(), borrowerDTO.getEmail());
        if (exists.isPresent()) {
            throw new ConflictException("Borrower Already Exists");
        }
        Borrower borrower = modelMapper.map(borrowerDTO, Borrower.class);
        borrowerRepository.save(borrower);
    }
}
