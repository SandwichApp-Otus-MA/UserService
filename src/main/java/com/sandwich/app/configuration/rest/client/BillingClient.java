package com.sandwich.app.configuration.rest.client;

import com.sandwich.app.configuration.rest.AbstractClient;
import com.sandwich.app.configuration.rest.RestClientFactory;
import com.sandwich.app.configuration.rest.properties.BillingProperties;
import com.sandwich.app.domain.dto.billing.UserAccountDto;

import java.util.UUID;

public class BillingClient extends AbstractClient<BillingProperties> {

    public BillingClient(BillingProperties properties, RestClientFactory restClientFactory) {
        super(properties, restClientFactory);
    }

    public UUID create(UserAccountDto userAccount) {
        return restClient
            .post()
            .uri(properties.getEndpoints().getCreate())
            .body(userAccount)
            .retrieve()
            .body(UUID.class);
    }
}
