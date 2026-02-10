package com.aura.syntax.ausempi.demo.enums;

import com.aura.syntax.ausempi.demo.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
public enum UserType {

    ADMIN("Admin"),
    DRIVER("Driver");

    private final String mappedValue;

    UserType(String mappedValue) {
        this.mappedValue = mappedValue;
    }

    public static UserType fromMappedValue(String mappedValue) {
        if (mappedValue == null || mappedValue.isBlank()) {
            return null;
        }
        for (UserType userType : UserType.values()) {
            if (userType.mappedValue.equalsIgnoreCase(mappedValue)) {
                return userType;
            }
        }
        throw new ServiceException("UNSUPPORTED_TYPE" + mappedValue, "BAD_REQUEST", HttpStatus.BAD_REQUEST);
    }

    public String getMappedValue() {
        return mappedValue;
    }

}
