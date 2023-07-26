package com.digitalnewagency.task.persistence.repository;


import com.digitalnewagency.task.persistence.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Optional<Merchant> findOneByMerchantId(UUID userId);
}
