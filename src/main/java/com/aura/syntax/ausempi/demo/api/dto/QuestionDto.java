package com.aura.syntax.ausempi.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionDto {
    private Long id;
    private String questionText;
    private Integer questionOrder;
    private Long videoId;
    private List<OptionDto> optionDtos;

    public QuestionDto(Long id, String questionText) {
        this.id = id;
        this.questionText = questionText;
    }
}
