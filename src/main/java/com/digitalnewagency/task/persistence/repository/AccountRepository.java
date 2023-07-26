package com.digitalnewagency.task.persistence.repository;


import com.digitalnewagency.task.persistence.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findOneByAccountId(UUID accountId);

    Optional<Account> findOneByUserId(UUID userId);
}
