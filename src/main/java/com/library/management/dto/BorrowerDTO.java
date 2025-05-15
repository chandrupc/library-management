package com.library.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerDTO {

    Long id;

    @NotBlank(message = "Borrower name is mandatory")
    @Length(min = 2, max = 50, message = "Please provide a name greater than one character and less than 50 characters")
    String name;

    @Email
    @NotBlank(message = "Borrower email is mandatory")
    @Length(min = 2, max = 50, message = "Please provide an email greater than one character and less than 50 characters")
    String email;
}
