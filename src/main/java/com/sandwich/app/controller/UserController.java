package com.sandwich.app.controller;

import com.sandwich.app.domain.dto.pagination.PageData;
import com.sandwich.app.domain.dto.user.UserDto;
import com.sandwich.app.domain.dto.user.UserSearchRequest;
import com.sandwich.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PageData<UserDto>> getAll(@Valid @RequestBody UserSearchRequest request) {
        return ResponseEntity.ok(userService.getAll(request));
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@Valid @RequestBody UserDto user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> edit(@Valid @RequestBody UserDto user) {
        userService.edit(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
