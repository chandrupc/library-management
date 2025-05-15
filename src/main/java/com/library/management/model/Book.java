package com.library.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book extends AuditColumns {

    @Id
    @GeneratedValue(generator = "book_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", allocationSize = 1)
    Long id;

    @Column(name = "isbn_no", length = 50)
    String isbnNo;

    @Column(name = "title", length = 255)
    String title;

    @Column(name = "author", length = 50)
    String author;

}
