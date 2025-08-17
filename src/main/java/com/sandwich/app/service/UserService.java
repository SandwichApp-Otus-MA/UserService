package com.sandwich.app.service;

import static com.sandwich.app.utils.PageUtil.createPage;
import static com.sandwich.app.utils.PageUtil.createPageable;
import static com.sandwich.app.utils.PageUtil.createSort;

import com.sandwich.app.domain.dto.auth.UserRole;
import com.sandwich.app.domain.dto.pagination.PageData;
import com.sandwich.app.domain.dto.user.UserDto;
import com.sandwich.app.domain.dto.user.UserSearchRequest;
import com.sandwich.app.domain.entity.UserEntity;
import com.sandwich.app.domain.repository.UserRepository;
import com.sandwich.app.mapper.UserMapper;
import com.sandwich.app.query.builder.UserQueryBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryBuilder queryBuilder;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserDto get(UUID id) {
        return userRepository.findById(id)
            .map(userMapper::convert)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public PageData<UserDto> getAll(UserSearchRequest request) {
        var predicate = queryBuilder.createPredicate(request.getFilter());
        var sort = createSort(request.getSorting());
        var pageable = createPageable(request.getPagination(), sort);
        var all = userRepository.findAll(predicate, pageable);

        return createPage(
            all.getTotalPages(),
            all.getTotalElements(),
            all.getContent().stream()
                .map(userMapper::convert)
                .collect(Collectors.toList()),
            request.getPagination());
    }

    @Transactional
    public UUID create(UserDto user) {
        validation(user);

        Optional.ofNullable(user.getId())
            .flatMap(id -> userRepository.findById(user.getId())).ifPresent(o -> {
                throw new IllegalStateException("Пользователь c id: %s уже существует!".formatted(user.getId()));
            });

        var newUser = new UserEntity()
            .setRoles(Set.of(UserRole.CLIENT));
        userMapper.convert(newUser, user);
        return userRepository.save(newUser).getId();
    }

    @Transactional
    public void edit(UserDto user) {
        validation(user);

        var existUser = Optional.ofNullable(user.getId())
            .flatMap(id -> userRepository.findById(user.getId()))
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userMapper.convert(existUser, user);
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    private void validation(UserDto user) {
        if (Strings.isBlank(user.getEmail()) && Strings.isBlank(user.getPhoneNumber())) {
            throw new IllegalArgumentException("Обазятельно нужно указать email или номер телефона!");
        }
    }
}
