package com.ecommerce.ShopZenbe.models.address;

import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
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

    @Column(name = "phone")
    private String phone;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_billing")
    private Boolean isBilling;

    @Column(name = "is_shipping")
    private Boolean isShipping;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @Column(name = "date_created")
    @CreationTimestamp
    private Timestamp dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Timestamp lastUpdated;

//    @OneToOne
//    @JsonIgnore
//    @PrimaryKeyJoinColumn
//    private Order order;
}
