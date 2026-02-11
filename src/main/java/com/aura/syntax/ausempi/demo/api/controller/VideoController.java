package com.aura.syntax.ausempi.demo.api.controller;

import com.aura.syntax.ausempi.demo.api.dto.ResponseDto;
import com.aura.syntax.ausempi.demo.api.dto.VideoDto;
import com.aura.syntax.ausempi.demo.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public ResponseDto saveVideoQuestionsOptions(@RequestBody VideoDto videoDto){
        return videoService.saveVideoQuestionsOptions(videoDto);
    }

//    @GetMapping
//    public List<VideoDto> getAllVideos(){
//        return videoService.getAllVideos()
//    }
}
