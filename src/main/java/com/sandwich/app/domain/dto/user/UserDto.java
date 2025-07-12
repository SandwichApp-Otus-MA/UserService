package com.sandwich.app.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sandwich.app.domain.dto.enums.UserStatus;
import com.sandwich.app.domain.entity.UserEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link UserEntity}
 */
@Data
@Accessors(chain = true)
public class UserDto {
    private UUID id;
    private String lastName;
    private String firstName;
    private String middleName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private UserStatus status;
}