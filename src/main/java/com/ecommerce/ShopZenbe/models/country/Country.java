package com.ecommerce.ShopZenbe.models.country;

import com.ecommerce.ShopZenbe.models.state.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "country")
@Getter
@Setter
public class Country {
    @Id
    @Column(name = "id")
    private Short id;

    @Column(name = "code", length = 2)
    private String code;

    @Column(name = "name", length = 255)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<State> states;

}
