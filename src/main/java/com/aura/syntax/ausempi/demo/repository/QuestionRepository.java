package com.aura.syntax.ausempi.demo.repository;

import com.aura.syntax.ausempi.demo.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Questions, Long> {
}
