package com.catalog.api.dto;

import com.catalog.api.domain.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {

    @JsonProperty("id_categoria")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @JsonProperty("nome")
    private String name;

    public CategoryDTO(CategoryEntity category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public CategoryEntity toEntity() {
        CategoryEntity category = new CategoryEntity();
        category.setId(this.id);
        category.setName(this.name);
        return category;
    }
}
