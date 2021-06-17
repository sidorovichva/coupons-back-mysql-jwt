package com.vs.couponsbackmysqljwt.repositories;

import com.vs.couponsbackmysqljwt.beans.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    boolean existsByTitleAndCompany(String title, Company company);

    boolean existsByTitleAndCompanyAndIdNot(String title, Company company, int couponID);

    boolean existsByIdAndCompany(int id, Company company);

    List<Coupon> getCouponsByEndDateBefore(Date date);

    List<Coupon> getCouponsByCompany(Company company);
    List<Coupon> findCouponsByCompany(Company company);
    List<Coupon> getCouponsByCategoryAndCompany(Category category, Company company);
    List<Coupon> getCouponsByCompanyAndPriceGreaterThanEqual(Company company, double price);

    Coupon getCouponByIdAndCompany(int id, Company company);

    Coupon getCouponByTitleAndCompany(String title, Company company);

    Coupon findByIdAndCompany(int id, Company company);

    void deleteCouponByIdAndCompany(int id, Company company);

    //@Transactional //makes a transaction with sql server
    //@Modifying
    @Query(value = "SELECT * FROM coupons " +
            "INNER JOIN customers_vs_coupons ON ID = COUPON_ID " +
            "WHERE customers_vs_coupons.CUSTOMER_ID = ?", nativeQuery = true)
    List<Coupon> getCustomerCoupons(int customerID);

    @Query(value = "SELECT * FROM coupons " +
            "INNER JOIN customers_vs_coupons ON coupons.ID = customers_vs_coupons.COUPON_ID " +
            "INNER JOIN categories ON categories.ID = coupons.CATEGORY_ID " +
            "WHERE categories.ID = ? AND customers_vs_coupons.CUSTOMER_ID = ?", nativeQuery = true)
    List<Coupon> getCustomerCoupons(Category category, int customerID);

    @Query(value = "SELECT * FROM coupons " +
            "INNER JOIN customers_vs_coupons ON ID = COUPON_ID " +
            "WHERE coupons.PRICE >= ? AND customers_vs_coupons.CUSTOMER_ID = ?", nativeQuery = true)
    List<Coupon> getCustomerCoupons(double price, int customerID);
}
