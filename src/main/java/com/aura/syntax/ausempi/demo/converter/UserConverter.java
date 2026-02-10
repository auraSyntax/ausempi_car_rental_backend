package com.aura.syntax.ausempi.demo.converter;

import com.aura.syntax.ausempi.demo.api.dto.UserDto;
import com.aura.syntax.ausempi.demo.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;

    public Users convert(UserDto userDto){
        return Users.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .employeeId(userDto.getEmployeeId())
                .createdAt(LocalDateTime.now())
                .isExamCompleted(Boolean.FALSE)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .isActive(Boolean.TRUE)
                .build();
    }

    public UserDto convert(Users users){
        return UserDto.builder()
                .id(users.getId())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .email(users.getEmail())
                .phoneNumber(users.getPhoneNumber())
                .employeeId(users.getEmployeeId())
                .createdAt(users.getCreatedAt())
                .isExamCompleted(users.getIsExamCompleted())
                .build();
    }

    public Users convert(UserDto userDto, Users users){
        if (userDto.getFirstName() != null) users.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null) users.setLastName(userDto.getLastName());
        if (userDto.getEmail() != null) users.setEmail(userDto.getEmail());
        if (userDto.getEmployeeId() != null) users.setEmployeeId(userDto.getEmployeeId());
        if (userDto.getPhoneNumber() != null) users.setPhoneNumber(userDto.getPhoneNumber());
        if (userDto.getIsActive() != null) users.setIsActive(userDto.getIsActive());

        return users;
    }
}
