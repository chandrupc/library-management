package com.library.management.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@MappedSuperclass
public class AuditColumns {

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    Date updatedDate;
}
