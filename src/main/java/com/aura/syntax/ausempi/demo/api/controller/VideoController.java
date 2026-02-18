package com.aura.syntax.ausempi.demo.api.controller;

import com.aura.syntax.ausempi.demo.api.dto.PaginatedResponseDto;
import com.aura.syntax.ausempi.demo.api.dto.ResponseDto;
import com.aura.syntax.ausempi.demo.api.dto.VideoDto;
import com.aura.syntax.ausempi.demo.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public ResponseDto saveVideoQuestionsOptions(@RequestBody VideoDto videoDto) {
        return videoService.saveVideoQuestionsOptions(videoDto);
    }

    @GetMapping("/{videoId}")
    public VideoDto getVideoQuestionsOptionsById(@PathVariable(value = "videoId") Long videoId) {
        return videoService.getVideoQuestionsOptionsById(videoId);
    }

    @DeleteMapping("/{videoId}")
    public ResponseDto deleteVideo(@PathVariable(value = "videoId") Long videoId) {
        return videoService.deleteVideo(videoId);
    }

    @GetMapping
    public PaginatedResponseDto<VideoDto> getAllVideosPagination(@RequestParam(value = "page", required = false) int page,
                                                                 @RequestParam(value = "size", required = false) int size,
                                                                 @RequestParam(value = "search", required = false) String search) {
        return videoService.getAllVideosPagination(page, size, search);
    }

    @PatchMapping
    public ResponseDto updateVideoQuestionsOptions(@RequestBody VideoDto videoDto) {
        return videoService.updateVideoQuestionsOptions(videoDto);
    }

    @GetMapping("/all")
    public List<VideoDto> getAllVideos(){
        return videoService.getAllVideos();
    }
}
