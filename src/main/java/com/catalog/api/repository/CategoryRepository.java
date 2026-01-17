package com.catalog.api.repository;

import com.catalog.api.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}
