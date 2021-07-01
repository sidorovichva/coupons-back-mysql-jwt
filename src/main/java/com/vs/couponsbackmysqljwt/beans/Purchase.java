package com.vs.couponsbackmysqljwt.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@Entity
@Table(name = "CUSTOMERS_VS_COUPONS", indexes = @Index(name = "purchaseIndex", columnList = "COUPON_ID, CUSTOMER_ID", unique = true))
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {

    @EmbeddedId
    private PurchaseID id = new PurchaseID();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("couponID")
    @JoinColumn(name = "COUPON_ID", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("customerID")
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    public Purchase(Coupon coupon, Customer customer) {
        this.coupon = coupon;
        this.customer = customer;
    }
}

//package com.vs.couponsbackmysqljwt.beans;
//
//        import com.fasterxml.jackson.annotation.JsonBackReference;
//        import lombok.AllArgsConstructor;
//        import lombok.Builder;
//        import lombok.Data;
//        import lombok.NoArgsConstructor;
//        import org.springframework.stereotype.Component;
//
//        import javax.persistence.*;
//
//@Component
//@Data
//@Entity
//@Table(name = "CUSTOMERS_VS_COUPONS", indexes = @Index(name = "purchaseIndex", columnList = "COUPON_ID, CUSTOMER_ID", unique = true))
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Purchase {
//
//    @EmbeddedId
//    private PurchaseID id = new PurchaseID();
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("couponID")
//    @JoinColumn(name = "COUPON_ID", nullable = false)
//    @JsonBackReference
//    private Coupon coupon;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("customerID")
//    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
//    @JsonBackReference
//    private Customer customer;
//
//    public Purchase(Coupon coupon, Customer customer) {
//        this.coupon = coupon;
//        this.customer = customer;
//    }
//}
