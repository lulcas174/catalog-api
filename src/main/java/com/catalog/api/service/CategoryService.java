package com.catalog.api.service;

import com.catalog.api.domain.CategoryEntity;
import com.catalog.api.dto.CategoryDTO;
import com.catalog.api.exception.BusinessException;
import com.catalog.api.exception.ResourceNotFoundException;
import com.catalog.api.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAll(String name) {
        List<CategoryEntity> categories;
        if (name != null && !name.isBlank()) {
            categories = categoryRepository.findByNameContainingIgnoreCase(name);
        } else {
            categories = categoryRepository.findAll();
        }
        return categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    public CategoryDTO findById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO create(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new BusinessException("Já existe uma categoria com este nome");
        }
        CategoryEntity category = categoryDTO.toEntity();
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (!category.getName().equals(categoryDTO.getName())
                && categoryRepository.existsByName(categoryDTO.getName())) {
            throw new BusinessException("Já existe uma categoria com este nome");
        }

        category.setName(categoryDTO.getName());
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada");
        }
        categoryRepository.deleteById(id);
    }
}
