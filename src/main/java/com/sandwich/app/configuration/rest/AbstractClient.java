package com.sandwich.app.configuration.rest;

import com.sandwich.app.configuration.rest.properties.ClientProperties;
import org.springframework.web.client.RestClient;

public abstract class AbstractClient<T extends ClientProperties> {

    protected final T properties;
    protected final RestClient restClient;

    protected AbstractClient(T properties, RestClientFactory restClientFactory) {
        this.properties = properties;
        this.restClient = restClientFactory.createRestClient(properties.getUrl());
    }
}
