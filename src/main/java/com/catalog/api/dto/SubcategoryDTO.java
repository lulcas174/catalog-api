package com.catalog.api.dto;

import com.catalog.api.domain.SubcategoryEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubcategoryDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("id_subcategoria")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @JsonProperty("nome")
    private String name;

    @NotNull(message = "A categoria é obrigatória")
    @JsonProperty("id_categoria")
    private Long categoryId;

    public SubcategoryDTO(SubcategoryEntity subcategory) {
        this.id = subcategory.getId();
        this.name = subcategory.getName();
        this.categoryId = subcategory.getCategory().getId();
    }
}
