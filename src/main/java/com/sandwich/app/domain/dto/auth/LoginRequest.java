package com.sandwich.app.domain.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginRequest {
    private String login;
    private char[] password;
}
