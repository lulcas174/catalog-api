package com.catalog.api.dto;

import com.catalog.api.domain.EntryEntity;
import com.catalog.api.validation.NotZero;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EntryDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("id_lancamento")
    private Long id;

    @NotNull(message = "O valor é obrigatório")
    @NotZero
    @JsonProperty("valor")
    private BigDecimal value;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("data")
    private LocalDate date;

    @NotNull(message = "A subcategoria é obrigatória")
    @JsonProperty("id_subcategoria")
    private Long subcategoryId;

    @JsonProperty("comentario")
    private String comment;

    public EntryDTO(EntryEntity entry) {
        this.id = entry.getId();
        this.value = entry.getValue();
        this.date = entry.getDate();
        this.subcategoryId = entry.getSubcategory().getId();
        this.comment = entry.getComment();
    }
}
