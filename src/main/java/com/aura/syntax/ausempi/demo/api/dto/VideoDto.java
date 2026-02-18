package com.aura.syntax.ausempi.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDto {
    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private String viewVideoUrl;
    private Integer durationSeconds;
    private LocalDateTime createdAt;
    private List<QuestionDto> questionDtos;
    private Integer videoOrder;

    public VideoDto(Long id, String title, String description, String videoUrl, Integer durationSeconds, LocalDateTime createdAt, Integer videoOrder) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.durationSeconds = durationSeconds;
        this.createdAt = createdAt;
        this.videoOrder = videoOrder;
    }

}
