package com.vs.couponsbackmysqljwt.controllers.controllerInterfaces;

import com.vs.couponsbackmysqljwt.beans.Category;
import com.vs.couponsbackmysqljwt.beans.Coupon;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface CouponControllerInterface {
    ResponseEntity<?> addCoupon(Principal principal, Coupon coupon) throws Exception;
    ResponseEntity<?> updateCoupon(Principal principal, Coupon coupon) throws Exception;
    ResponseEntity<?> deleteCoupon(Principal principal, int couponID) throws Exception;
    ResponseEntity<?> getCompanyCoupons(Principal principal) throws Exception;
    ResponseEntity<?> getCompanyCoupons(Principal principal, Category category) throws Exception;
    ResponseEntity<?> getCompanyCoupons(Principal principal, double maxPrice) throws Exception;
    ResponseEntity<?> getOneCouponByCompany(Principal principal, int couponID) throws Exception;
    ResponseEntity<?> getCompanyDetails(Principal principal) throws Exception;
}
