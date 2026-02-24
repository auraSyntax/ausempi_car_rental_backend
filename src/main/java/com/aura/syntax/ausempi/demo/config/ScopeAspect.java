package com.aura.syntax.ausempi.demo.config;

import com.aura.syntax.ausempi.demo.api.dto.JwtTokenDto;
import com.aura.syntax.ausempi.demo.enums.UserType;
import com.aura.syntax.ausempi.demo.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Aspect
@Slf4j
public class ScopeAspect {
    private final HttpServletRequest request;

    public ScopeAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Before("@annotation(scope)")
    public void checkScope(Scope scope) throws Throwable {
        String userType = request.getHeader("user_type");

        // List of allowed user types for the method
        List<String> allowedUserTypes = Arrays.asList(scope.value());

        log.info("Allowed User Types----{}", allowedUserTypes);
        log.info("User Types----" + userType);

        // Check if the user's role (userType) is allowed to access this endpoint
        if (!allowedUserTypes.contains(userType)) {
            throw new ServiceException("YOUR_GRANT_TYPE_IS_NOT_AUTHORIZED", "UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }
    }

    public JwtTokenDto getTokenPropertiesFromToken() {
        return JwtTokenDto.builder()
                .userId(request.getHeader("user_id"))
                .email(request.getHeader("email"))
                .userType(UserType.fromMappedValue(request.getHeader("user_type")))
                .build();
    }
}

