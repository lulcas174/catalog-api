package com.catalog.api.controller;

import com.catalog.api.dto.EntryDTO;
import com.catalog.api.service.EntryService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping
    public ResponseEntity<List<EntryDTO>> findAll(
            @RequestParam(name = "data_inicio", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @RequestParam(name = "data_fim", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim) {
        return ResponseEntity.ok(entryService.findAll(dataInicio, dataFim));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(entryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EntryDTO> create(@RequestBody @Valid EntryDTO entryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entryService.create(entryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntryDTO> update(@PathVariable Long id, @RequestBody @Valid EntryDTO entryDTO) {
        return ResponseEntity.ok(entryService.update(id, entryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        entryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
