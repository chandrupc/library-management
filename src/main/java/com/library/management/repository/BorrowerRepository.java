package com.library.management.repository;

import com.library.management.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long>, PagingAndSortingRepository<Borrower, Long> {
    Optional<Borrower> findByNameAndEmail(String name, String email);
}
