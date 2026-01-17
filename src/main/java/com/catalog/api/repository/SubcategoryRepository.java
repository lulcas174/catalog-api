package com.catalog.api.repository;

import com.catalog.api.domain.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {
    List<SubcategoryEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByCategoryIdAndName(Long categoryId, String name);
}
