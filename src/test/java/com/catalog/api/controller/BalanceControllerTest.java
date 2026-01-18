package com.catalog.api.controller;

import com.catalog.api.dto.BalanceDTO;
import com.catalog.api.dto.CategorySummaryDTO;
import com.catalog.api.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceControllerTest {

    @InjectMocks
    private BalanceController balanceController;

    @Mock
    private BalanceService balanceService;

    @Test
    void shouldReturnBalanceWithoutCategory() {
        LocalDate dataInicio = LocalDate.of(2021, 1, 1);
        LocalDate dataFim = LocalDate.of(2021, 1, 31);

        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setIncome(new BigDecimal("2320.00"));
        balanceDTO.setExpense(new BigDecimal("1000.00"));
        balanceDTO.setBalance(new BigDecimal("1320.00"));

        when(balanceService.calculateBalance(dataInicio, dataFim, null)).thenReturn(balanceDTO);

        ResponseEntity<BalanceDTO> response = balanceController.getBalance(dataInicio, dataFim, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("2320.00"), response.getBody().getIncome());
        assertEquals(new BigDecimal("1000.00"), response.getBody().getExpense());
        assertEquals(new BigDecimal("1320.00"), response.getBody().getBalance());
        assertNull(response.getBody().getCategory());
    }

    @Test
    void shouldReturnBalanceWithCategory() {
        LocalDate dataInicio = LocalDate.of(2021, 1, 1);
        LocalDate dataFim = LocalDate.of(2021, 1, 31);
        Long idCategoria = 1L;

        CategorySummaryDTO categoryDTO = new CategorySummaryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Transporte");

        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setCategory(categoryDTO);
        balanceDTO.setIncome(new BigDecimal("2320.00"));
        balanceDTO.setExpense(new BigDecimal("1000.00"));
        balanceDTO.setBalance(new BigDecimal("1320.00"));

        when(balanceService.calculateBalance(dataInicio, dataFim, idCategoria)).thenReturn(balanceDTO);

        ResponseEntity<BalanceDTO> response = balanceController.getBalance(dataInicio, dataFim, idCategoria);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("2320.00"), response.getBody().getIncome());
        assertEquals(new BigDecimal("1000.00"), response.getBody().getExpense());
        assertEquals(new BigDecimal("1320.00"), response.getBody().getBalance());
        assertEquals("Transporte", response.getBody().getCategory().getName());
    }
}
