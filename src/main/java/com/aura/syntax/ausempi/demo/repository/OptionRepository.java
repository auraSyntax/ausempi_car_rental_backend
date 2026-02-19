package com.aura.syntax.ausempi.demo.repository;

import com.aura.syntax.ausempi.demo.api.dto.OptionDto;
import com.aura.syntax.ausempi.demo.entity.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Options, Long> {

    @Query("""
                SELECT new com.aura.syntax.ausempi.demo.api.dto.OptionDto(
                    o.id,
                    o.optionText,
                    o.isCorrect
                )
                FROM Options o
                WHERE o.questionId = :questionId
                AND (
                    :search IS NULL OR :search = '' OR
                    LOWER(o.optionText) LIKE LOWER(CONCAT('%', :search, '%'))
                )
                ORDER BY o.id ASC
            """)
    List<OptionDto> getAllOptionsByQuestionId(Long questionId, String search);

    List<Options> findByQuestionIdIn(List<Long> questionIds);
}
