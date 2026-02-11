package com.aura.syntax.ausempi.demo.repository;

import com.aura.syntax.ausempi.demo.entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Videos, Long> {
}
