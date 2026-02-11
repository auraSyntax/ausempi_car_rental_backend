package com.aura.syntax.ausempi.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String questionText;
    private Integer questionOrder;
    private Long videoId;
    private List<OptionDto> optionDtos;
}
