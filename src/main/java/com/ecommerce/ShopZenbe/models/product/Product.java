package com.ecommerce.ShopZenbe.models.product;

import com.ecommerce.ShopZenbe.models.productCategory.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    @JsonProperty("id")
    private UUID id;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "name", nullable = false)
//    @JsonIgnore
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "unit_price", nullable = false, precision = 13, scale = 2)
    private BigDecimal price;

    @Column(name = "image_url", nullable = false)
    private String image;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "units_in_stock", nullable = false)
    private Integer stock;

    @Column(name = "date_created", nullable = false)
//    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(name = "last_updated")
//    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id",  referencedColumnName = "id")
    private ProductCategory category;

}
