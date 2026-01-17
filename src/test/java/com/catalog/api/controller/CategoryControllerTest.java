package com.catalog.api.controller;

import com.catalog.api.dto.CategoryDTO;
import com.catalog.api.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Test
    void shouldReturnAllCategories() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Transporte");
        when(categoryService.findAll(null)).thenReturn(Collections.singletonList(categoryDTO));

        ResponseEntity<List<CategoryDTO>> response = categoryController.findAll(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Transporte", response.getBody().get(0).getName());
    }

    @Test
    void shouldReturnCategoryById() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Transporte");
        when(categoryService.findById(1L)).thenReturn(categoryDTO);

        ResponseEntity<CategoryDTO> response = categoryController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transporte", response.getBody().getName());
    }

    @Test
    void shouldCreateCategory() {
        CategoryDTO inputDTO = new CategoryDTO();
        inputDTO.setName("Transporte");

        CategoryDTO outputDTO = new CategoryDTO();
        outputDTO.setId(1L);
        outputDTO.setName("Transporte");

        when(categoryService.create(inputDTO)).thenReturn(outputDTO);

        ResponseEntity<CategoryDTO> response = categoryController.create(inputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldUpdateCategory() {
        CategoryDTO inputDTO = new CategoryDTO();
        inputDTO.setName("Transporte Editado");

        CategoryDTO outputDTO = new CategoryDTO();
        outputDTO.setId(1L);
        outputDTO.setName("Transporte Editado");

        when(categoryService.update(1L, inputDTO)).thenReturn(outputDTO);

        ResponseEntity<CategoryDTO> response = categoryController.update(1L, inputDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transporte Editado", response.getBody().getName());
    }

    @Test
    void shouldDeleteCategory() {
        doNothing().when(categoryService).delete(1L);

        ResponseEntity<Void> response = categoryController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService, times(1)).delete(1L);
    }
}
