package com.ecommerce.ShopZenbe.models.productCategory;

import com.ecommerce.ShopZenbe.common.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO {
    @NotNull(message = "Category name is mandatory")
    @Enumerated(EnumType.STRING)
    private Category categoryName;
}
