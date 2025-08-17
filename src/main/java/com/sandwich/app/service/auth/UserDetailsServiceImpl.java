package com.sandwich.app.service.auth;

import com.sandwich.app.domain.dto.auth.SandwichUser;
import com.sandwich.app.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmailOrPhoneNumber(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new SandwichUser()
            .setUserId(user.getId())
            .setUser(User.builder()
                .username(Optional.ofNullable(user.getEmail()).orElse(user.getPhoneNumber()))
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                    .map(Enum::name)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()))
                .build());
    }
}
