package com.vs.couponsbackmysqljwt.commandLineRunners;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.services.AdminFacade;
import com.vs.couponsbackmysqljwt.services.CustomerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class CustomerTest implements CommandLineRunner {

    private final CustomerFacade customerFacade;
    private final AdminFacade adminFacade;

    @Override
    public void run(String... args) throws Exception {
        purchaseTestOne(adminFacade.getOneCustomer(2));
        purchaseTestTwo(adminFacade.getOneCustomer(3));
    }

    private void purchaseTestOne(Customer customer) throws Exception {
        try {customerFacade.purchaseCoupon(customer, customerFacade.getOneCoupon(1));}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {customerFacade.purchaseCoupon(customer, customerFacade.getOneCoupon(3));}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {customerFacade.getAllCoupons().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {customerFacade.getCustomerCoupons(customer).forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {customerFacade.getCustomerCoupons(customer, customerFacade.getOneCategory(1)).forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {customerFacade.getCustomerCoupons(customer, 50).forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try { System.out.println(customerFacade.getCustomerDetails(customer).details());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void purchaseTestTwo(Customer customer) throws Exception {
        try {customerFacade.purchaseCoupon(customer, customerFacade.getOneCoupon(2));}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {customerFacade.purchaseCoupon(customer, customerFacade.getOneCoupon(4));}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }
}
