package com.catalog.api.service;

import com.catalog.api.domain.EntryEntity;
import com.catalog.api.domain.SubcategoryEntity;
import com.catalog.api.dto.EntryDTO;
import com.catalog.api.repository.EntryRepository;
import com.catalog.api.repository.SubcategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntryService {

    private final EntryRepository entryRepository;
    private final SubcategoryRepository subcategoryRepository;

    public EntryService(EntryRepository entryRepository, SubcategoryRepository subcategoryRepository) {
        this.entryRepository = entryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    public List<EntryDTO> findAll(LocalDate dataInicio, LocalDate dataFim) {
        List<EntryEntity> entries;
        if (dataInicio != null && dataFim != null) {
            entries = entryRepository.findByDateBetween(dataInicio, dataFim);
        } else {
            entries = entryRepository.findAll();
        }
        return entries.stream().map(EntryDTO::new).collect(Collectors.toList());
    }

    public EntryDTO findById(Long id) {
        EntryEntity entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lançamento não encontrado"));
        return new EntryDTO(entry);
    }

    @Transactional
    public EntryDTO create(EntryDTO entryDTO) {
        SubcategoryEntity subcategory = subcategoryRepository.findById(entryDTO.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategoria não encontrada"));

        EntryEntity entry = new EntryEntity();
        entry.setValue(entryDTO.getValue());
        entry.setDate(entryDTO.getDate() != null ? entryDTO.getDate() : LocalDate.now());
        entry.setSubcategory(subcategory);
        entry.setComment(entryDTO.getComment());

        entry = entryRepository.save(entry);
        return new EntryDTO(entry);
    }

    @Transactional
    public EntryDTO update(Long id, EntryDTO entryDTO) {
        EntryEntity entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lançamento não encontrado"));

        SubcategoryEntity subcategory = subcategoryRepository.findById(entryDTO.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategoria não encontrada"));

        entry.setValue(entryDTO.getValue());
        entry.setDate(entryDTO.getDate() != null ? entryDTO.getDate() : entry.getDate());
        entry.setSubcategory(subcategory);
        entry.setComment(entryDTO.getComment());

        entry = entryRepository.save(entry);
        return new EntryDTO(entry);
    }

    @Transactional
    public void delete(Long id) {
        if (!entryRepository.existsById(id)) {
            throw new RuntimeException("Lançamento não encontrado");
        }
        entryRepository.deleteById(id);
    }
}
