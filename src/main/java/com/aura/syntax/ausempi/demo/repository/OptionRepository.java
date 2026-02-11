package com.aura.syntax.ausempi.demo.repository;

import com.aura.syntax.ausempi.demo.entity.Options;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Options, Long> {
}
