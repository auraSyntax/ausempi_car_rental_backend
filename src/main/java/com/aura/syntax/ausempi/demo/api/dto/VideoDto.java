package com.aura.syntax.ausempi.demo.api.dto;

import com.aura.syntax.ausempi.demo.entity.Questions;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private Integer durationSeconds;
    private LocalDateTime createdAt;
    private List<QuestionDto> questionDtos;
}
