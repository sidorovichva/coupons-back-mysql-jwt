package com.vs.couponsbackmysqljwt.controllers.controllerInterfaces;

import com.vs.couponsbackmysqljwt.beans.Category;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface PurchaseControllerInterface {
    ResponseEntity<?> addPurchase(Principal principal, int id) throws Exception;
    ResponseEntity<?> getCustomerCoupons(Principal principal) throws Exception;
    ResponseEntity<?> getCustomerCoupons(Principal principal, Category category) throws Exception;
    ResponseEntity<?> getCustomerCoupons(Principal principal, double maxPrice) throws Exception;
    ResponseEntity<?> getCustomerDetails(Principal principal) throws Exception;

}
