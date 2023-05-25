package com.ecommerce.ShopZenbe.models.address;

import com.ecommerce.ShopZenbe.models.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "street")
    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "full_name")
    private String fullName;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;
}
