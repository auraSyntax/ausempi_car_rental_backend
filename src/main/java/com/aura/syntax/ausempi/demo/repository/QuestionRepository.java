package com.aura.syntax.ausempi.demo.repository;

import com.aura.syntax.ausempi.demo.api.dto.QuestionDto;
import com.aura.syntax.ausempi.demo.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Questions, Long> {

    @Query("""
                SELECT new com.aura.syntax.ausempi.demo.api.dto.QuestionDto(
                    q.id,
                    q.questionText
                )
                FROM Questions q
                WHERE q.videoId = :videoId
                AND (
                    :search IS NULL OR :search = '' OR
                    LOWER(q.questionText) LIKE LOWER(CONCAT('%', :search, '%'))
                )
                ORDER BY q.questionOrder ASC
            """)
    List<QuestionDto> getAllQuestionsByVideoId(Long videoId, String search);
}
