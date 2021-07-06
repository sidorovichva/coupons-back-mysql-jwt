package com.vs.couponsbackmysqljwt.commandLineRunners;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsbackmysqljwt.beans.Category;
import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Coupon;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.services.AdminFacade;
import com.vs.couponsbackmysqljwt.services.CompanyFacade;
import com.vs.couponsbackmysqljwt.services.CustomerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Order(1)
@RequiredArgsConstructor
public class Population implements CommandLineRunner {

    private final AdminFacade adminFacade;
    private final CompanyFacade companyFacade;
    private final CustomerFacade customerFacade;

    @Override
    public void run(String... args) throws Exception {
        companyTest();
        customerTest();
        categoryTest();
        couponTestOne(adminFacade.getOneCompany(1));
        purchaseTestOne(adminFacade.getOneCustomer(1));
    }

    private void companyTest() throws Exception {
        try {adminFacade.addCompany(Company.builder().name("TestCompany").email("company").password("com").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void customerTest() throws Exception {
        try {adminFacade.addCustomer(Customer.builder().firstName("Test").lastName("Customer").email("customer").password("cus").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void categoryTest() throws Exception {
        try {adminFacade.addCategory(Category.builder().id(0).name("FOOD").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCategory(Category.builder().id(0).name("TRAVEL").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCategory(Category.builder().id(0).name("EDUCATION").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void couponTestOne(Company company) throws Exception {
        try {companyFacade.addCoupon(company, Coupon.builder()
                .company(company)
                .category(companyFacade.getAllCategories().get(0))
                .title("TestCoupon")
                .description("you can't delete this coupon")
                .startDate(Date.valueOf("2021-08-02"))
                .endDate(Date.valueOf("2021-08-06"))
                .amount(500)
                .price(23.99)
                .image("AKJHDGDHJDJD").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void purchaseTestOne(Customer customer) throws Exception {
        try {customerFacade.purchaseCoupon(customer, customerFacade.getOneCoupon(1));}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }
}
