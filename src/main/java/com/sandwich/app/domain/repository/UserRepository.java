package com.sandwich.app.domain.repository;

import com.sandwich.app.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID>,
    JpaSpecificationExecutor<UserEntity>,
    QuerydslPredicateExecutor<UserEntity> {

    @Override
    @Modifying
    @Query("delete from UserEntity where id = :uuid")
    void deleteById(UUID uuid);

    @Query("select u from UserEntity u where u.email = :login or u.phoneNumber = :login")
    Optional<UserEntity> findByEmailOrPhoneNumber(String login);
}