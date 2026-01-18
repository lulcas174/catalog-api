package com.catalog.api.controller;

import com.catalog.api.dto.BalanceDTO;
import com.catalog.api.service.BalanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/balanço")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping
    public ResponseEntity<BalanceDTO> getBalance(
            @RequestParam(name = "data_inicio", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @RequestParam(name = "data_fim", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim,
            @RequestParam(name = "id_categoria", required = false) Long idCategoria) {
        return ResponseEntity.ok(balanceService.calculateBalance(dataInicio, dataFim, idCategoria));
    }
}
