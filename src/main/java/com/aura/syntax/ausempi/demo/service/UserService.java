package com.aura.syntax.ausempi.demo.service;

import com.aura.syntax.ausempi.demo.api.dto.PaginatedResponseDto;
import com.aura.syntax.ausempi.demo.api.dto.ResponseDto;
import com.aura.syntax.ausempi.demo.api.dto.UserDto;
import com.aura.syntax.ausempi.demo.converter.UserConverter;
import com.aura.syntax.ausempi.demo.entity.Users;
import com.aura.syntax.ausempi.demo.exception.ServiceException;
import com.aura.syntax.ausempi.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    public ResponseDto createUser(UserDto userDto) {
        Optional<Users> userWithEmail = userRepository.findByEmail(userDto.getEmail());
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(userDto.getId())) {
            throw new ServiceException("Email already in use", "Bad request", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(userConverter.convert(userDto));
        return new ResponseDto("User created successfully");
    }

    public PaginatedResponseDto<UserDto> getAllUsers(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<UserDto> userDtos = userRepository.getAllUsers(pageable, search);

        PaginatedResponseDto<UserDto> userDtoPaginatedResponseDto = new PaginatedResponseDto<>();
        List<UserDto> userDtoList = userDtos.getContent();
        userDtoPaginatedResponseDto.setData(userDtoList);
        userDtoPaginatedResponseDto.setCurrentPage(page);
        userDtoPaginatedResponseDto.setTotalItems(userDtos.getTotalElements());
        userDtoPaginatedResponseDto.setTotalPages(userDtos.getTotalPages());
        userDtoPaginatedResponseDto.setPageSize(size);
        userDtoPaginatedResponseDto.setHasPrevious(page > 1);
        userDtoPaginatedResponseDto.setHasNext(page < userDtos.getTotalPages());

        return userDtoPaginatedResponseDto;
    }

    public ResponseDto updateUser(UserDto userDto) {
        Users existingUser = userRepository.findById(userDto.getId()).orElseThrow(() -> new ServiceException("User not found", "Bad request", HttpStatus.BAD_REQUEST));
        Optional<Users> userWithEmail = userRepository.findByEmail(userDto.getEmail());
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(userDto.getId())) {
            throw new ServiceException("Email already in use", "Bad request", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(userConverter.convert(userDto, existingUser));
        return new ResponseDto("User updated successfully");
    }

    public UserDto getUserById(Long id) {
        Users existingUser = userRepository.findById(id).orElseThrow(() -> new ServiceException("User not found", "Bad request", HttpStatus.BAD_REQUEST));
        return userConverter.convert(existingUser);
    }

    public ResponseDto deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ServiceException("User not found", "Bad request", HttpStatus.BAD_REQUEST));
        userRepository.deleteById(id);
        return new ResponseDto("User deleted successfully");
    }

    public ResponseDto updateExamStatus(Long userId) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new ServiceException("User not found", "Bad request", HttpStatus.BAD_REQUEST));
        users.setIsExamCompleted(Boolean.TRUE);
        userRepository.save(users);

        return new ResponseDto("User exam status updated");
    }
}
