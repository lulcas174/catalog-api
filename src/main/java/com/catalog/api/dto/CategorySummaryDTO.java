package com.catalog.api.dto;

import com.catalog.api.domain.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategorySummaryDTO {

    @JsonProperty("id_categoria")
    private Long id;

    @JsonProperty("nome")
    private String name;

    public CategorySummaryDTO(CategoryEntity category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
