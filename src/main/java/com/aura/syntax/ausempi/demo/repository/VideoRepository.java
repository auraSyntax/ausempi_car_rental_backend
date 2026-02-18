package com.aura.syntax.ausempi.demo.repository;

import com.aura.syntax.ausempi.demo.api.dto.VideoDto;
import com.aura.syntax.ausempi.demo.entity.Videos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Videos, Long> {
    @Query("""
                SELECT new com.aura.syntax.ausempi.demo.api.dto.VideoDto(
                    v.id,
                    v.title,
                    v.description,
                    CASE WHEN v.videoUrl IS NOT NULL THEN CONCAT(RTRIM(:videoUrl),LTRIM(v.videoUrl)) ELSE NULL END AS videoUrl,
                    v.durationSeconds,
                    v.createdAt,
                    v.videoOrder
                )
                FROM Videos v
                WHERE (
                    :search IS NULL OR :search = '' OR
                    LOWER(v.title) LIKE LOWER(CONCAT('%', :search, '%')) OR
                    LOWER(v.description) LIKE LOWER(CONCAT('%', :search, '%'))
                )
                ORDER BY v.createdAt DESC
            """)
    Page<VideoDto> getAllVideosPagination(Pageable pageable, String search, String videoUrl);

    @Query("""
            SELECT new com.aura.syntax.ausempi.demo.api.dto.VideoDto(
                    v.id,
                    v.title,
                    v.description,
                    CASE WHEN v.videoUrl IS NOT NULL THEN CONCAT(RTRIM(:videoUrl),LTRIM(v.videoUrl)) ELSE NULL END AS videoUrl,
                    v.durationSeconds,
                    v.createdAt,
                    v.videoOrder
                )
                FROM Videos v
            """)
    List<VideoDto> getAllVideos(String videoUrl);
}
