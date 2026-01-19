package com.catalog.api.service;

import com.catalog.api.domain.CategoryEntity;
import com.catalog.api.domain.SubcategoryEntity;
import com.catalog.api.dto.SubcategoryDTO;
import com.catalog.api.exception.BusinessException;
import com.catalog.api.exception.ResourceNotFoundException;
import com.catalog.api.repository.CategoryRepository;
import com.catalog.api.repository.EntryRepository;
import com.catalog.api.repository.SubcategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final EntryRepository entryRepository;

    public SubcategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository,
            EntryRepository entryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
        this.entryRepository = entryRepository;
    }

    public List<SubcategoryDTO> findAll(String name) {
        List<SubcategoryEntity> subcategories;
        if (name != null && !name.isBlank()) {
            subcategories = subcategoryRepository.findByNameContainingIgnoreCase(name);
        } else {
            subcategories = subcategoryRepository.findAll();
        }
        return subcategories.stream().map(SubcategoryDTO::new).collect(Collectors.toList());
    }

    public SubcategoryDTO findById(Long id) {
        SubcategoryEntity subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoria não encontrada"));
        return new SubcategoryDTO(subcategory);
    }

    @Transactional
    public SubcategoryDTO create(SubcategoryDTO subcategoryDTO) {
        CategoryEntity category = categoryRepository.findById(subcategoryDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (subcategoryRepository.existsByCategoryIdAndName(subcategoryDTO.getCategoryId(), subcategoryDTO.getName())) {
            throw new BusinessException("Já existe uma subcategoria com este nome nesta categoria");
        }

        SubcategoryEntity subcategory = new SubcategoryEntity();
        subcategory.setName(subcategoryDTO.getName());
        subcategory.setCategory(category);

        subcategory = subcategoryRepository.save(subcategory);
        return new SubcategoryDTO(subcategory);
    }

    @Transactional
    public SubcategoryDTO update(Long id, SubcategoryDTO subcategoryDTO) {
        SubcategoryEntity subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoria não encontrada"));

        CategoryEntity category = categoryRepository.findById(subcategoryDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (!subcategory.getName().equals(subcategoryDTO.getName()) ||
                !subcategory.getCategory().getId().equals(subcategoryDTO.getCategoryId())) {
            if (subcategoryRepository.existsByCategoryIdAndName(subcategoryDTO.getCategoryId(),
                    subcategoryDTO.getName())) {
                throw new BusinessException("Já existe uma subcategoria com este nome nesta categoria");
            }
        }

        subcategory.setName(subcategoryDTO.getName());
        subcategory.setCategory(category);
        subcategory = subcategoryRepository.save(subcategory);
        return new SubcategoryDTO(subcategory);
    }

    @Transactional
    public void delete(Long id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subcategoria não encontrada");
        }
        if (entryRepository.existsBySubcategoryId(id)) {
            throw new BusinessException("Não é possível excluir subcategoria com lançamentos associados");
        }
        subcategoryRepository.deleteById(id);
    }
}
