package com.digitalnewagency.task.service;

import com.digitalnewagency.task.api.dto.MerchantDto;
import com.digitalnewagency.task.persistence.entity.Merchant;
import com.digitalnewagency.task.persistence.repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public MerchantDto addMerchant(String name) {
        Merchant merchant = merchantRepository.save(new Merchant(UUID.randomUUID(), name));
        return merchantToMerchantDto(merchant);
    }

    public MerchantDto getMerchant(UUID merchantId) {
        return merchantRepository.findOneByMerchantId(merchantId).map(this::merchantToMerchantDto).orElseThrow();
    }


    public MerchantDto merchantToMerchantDto(Merchant merchant) {
        return new MerchantDto(merchant.getMerchantId(), merchant.getName());
    }
}
