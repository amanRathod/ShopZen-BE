package com.ecommerce.ShopZenbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "country")
@Data
public class Country {
    @Id
    @Column(name = "id")
    private Short id;

    @Column(name = "code", length = 2)
    private String code;

    @Column(name = "name", length = 255)
    private String name;
}
