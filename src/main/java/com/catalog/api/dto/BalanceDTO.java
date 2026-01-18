package com.catalog.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceDTO {

    @JsonProperty("categoria")
    private CategorySummaryDTO category;

    @JsonProperty("receita")
    private BigDecimal income;

    @JsonProperty("despesa")
    private BigDecimal expense;

    @JsonProperty("saldo")
    private BigDecimal balance;
}
