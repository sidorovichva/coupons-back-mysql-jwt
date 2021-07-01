package com.vs.couponsbackmysqljwt.controllers;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTException;
import com.vs.couponsbackmysqljwt.beans.Category;
import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Coupon;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.controllers.controllerInterfaces.PurchaseControllerInterface;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import com.vs.couponsbackmysqljwt.services.CustomerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("purchases")
@RequiredArgsConstructor
public class PurchaseController implements PurchaseControllerInterface {

    private final CustomerFacade customerFacade;
    private final CustomerRepository customerRepository;

    @Override
    @PostMapping
    public ResponseEntity<?> addPurchase(Principal principal, @RequestBody int couponId) throws Exception{
        Customer customer = customerFacade.getCustomerRepository().findCustomerByEmail(principal.getName());
        Coupon coupon = customerFacade.getOneCoupon(couponId);
        customerFacade.purchaseCoupon(customer, coupon);
        return new ResponseEntity<>(CouponRESTException.COMPANY_ADD.getSuccess(), HttpStatus.OK);
    }

    @Override
    @GetMapping()
    public ResponseEntity<?> getCustomerCoupons(Principal principal) throws Exception {
        Customer customer = customerFacade.getCustomerRepository().findCustomerByEmail(principal.getName());
        List<Coupon> list = customerFacade.getCustomerCoupons(customer);
        list.forEach(p -> System.err.println(p));
        return ResponseEntity.ok().body(list);
        //return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(customer));
    }

    @GetMapping("/not")
    public ResponseEntity<?> getNotCustomerCoupons(Principal principal) throws Exception {
        Customer customer = customerFacade.getCustomerRepository().findCustomerByEmail(principal.getName());
        return ResponseEntity.ok().body(customerFacade.getNotCustomerCoupons(customer));
    }

    @Override
    @GetMapping("/{category}")
    public ResponseEntity<?> getCustomerCoupons(Principal principal, @PathVariable Category category) throws Exception {
        Customer customer = customerRepository.findCustomerByEmail(principal.getName());
        return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(customer, category));
    }

    @Override
    @GetMapping("/{maxPrice}")
    public ResponseEntity<?> getCustomerCoupons(Principal principal, @PathVariable double maxPrice) throws Exception {
        Customer customer = customerRepository.findCustomerByEmail(principal.getName());
        return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(customer, maxPrice));
    }

    @Override
    @GetMapping("/details")
    public ResponseEntity<?> getCustomerDetails(Principal principal) throws Exception {
        Customer customer = customerRepository.findCustomerByEmail(principal.getName());
        return ResponseEntity.ok().body(customerFacade.getCustomerDetails(customer));
    }
}
