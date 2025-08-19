package com.avenga.fil.lt.service;

import com.avenga.fil.lt.model.ExtractDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractRepository extends JpaRepository<ExtractDataEntity, Long> {
}
