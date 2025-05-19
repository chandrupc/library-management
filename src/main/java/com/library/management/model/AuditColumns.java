package com.library.management.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * Abstract base class that provides audit fields for entity creation and update timestamps.
 * <p>
 * Entities that extend this class will automatically have {@code createdDate} and {@code updatedDate}
 * fields managed by Hibernate.
 * </p>
 *
 * <p>
 * The {@code createdDate} is set only once when the entity is first persisted, while
 * {@code updatedDate} is updated whenever the entity is modified.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@MappedSuperclass
public class AuditColumns {

    /**
     * The timestamp when the entity was created.
     * This field is set automatically and is not updatable.
     */
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    Date createdDate;

    /**
     * The timestamp when the entity was last updated.
     * This field is updated automatically whenever the entity is modified.
     */
    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    Date updatedDate;
}
