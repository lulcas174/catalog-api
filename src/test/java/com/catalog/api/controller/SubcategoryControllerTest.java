package com.catalog.api.controller;

import com.catalog.api.dto.SubcategoryDTO;
import com.catalog.api.service.SubcategoryService;
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
class SubcategoryControllerTest {

    @InjectMocks
    private SubcategoryController subcategoryController;

    @Mock
    private SubcategoryService subcategoryService;

    @Test
    void shouldReturnAllSubcategories() {
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
        subcategoryDTO.setId(1L);
        subcategoryDTO.setName("Combustível");
        subcategoryDTO.setCategoryId(1L);
        when(subcategoryService.findAll(null)).thenReturn(Collections.singletonList(subcategoryDTO));

        ResponseEntity<List<SubcategoryDTO>> response = subcategoryController.findAll(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Combustível", response.getBody().get(0).getName());
    }

    @Test
    void shouldReturnSubcategoryById() {
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
        subcategoryDTO.setId(1L);
        subcategoryDTO.setName("Combustível");
        subcategoryDTO.setCategoryId(1L);
        when(subcategoryService.findById(1L)).thenReturn(subcategoryDTO);

        ResponseEntity<SubcategoryDTO> response = subcategoryController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Combustível", response.getBody().getName());
    }

    @Test
    void shouldCreateSubcategory() {
        SubcategoryDTO inputDTO = new SubcategoryDTO();
        inputDTO.setName("Combustível");
        inputDTO.setCategoryId(1L);

        SubcategoryDTO outputDTO = new SubcategoryDTO();
        outputDTO.setId(1L);
        outputDTO.setName("Combustível");
        outputDTO.setCategoryId(1L);

        when(subcategoryService.create(inputDTO)).thenReturn(outputDTO);

        ResponseEntity<SubcategoryDTO> response = subcategoryController.create(inputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldUpdateSubcategory() {
        SubcategoryDTO inputDTO = new SubcategoryDTO();
        inputDTO.setName("Combustível Editado");
        inputDTO.setCategoryId(1L);

        SubcategoryDTO outputDTO = new SubcategoryDTO();
        outputDTO.setId(1L);
        outputDTO.setName("Combustível Editado");
        outputDTO.setCategoryId(1L);

        when(subcategoryService.update(1L, inputDTO)).thenReturn(outputDTO);

        ResponseEntity<SubcategoryDTO> response = subcategoryController.update(1L, inputDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Combustível Editado", response.getBody().getName());
    }

    @Test
    void shouldDeleteSubcategory() {
        doNothing().when(subcategoryService).delete(1L);

        ResponseEntity<Void> response = subcategoryController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(subcategoryService, times(1)).delete(1L);
    }
}
