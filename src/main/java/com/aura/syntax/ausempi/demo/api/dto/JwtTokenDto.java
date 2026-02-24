package com.aura.syntax.ausempi.demo.api.dto;

import com.aura.syntax.ausempi.demo.enums.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenDto {
    private String userId;
    private String email;
    private UserType userType;
}
