package com.library.management.model;

import com.library.management.enums.LedgerStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ledger")
@Data
public class Ledger extends AuditColumns {
    @Id
    @GeneratedValue(generator = "ledger_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ledger_seq", sequenceName = "ledger_seq", allocationSize = 1)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "borrower_id")
    private Long borrowerId;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private LedgerStatus status;

}
