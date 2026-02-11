package com.aura.syntax.ausempi.demo.service;

import com.aura.syntax.ausempi.demo.api.dto.ResponseDto;
import com.aura.syntax.ausempi.demo.api.dto.VideoDto;
import com.aura.syntax.ausempi.demo.converter.VideoConverter;
import com.aura.syntax.ausempi.demo.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoConverter videoConverter;

    private final VideoRepository videoRepository;

    public ResponseDto saveVideoQuestionsOptions(VideoDto videoDto) {
        videoRepository.save(videoConverter.convert(videoDto));
        return new ResponseDto("Video saved");
    }
}
