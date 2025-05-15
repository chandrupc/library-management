package com.library.management.service;

import com.library.management.dto.BorrowerDTO;
import com.library.management.exception.ConflictException;

public interface BorrowerService {
    void addBorrower(BorrowerDTO borrowerDTO) throws ConflictException;
}
