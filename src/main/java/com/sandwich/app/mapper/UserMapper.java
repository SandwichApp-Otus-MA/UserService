package com.sandwich.app.mapper;

import com.sandwich.app.domain.dto.user.UserDto;
import com.sandwich.app.domain.entity.UserEntity;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;

@Mapper
public abstract class UserMapper {

    @Setter(onMethod = @__(@Autowired))
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "password", ignore = true)
    public abstract UserDto convert(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", qualifiedByName = "passwordHash")
    public abstract UserEntity convert(@MappingTarget UserEntity entity, UserDto dto);

    @Named("passwordHash")
    protected String passwordHash(char[] password) {
        return passwordEncoder.encode(CharBuffer.wrap(password));
    }
}
