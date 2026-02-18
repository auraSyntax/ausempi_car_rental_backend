package com.aura.syntax.ausempi.demo.converter;

import com.aura.syntax.ausempi.demo.api.dto.OptionDto;
import com.aura.syntax.ausempi.demo.api.dto.QuestionDto;
import com.aura.syntax.ausempi.demo.api.dto.VideoDto;
import com.aura.syntax.ausempi.demo.entity.Options;
import com.aura.syntax.ausempi.demo.entity.Questions;
import com.aura.syntax.ausempi.demo.entity.Videos;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VideoConverter {

    @Value("${cloudinary.base.url}")
    private String videoUrl;

    public Videos convert(VideoDto videoDto) {
        return Videos.builder()
                .id(videoDto.getId())
                .title(videoDto.getTitle())
                .description(videoDto.getDescription())
                .videoUrl(videoDto.getVideoUrl())
                .createdAt(LocalDateTime.now())
                .durationSeconds(videoDto.getDurationSeconds())
                .questions(convertQuestions(videoDto))
                .videoOrder(videoDto.getVideoOrder())
                .build();
    }

    public Videos convert(VideoDto dto, Videos video) {

        if (dto.getTitle() != null)
            video.setTitle(dto.getTitle());

        if (dto.getVideoUrl() != null)
            video.setVideoUrl(dto.getVideoUrl());

        if (dto.getDescription() != null)
            video.setDescription(dto.getDescription());

        if (dto.getDurationSeconds() != null)
            video.setDurationSeconds(dto.getDurationSeconds());

        if (dto.getVideoOrder() != null)
            video.setVideoOrder(dto.getVideoOrder());

        updateQuestions(video, dto.getQuestionDtos());

        return video;
    }

    private void updateQuestions(Videos video, List<QuestionDto> dtos) {

        Map<Long, Questions> existingMap =
                video.getQuestions()
                        .stream()
                        .collect(Collectors.toMap(Questions::getId, q -> q));

        for (QuestionDto dto : dtos) {

            Questions question;

            if (dto.getId() != null && existingMap.containsKey(dto.getId())) {
                question = existingMap.get(dto.getId());

                if (dto.getQuestionText() != null)
                    question.setQuestionText(dto.getQuestionText());

                if (dto.getQuestionOrder() != null)
                    question.setQuestionOrder(dto.getQuestionOrder());

            } else {
                question = new Questions();
                video.getQuestions().add(question);

                question.setQuestionText(dto.getQuestionText());
                question.setQuestionOrder(dto.getQuestionOrder());
            }

            updateOptions(question, dto.getOptionDtos());
        }
    }

    private void updateOptions(Questions question, List<OptionDto> dtos) {

        Map<Long, Options> existingMap =
                question.getOptions()
                        .stream()
                        .collect(Collectors.toMap(Options::getId, o -> o));

        for (OptionDto dto : dtos) {

            Options option;

            if (dto.getId() != null && existingMap.containsKey(dto.getId())) {
                option = existingMap.get(dto.getId());

                if (dto.getOptionText() != null)
                    option.setOptionText(dto.getOptionText());

                if (dto.getIsCorrect() != null)
                    option.setIsCorrect(dto.getIsCorrect());
            } else {
                option = new Options();
                question.getOptions().add(option);

                option.setOptionText(dto.getOptionText());
                option.setIsCorrect(dto.getIsCorrect());
            }
        }
    }

    public Set<Questions> convertQuestions(VideoDto videoDto) {
        Set<Questions> questions = new HashSet<>();
        videoDto.getQuestionDtos().forEach(questionDto -> {
            Questions question = new Questions();
            question.setQuestionOrder(questionDto.getQuestionOrder());
            question.setQuestionText(questionDto.getQuestionText());
            question.setId(questionDto.getId());
            question.setOptions(convertOptions(questionDto));
            question.setVideoId(questionDto.getVideoId());
            questions.add(question);
        });

        return questions;
    }

    public Set<Options> convertOptions(QuestionDto questionDto) {
        Set<Options> options = new HashSet<>();
        questionDto.getOptionDtos().forEach(optionDto -> {
            Options option = new Options();
            option.setId(optionDto.getId());
            option.setOptionText(optionDto.getOptionText());
            option.setIsCorrect(optionDto.getIsCorrect());
            options.add(option);
        });

        return options;
    }

    public VideoDto convert(Videos videos) {
        return VideoDto.builder()
                .id(videos.getId())
                .title(videos.getTitle())
                .description(videos.getDescription())
                .viewVideoUrl(videos.getVideoUrl() != null ? videoUrl + videos.getVideoUrl() : null)
                .videoUrl(videos.getVideoUrl())
                .createdAt(LocalDateTime.now())
                .durationSeconds(videos.getDurationSeconds())
                .questionDtos(convertQuestions(videos))
                .videoOrder(videos.getVideoOrder())
                .build();
    }

    public List<QuestionDto> convertQuestions(Videos videos) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        videos.getQuestions().forEach(questions -> {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setQuestionOrder(questions.getQuestionOrder());
            questionDto.setQuestionText(questions.getQuestionText());
            questionDto.setId(questions.getId());
            questionDto.setOptionDtos(convertOptions(questions));
            questionDtos.add(questionDto);
        });

        return questionDtos.stream().toList();
    }

    public List<OptionDto> convertOptions(Questions questions) {
        List<OptionDto> optionDtos = new ArrayList<>();
        questions.getOptions().forEach(options -> {
            OptionDto optionDto = new OptionDto();
            optionDto.setId(options.getId());
            optionDto.setOptionText(options.getOptionText());
            optionDto.setIsCorrect(options.getIsCorrect());
            optionDtos.add(optionDto);
        });

        return optionDtos.stream().toList();
    }
}
