package com.sandwich.app.query.builder;

import com.querydsl.core.BooleanBuilder;
import com.sandwich.app.domain.dto.user.UserFilter;
import com.sandwich.app.domain.entity.QUserEntity;
import com.sandwich.app.utils.AdvancedFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserQueryBuilder {

    private static final QUserEntity USER = QUserEntity.userEntity;

    public BooleanBuilder createPredicate(UserFilter filter) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.getId() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(USER.id, filter.getId()));
        }

        if (filter.getLastName() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(USER.lastName, filter.getLastName()));
        }

        if (filter.getFirstName() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(USER.firstName, filter.getFirstName()));
        }

        if (filter.getMiddleName() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(USER.middleName, filter.getMiddleName()));
        }

        if (filter.getBirthDate() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(USER.birthDate, filter.getBirthDate()));
        }

        if (filter.getEmail() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(USER.email, filter.getEmail()));
        }

        if (filter.getPhoneNumber() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(USER.phoneNumber, filter.getPhoneNumber()));
        }

        return predicate;
    }
}