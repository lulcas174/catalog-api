package com.catalog.api.controller;

import com.catalog.api.dto.EntryDTO;
import com.catalog.api.service.EntryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntryControllerTest {

    @InjectMocks
    private EntryController entryController;

    @Mock
    private EntryService entryService;

    @Test
    void shouldReturnAllEntries() {
        EntryDTO entryDTO = new EntryDTO();
        entryDTO.setId(1L);
        entryDTO.setValue(new BigDecimal("200.00"));
        entryDTO.setDate(LocalDate.of(2021, 1, 1));
        entryDTO.setSubcategoryId(1L);
        when(entryService.findAll(null, null)).thenReturn(Collections.singletonList(entryDTO));

        ResponseEntity<List<EntryDTO>> response = entryController.findAll(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(new BigDecimal("200.00"), response.getBody().get(0).getValue());
    }

    @Test
    void shouldReturnEntryById() {
        EntryDTO entryDTO = new EntryDTO();
        entryDTO.setId(1L);
        entryDTO.setValue(new BigDecimal("200.00"));
        entryDTO.setDate(LocalDate.of(2021, 1, 1));
        entryDTO.setSubcategoryId(1L);
        when(entryService.findById(1L)).thenReturn(entryDTO);

        ResponseEntity<EntryDTO> response = entryController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("200.00"), response.getBody().getValue());
    }

    @Test
    void shouldCreateEntry() {
        EntryDTO inputDTO = new EntryDTO();
        inputDTO.setValue(new BigDecimal("200.00"));
        inputDTO.setDate(LocalDate.of(2021, 1, 1));
        inputDTO.setSubcategoryId(1L);

        EntryDTO outputDTO = new EntryDTO();
        outputDTO.setId(1L);
        outputDTO.setValue(new BigDecimal("200.00"));
        outputDTO.setDate(LocalDate.of(2021, 1, 1));
        outputDTO.setSubcategoryId(1L);

        when(entryService.create(inputDTO)).thenReturn(outputDTO);

        ResponseEntity<EntryDTO> response = entryController.create(inputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldUpdateEntry() {
        EntryDTO inputDTO = new EntryDTO();
        inputDTO.setValue(new BigDecimal("250.00"));
        inputDTO.setDate(LocalDate.of(2021, 1, 2));
        inputDTO.setSubcategoryId(1L);

        EntryDTO outputDTO = new EntryDTO();
        outputDTO.setId(1L);
        outputDTO.setValue(new BigDecimal("250.00"));
        outputDTO.setDate(LocalDate.of(2021, 1, 2));
        outputDTO.setSubcategoryId(1L);

        when(entryService.update(1L, inputDTO)).thenReturn(outputDTO);

        ResponseEntity<EntryDTO> response = entryController.update(1L, inputDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("250.00"), response.getBody().getValue());
    }

    @Test
    void shouldDeleteEntry() {
        doNothing().when(entryService).delete(1L);

        ResponseEntity<Void> response = entryController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(entryService, times(1)).delete(1L);
    }
}
