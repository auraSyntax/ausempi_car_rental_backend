package com.aura.syntax.ausempi.demo.service;

import com.aura.syntax.ausempi.demo.api.dto.*;
import com.aura.syntax.ausempi.demo.converter.VideoConverter;
import com.aura.syntax.ausempi.demo.entity.Videos;
import com.aura.syntax.ausempi.demo.exception.ServiceException;
import com.aura.syntax.ausempi.demo.repository.OptionRepository;
import com.aura.syntax.ausempi.demo.repository.QuestionRepository;
import com.aura.syntax.ausempi.demo.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoConverter videoConverter;

    private final VideoRepository videoRepository;

    @Value("${cloudinary.base.url}")
    private String videoUrl;

    private final QuestionRepository questionRepository;

    private final OptionRepository optionRepository;

    public ResponseDto saveVideoQuestionsOptions(VideoDto videoDto) {
        videoRepository.save(videoConverter.convert(videoDto));
        return new ResponseDto("Video saved successfully");
    }

    public VideoDto getVideoQuestionsOptionsById(Long videoId) {
        Videos videos = videoRepository.findById(videoId).orElseThrow(() -> new ServiceException("Video not found", "Bad request", HttpStatus.BAD_REQUEST));
        return videoConverter.convert(videos);
    }

    public ResponseDto deleteVideo(Long videoId) {
        videoRepository.findById(videoId).orElseThrow(() -> new ServiceException("Video not found", "Bad request", HttpStatus.BAD_REQUEST));
        videoRepository.deleteById(videoId);
        return new ResponseDto("Video deleted successfully");
    }

    public PaginatedResponseDto<VideoDto> getAllVideosPagination(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<VideoDto> videoDtos = videoRepository.getAllVideosPagination(pageable, search, videoUrl);
        List<VideoDto> videoDtoList = videoDtos.getContent();

        videoDtoList.forEach(videoDto -> {
            List<QuestionDto> questionDtos = questionRepository.getAllQuestionsByVideoId(videoDto.getId(), search);
            videoDto.setQuestionDtos(questionDtos);

            questionDtos.forEach(questionDto -> {
                List<OptionDto> optionDtos = optionRepository.getAllOptionsByQuestionId(questionDto.getId(), search);
                questionDto.setOptionDtos(optionDtos);
            });
        });

        PaginatedResponseDto<VideoDto> videoDtoPaginatedResponseDto = new PaginatedResponseDto<>();
        videoDtoPaginatedResponseDto.setData(videoDtoList);
        videoDtoPaginatedResponseDto.setCurrentPage(page);
        videoDtoPaginatedResponseDto.setTotalItems(videoDtos.getTotalElements());
        videoDtoPaginatedResponseDto.setTotalPages(videoDtos.getTotalPages());
        videoDtoPaginatedResponseDto.setPageSize(size);
        videoDtoPaginatedResponseDto.setHasPrevious(page > 1);
        videoDtoPaginatedResponseDto.setHasNext(page < videoDtos.getTotalPages());

        return videoDtoPaginatedResponseDto;
    }

    public ResponseDto updateVideoQuestionsOptions(VideoDto videoDto) {
        Videos videos = videoRepository.findById(videoDto.getId()).orElseThrow(() -> new ServiceException("Video not found", "Bad request", HttpStatus.BAD_REQUEST));
        videoRepository.save(videoConverter.convert(videoDto, videos));
        return new ResponseDto("Video updated successfully");
    }
}
