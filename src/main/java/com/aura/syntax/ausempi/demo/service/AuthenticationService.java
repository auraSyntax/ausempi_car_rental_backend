package com.aura.syntax.ausempi.demo.service;

import com.aura.syntax.ausempi.demo.api.dto.AuthRequest;
import com.aura.syntax.ausempi.demo.api.dto.AuthResponse;
import com.aura.syntax.ausempi.demo.api.dto.RefreshTokenRequest;
import com.aura.syntax.ausempi.demo.api.dto.UserDto;
import com.aura.syntax.ausempi.demo.config.JwtService;
import com.aura.syntax.ausempi.demo.config.RefreshTokenService;
import com.aura.syntax.ausempi.demo.entity.RefreshToken;
import com.aura.syntax.ausempi.demo.entity.Users;
import com.aura.syntax.ausempi.demo.exception.ServiceException;
import com.aura.syntax.ausempi.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse authenticate(AuthRequest request) {
        try {
            Authentication authentication = authenticateUser(request.getEmail(), request.getPassword());

            Users user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ServiceException("User not found", "Bad request", HttpStatus.BAD_REQUEST));
            if (!request.getUserType().equalsIgnoreCase(user.getUserType().getMappedValue())){
                throw new ServiceException("This is email is registered as " + user.getUserType().getMappedValue(), "Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            return buildAuthResponse(user, authentication);

        } catch (BadCredentialsException e) {
            throw new ServiceException("Invalid email or password", "Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new ServiceException("Refresh token not found", "Unauthorized", HttpStatus.UNAUTHORIZED));

        Users user = refreshToken.getUser();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, user.getAuthorities());

        String newAccessToken = jwtService.generateToken(user, authentication);
        return buildAuthResponse(user, newAccessToken);
    }

    private AuthResponse buildAuthResponse(Users user, Authentication authentication) {
        String jwtToken = jwtService.generateToken(user, authentication);
        return buildAuthResponse(user, jwtToken);
    }

    private AuthResponse buildAuthResponse(Users user, String accessToken) {
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        UserDto userDto = new UserDto();
        userDto.setEmployeeName(user.getFirstName() + " " + user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setUserType(user.getUserType().getMappedValue());
        userDto.setIsExamCompleted(user.getIsExamCompleted());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .userDto(userDto)
                .build();
    }

    private Authentication authenticateUser(String email, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
    }
}