package com.library.management.controller;

import com.library.management.dto.BorrowerDTO;
import com.library.management.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    @Autowired
    private BorrowerService borrowerService;

    /**
     * @param borrowerDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addBorrower(@Valid @RequestBody BorrowerDTO borrowerDTO) throws Exception {
        borrowerService.addBorrower(borrowerDTO);
        return "Added new Borrower in your Library";
    }
}
