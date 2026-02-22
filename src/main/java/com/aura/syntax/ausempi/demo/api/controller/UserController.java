package com.aura.syntax.ausempi.demo.api.controller;

import com.aura.syntax.ausempi.demo.api.dto.PaginatedResponseDto;
import com.aura.syntax.ausempi.demo.api.dto.ResponseDto;
import com.aura.syntax.ausempi.demo.api.dto.UserDto;
import com.aura.syntax.ausempi.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseDto createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping
    public PaginatedResponseDto<UserDto> getAllUsers(@RequestParam(value = "page", required = false) int page,
                                            @RequestParam(value = "size", required = false) int size,
                                            @RequestParam(value = "search", required = false) String search) {
        return userService.getAllUsers(page, size, search);
    }

    @PatchMapping
    public ResponseDto updateUser(@RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    @PutMapping
    public ResponseDto updateExamStatus(@RequestParam(value = "userId") Long userId){
        return userService.updateExamStatus(userId);
    }
}
