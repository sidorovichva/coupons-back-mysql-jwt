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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Order(1)
@RequiredArgsConstructor
public class Population implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final AdminFacade adminFacade;
    private final CompanyFacade companyFacade;
    private final CustomerFacade customerFacade;

    @Override
    public void run(String... args) throws Exception {
        companyTest();
        customerTest();
        categoryTest();
        couponTestOne(adminFacade.getOneCompany(2)); //15
        couponTestTwo(adminFacade.getOneCompany(1)); //5
        purchaseTestOne(adminFacade.getOneCustomer(2)); //15
        purchaseTestTwo(adminFacade.getOneCustomer(3)); //25
    }

    private void companyTest() throws Exception {
        System.out.println("================================================================================COMPANIES");
        try {adminFacade.addCompany(Company.builder().name("companyA").email("aaa").password(passwordEncoder.encode("111")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCompany(Company.builder().name("companyB").email("bbb").password(passwordEncoder.encode("222")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCompany(Company.builder().name("companyC").email("ccc").password(passwordEncoder.encode("333")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {
            Company updatedCompany = adminFacade.getOneCompany(1); //5
            updatedCompany.setEmail("company");
            updatedCompany.setPassword(passwordEncoder.encode("com"));
            adminFacade.updateCompany(updatedCompany);
        } catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {adminFacade.deleteCompany(3);} //25
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {adminFacade.getAllCompanies().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {System.out.println(adminFacade.getOneCompany(3));} //25
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void customerTest() throws Exception {
        System.out.println("\n==============================================================================CUSTOMERS");
        try {adminFacade.addCustomer(Customer.builder().firstName("Adam").lastName("Alef").email("aa").password(passwordEncoder.encode("11")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCustomer(Customer.builder().firstName("Bob").lastName("Bet").email("bb").password(passwordEncoder.encode("22")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCustomer(Customer.builder().firstName("Clare").lastName("Gimel").email("cc").password(passwordEncoder.encode("33")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {
            Customer updatedCustomer = adminFacade.getOneCustomer(3); //25
            updatedCustomer.setEmail("customer");
            updatedCustomer.setPassword(passwordEncoder.encode("cus"));
            adminFacade.updateCustomer(updatedCustomer);
        } catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {adminFacade.deleteCustomer(1);} //5
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {adminFacade.getAllCustomers().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {System.out.println(adminFacade.getOneCustomer(2));} //15
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void categoryTest() throws Exception {
        System.out.println("\n=============================================================================CATEGORIES");
        try {adminFacade.addCategory(Category.builder().id(0).name("FOOD").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCategory(Category.builder().id(0).name("TRAVEL").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {adminFacade.addCategory(Category.builder().id(0).name("EDUCATION").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {adminFacade.getAllCategories().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {System.out.println(adminFacade.getOneCategory(3));} //25
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void couponTestOne(Company company) throws Exception {
        System.out.println("\n================================================================================COUPONS");
        try {companyFacade.addCoupon(company, Coupon.builder()
                .company(company)
                .category(companyFacade.getAllCategories().get(0))
                .title("TitleOne")
                .description("descr1a")
                .startDate(Date.valueOf("2021-06-02"))
                .endDate(Date.valueOf("2021-06-06"))
                .amount(500)
                .price(23.99)
                .image("AKJHDGDHJDJD").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {companyFacade.addCoupon(company, Coupon.builder()
                .company(company)
                .category(companyFacade.getAllCategories().get(1))
                .title("TitleTwo")
                .description("another description")
                .startDate(Date.valueOf("2021-06-06"))
                .endDate(Date.valueOf("2021-07-06"))
                .amount(10_000)
                .price(59.99)
                .image("LKJGHJGF").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {companyFacade.addCoupon(company, Coupon.builder()
                .company(company)
                .category(companyFacade.getAllCategories().get(0))
                .title("TitleThree")
                .description("another one description")
                .startDate(Date.valueOf("2021-06-10"))
                .endDate(Date.valueOf("2023-01-30"))
                .amount(33)
                .price(199.99)
                .image("KJGYFFGFHGJ").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {companyFacade.addCoupon(company, Coupon.builder()
                .company(company)
                .category(companyFacade.getAllCategories().get(2))
                .title("TitleFour")
                .description("4th description")
                .startDate(Date.valueOf("2021-08-02"))
                .endDate(Date.valueOf("2022-10-06"))
                .amount(500)
                .price(49.99)
                .image("HGHGFKKJGJ").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {
            Coupon updatedCoupon = companyFacade.getOneCouponByCompany(company, 3); //25
            updatedCoupon.setTitle("NewTitle");
            updatedCoupon.setDescription("newDescription");
            updatedCoupon.setStartDate(Date.valueOf("2021-02-07"));
            updatedCoupon.setEndDate(Date.valueOf("2021-08-08"));
            updatedCoupon.setAmount(222);
            updatedCoupon.setPrice(99.99);
            companyFacade.updateCoupon(company, updatedCoupon);
        } catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {companyFacade.deleteCoupon(company, 4);} //35
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {companyFacade.getCompanyCoupons(company).forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {companyFacade.getCompanyCoupons(company, companyFacade.getAllCategories().get(2)).forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {companyFacade.getCompanyCoupons(company, 50).forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try { System.out.println(companyFacade.getCompanyDetails(company).details());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void couponTestTwo(Company company) throws Exception {
        try {companyFacade.addCoupon(company, Coupon.builder()
                .company(company)
                .category(companyFacade.getAllCategories().get(0))
                .title("TitleFive")
                .description("descr555")
                .startDate(Date.valueOf("2021-07-02"))
                .endDate(Date.valueOf("2021-07-06"))
                .amount(333)
                .price(66.6)
                .image("YHGFHHFHFDJD").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void purchaseTestOne(Customer customer) throws Exception {
        try {customerFacade.purchaseCoupon(customer, customerFacade.getOneCoupon(1));} //5
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {customerFacade.purchaseCoupon(customer, customerFacade.getOneCoupon(3));} //25
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {customerFacade.getAllCoupons().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {customerFacade.getCustomerCoupons(customer).forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {customerFacade.getCustomerCoupons(customer, customerFacade.getOneCategory(1)).forEach(System.out::println);} //5
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {customerFacade.getCustomerCoupons(customer, 50).forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try { System.out.println(customerFacade.getCustomerDetails(customer).details());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void purchaseTestTwo(Customer customer) throws Exception {
        try {customerFacade.purchaseCoupon(customer, customerFacade.getOneCoupon(2));} //15
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {customerFacade.purchaseCoupon(customer, customerFacade.getOneCoupon(4));} //35
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }
}
