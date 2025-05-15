package com.library.management.dto;

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
public class BookDTO {

    Long id;

    @NotBlank(message = "Title of the book is mandatory")
    @Length(min = 2, max = 255, message = "Please provide a title greater than one character and less than 255 characters")
    String title;

    @NotBlank(message = "Author name is mandatory")
    @Length(min = 2, max = 50, message = "Please provide a title greater than one character and less than 50 characters")
    String author;

    @NotBlank(message = "ISBN Number is mandatory")
    @Length(min = 3, max = 50, message = "Please provide a title greater than one character and less than 50 characters")
    String isbnNo;
}
