package com.vs.couponsbackmysqljwt.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Coupon object
 */
@Component
@Data
@Entity
@Table(name = "COUPONS", indexes = @Index(name = "TitleIndex", columnList = "COMPANY_ID, TITLE", unique = true))
@AllArgsConstructor
@NoArgsConstructor
@Lazy
@Builder
@JsonIgnoreProperties("purchase")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @Column(nullable = false, name = "ID", updatable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    @JsonBackReference
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    @JsonBackReference
    private Category category;

    @Column(nullable = false, length = 100, name = "TITLE")
    private String title;

    @Column(nullable = false, length = 100, name = "DESCRIPTION")
    private String description;

    @Column(nullable = false, name = "START_DATE")
    private Date startDate;

    @Column(nullable = false, name = "END_DATE")
    private Date endDate;

    @Column(nullable = false, name = "AMOUNT")
    private int amount;

    @Column(nullable = false, name = "PRICE")
    private double price;

    @Column(nullable = false, length = 100, name = "IMAGE")
    private String image;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) //cascade = CascadeType.ALL
    @JsonManagedReference
    private List<Purchase> purchase = new ArrayList<>();

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", company=" + company +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
