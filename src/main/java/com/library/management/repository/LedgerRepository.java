package com.library.management.repository;

import com.library.management.enums.LedgerStatus;
import com.library.management.model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {
    Optional<Ledger> findByBookIdAndStatus(Long bookId, LedgerStatus borrowed);
}
