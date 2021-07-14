package com.vs.couponsbackmysqljwt.controllers;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTException;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.controllers.controllerInterfaces.CustomerControllerInterface;
import com.vs.couponsbackmysqljwt.services.AdminFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController implements CustomerControllerInterface {

    private final AdminFacade adminFacade;

    @Override
    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws Exception {
        adminFacade.addCustomer(customer);
        return new ResponseEntity<>(CouponRESTException.CUSTOMER_ADD.getSuccess(), HttpStatus.OK);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) throws Exception {
        adminFacade.updateCustomer(customer);
        return new ResponseEntity<>(CouponRESTException.CUSTOMER_UPDATE.getSuccess(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) throws Exception {
        adminFacade.deleteCustomer(id);
        return new ResponseEntity<>(CouponRESTException.CUSTOMER_DELETE.getSuccess(), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getAllCustomers() throws Exception {
        return ResponseEntity.ok().body(adminFacade.getAllCustomers());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneCustomer(@PathVariable int id) throws Exception {
        return ResponseEntity.ok().body(adminFacade.getOneCustomer(id));
    }
}
