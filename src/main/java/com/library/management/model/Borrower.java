package com.library.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "borrower")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Borrower extends AuditColumns {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrower_seq")
    @SequenceGenerator(name = "borrower_seq", sequenceName = "borrower_seq", allocationSize = 1)
    Long id;

    @Column(name = "name", length = 50)
    String name;

    @Column(name = "email", length = 50)
    String email;
}
