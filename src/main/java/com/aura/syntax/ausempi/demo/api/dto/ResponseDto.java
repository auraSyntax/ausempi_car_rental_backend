package com.aura.syntax.ausempi.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private String message;

    public ResponseDto(String message) {
        this.message = message;
    }
}
