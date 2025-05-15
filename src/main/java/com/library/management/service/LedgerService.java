package com.library.management.service;

import com.library.management.exception.ConflictException;

public interface LedgerService {
    void handleLedger(Long bookId, Long borrowerId, boolean isBorrow) throws ConflictException;
}
