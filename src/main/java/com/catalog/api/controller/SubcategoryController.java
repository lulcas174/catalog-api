package com.catalog.api.controller;

import com.catalog.api.dto.SubcategoryDTO;
import com.catalog.api.service.SubcategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subcategorias")
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public ResponseEntity<List<SubcategoryDTO>> findAll(@RequestParam(required = false) String nome) {
        return ResponseEntity.ok(subcategoryService.findAll(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(subcategoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubcategoryDTO> create(@RequestBody @Valid SubcategoryDTO subcategoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subcategoryService.create(subcategoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> update(@PathVariable Long id,
            @RequestBody @Valid SubcategoryDTO subcategoryDTO) {
        return ResponseEntity.ok(subcategoryService.update(id, subcategoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subcategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
