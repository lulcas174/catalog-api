package com.catalog.api.service;

import com.catalog.api.domain.CategoryEntity;
import com.catalog.api.domain.EntryEntity;
import com.catalog.api.dto.BalanceDTO;
import com.catalog.api.dto.CategorySummaryDTO;
import com.catalog.api.repository.CategoryRepository;
import com.catalog.api.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BalanceService {

    private final EntryRepository entryRepository;
    private final CategoryRepository categoryRepository;

    public BalanceService(EntryRepository entryRepository, CategoryRepository categoryRepository) {
        this.entryRepository = entryRepository;
        this.categoryRepository = categoryRepository;
    }

    public BalanceDTO calculateBalance(LocalDate dataInicio, LocalDate dataFim, Long idCategoria) {
        List<EntryEntity> entries;

        if (idCategoria != null) {
            CategoryEntity category = categoryRepository.findById(idCategoria)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            entries = entryRepository.findByDateBetweenAndSubcategory_Category_Id(dataInicio, dataFim, idCategoria);

            BalanceDTO balance = calculateBalanceFromEntries(entries);
            balance.setCategory(new CategorySummaryDTO(category));
            return balance;
        } else {
            entries = entryRepository.findByDateBetween(dataInicio, dataFim);
            return calculateBalanceFromEntries(entries);
        }
    }

    private BalanceDTO calculateBalanceFromEntries(List<EntryEntity> entries) {
        BigDecimal income = entries.stream()
                .map(EntryEntity::getValue)
                .filter(value -> value.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = entries.stream()
                .map(EntryEntity::getValue)
                .filter(value -> value.compareTo(BigDecimal.ZERO) < 0)
                .map(BigDecimal::abs)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = income.subtract(expense);

        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setIncome(income);
        balanceDTO.setExpense(expense);
        balanceDTO.setBalance(balance);
        return balanceDTO;
    }
}
