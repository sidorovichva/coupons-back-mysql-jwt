package com.vs.couponsbackmysqljwt.repositories;

import com.vs.couponsbackmysqljwt.beans.Coupon;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.beans.Purchase;
import com.vs.couponsbackmysqljwt.beans.PurchaseID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, PurchaseID> {

    boolean existsByCouponAndCustomer(Coupon coupon, Customer customer);

    List<Purchase> getPurchasesByCustomer (Customer customer);
}
