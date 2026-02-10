package com.aura.syntax.ausempi.demo.api.dto;

import com.aura.syntax.ausempi.demo.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "First name is required")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Employee id is required")
    private String employeeId;
    private String password;
    private LocalDateTime createdAt;
    private Boolean isExamCompleted;
    private String employeeName;
    private Boolean isActive;
    private String userType;

    public UserDto(Long id, String employeeName, String phoneNumber, String email, String employeeId, LocalDateTime createdAt, Boolean isExamCompleted,
                   Boolean isActive, UserType userType) {
        this.id = id;
        this.employeeName = employeeName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.employeeId = employeeId;
        this.createdAt = createdAt;
        this.isExamCompleted = isExamCompleted;
        this.isActive = isActive;
        this.userType = userType != null ? userType.getMappedValue() : null;
    }
}
