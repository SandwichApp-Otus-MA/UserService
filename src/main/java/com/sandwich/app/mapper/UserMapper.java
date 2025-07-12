package com.sandwich.app.mapper;

import com.sandwich.app.domain.dto.user.UserDto;
import com.sandwich.app.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

    UserDto convert(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    UserEntity convert(@MappingTarget UserEntity entity, UserDto dto);
}
