package com.aura.syntax.ausempi.demo.converter;

import com.aura.syntax.ausempi.demo.api.dto.OptionDto;
import com.aura.syntax.ausempi.demo.api.dto.QuestionDto;
import com.aura.syntax.ausempi.demo.api.dto.VideoDto;
import com.aura.syntax.ausempi.demo.entity.Options;
import com.aura.syntax.ausempi.demo.entity.Questions;
import com.aura.syntax.ausempi.demo.entity.Videos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class VideoConverter {

    public Videos convert(VideoDto videoDto){
        return Videos.builder()
                .id(videoDto.getId())
                .title(videoDto.getTitle())
                .description(videoDto.getDescription())
                .videoUrl(videoDto.getVideoUrl())
                .createdAt(LocalDateTime.now())
                .durationSeconds(videoDto.getDurationSeconds())
                .questions(convertQuestions(videoDto))
                .build();
    }

    public Set<Questions> convertQuestions(VideoDto videoDto){
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

    public Set<Options> convertOptions(QuestionDto questionDto){
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

    public VideoDto convert(Videos videos){
        return VideoDto.builder()
                .id(videos.getId())
                .title(videos.getTitle())
                .description(videos.getDescription())
                .videoUrl(videos.getVideoUrl())
                .createdAt(LocalDateTime.now())
                .durationSeconds(videos.getDurationSeconds())
                .questionDtos(convertQuestions(videos))
                .build();
    }

    public List<QuestionDto> convertQuestions(Videos videos){
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

    public List<OptionDto> convertOptions(Questions questions){
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
