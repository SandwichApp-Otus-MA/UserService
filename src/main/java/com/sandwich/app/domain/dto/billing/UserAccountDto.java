package com.sandwich.app.domain.dto.billing;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserAccountDto {
    @NotNull
    private UUID userId;
    @NotNull
    private BigDecimal balance;
}
