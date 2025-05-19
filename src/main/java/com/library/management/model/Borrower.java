package com.library.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a borrower in the library management system.
 * <p>
 * This class extends {@link AuditColumns} to inherit automatic timestamp management
 * for creation and updates.
 * </p>
 *
 * <p>
 * Each borrower has a unique ID, a name, and an email address.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@Entity
@Table(name = "borrower")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Borrower extends AuditColumns {

    /**
     * Unique identifier for the borrower.
     * This ID is auto-generated using a sequence.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrower_seq")
    @SequenceGenerator(name = "borrower_seq", sequenceName = "borrower_seq", allocationSize = 1)
    Long id;

    /**
     * Name of the borrower.
     */
    @Column(name = "name", length = 50)
    String name;

    /**
     * Email address of the borrower.
     */
    @Column(name = "email", length = 50)
    String email;
}
