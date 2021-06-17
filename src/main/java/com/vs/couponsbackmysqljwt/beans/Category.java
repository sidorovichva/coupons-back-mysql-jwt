package com.vs.couponsbackmysqljwt.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
@Entity
@Table(name = "CATEGORIES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties("coupons")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @Column(nullable = false, name = "ID", updatable = false)
    private int id;

    @Column(nullable = false, length = 40, name = "NAME", unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Coupon> coupons = new ArrayList<>();

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id +". " + this.name;
    }
}
