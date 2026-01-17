package com.catalog.api.repository;

import com.catalog.api.domain.EntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<EntryEntity, Long> {
    List<EntryEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsBySubcategoryId(Long subcategoryId);
}
