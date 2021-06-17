package com.vs.couponsbackmysqljwt.controllers;

import com.vs.couponsbackmysqljwt.beans.Category;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.controllers.controllerInterfaces.PurchaseControllerInterface;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import com.vs.couponsbackmysqljwt.services.CustomerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("purchases")
@RequiredArgsConstructor
public class PurchaseController implements PurchaseControllerInterface {

    private final CustomerFacade customerFacade;
    private final CustomerRepository customerRepository;

    @Override
    @GetMapping()
    public ResponseEntity<?> getCustomerCoupons(Principal principal) throws Exception {
        System.err.println(principal.getName());
        Customer customer = customerFacade.getCustomerRepository().findCustomerByEmail(principal.getName());
        System.err.println(customer);
        customerFacade.getCustomerCoupons(customer).forEach(System.err::println);
        return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(customer));
    }

    @Override
    @GetMapping("/{category}")
    public ResponseEntity<?> getCompanyCoupons(Principal principal, @PathVariable Category category) throws Exception {
        Customer customer = customerRepository.findCustomerByEmail(principal.getName());
        return ResponseEntity.ok().body(customerFacade.getCustomerCoupons(customer, category));
    }

    @Override
    @GetMapping("/{maxPrice}")
    public ResponseEntity<?> getCompanyCoupons(Principal principal, @PathVariable double maxPrice) throws Exception {
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
