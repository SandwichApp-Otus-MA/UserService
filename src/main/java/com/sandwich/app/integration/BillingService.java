package com.sandwich.app.integration;

import com.sandwich.app.configuration.rest.client.BillingClient;
import com.sandwich.app.domain.dto.billing.UserAccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingClient billingClient;

    public void create(UserAccountDto userAccountDto) {
        log.info("Создание нового акканута для userId: {}", userAccountDto.getUserId());
        billingClient.create(userAccountDto);
    }
}
