package com.sandwich.app.configuration.rest.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingProperties implements ClientProperties {

    private String url;

    private Endpoints endpoints;

    @Getter
    @Setter
    public static class Endpoints {
        private String create = "/v1/user-account/create";
    }
}
