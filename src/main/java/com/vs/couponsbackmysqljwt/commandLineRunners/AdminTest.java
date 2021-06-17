package com.vs.couponsbackmysqljwt.commandLineRunners;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsbackmysqljwt.beans.Category;
import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.enums.ClientType;
import com.vs.couponsbackmysqljwt.services.AdminFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class AdminTest implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final AdminFacade adminFacade;

    @Override
    public void run(String... args) throws Exception {
        companyTest();
        customerTest();
        categoryTest();
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
            Company updatedCompany = adminFacade.getOneCompany(5); //1
            updatedCompany.setEmail("ddd");
            updatedCompany.setPassword(passwordEncoder.encode("444"));
            adminFacade.updateCompany(updatedCompany);
        } catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {adminFacade.deleteCompany(25);} //3
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {adminFacade.getAllCompanies().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {System.out.println(adminFacade.getOneCompany(25));} //3
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
            Customer updatedCustomer = adminFacade.getOneCustomer(25); //3
            updatedCustomer.setEmail("dd");
            updatedCustomer.setPassword(passwordEncoder.encode("44"));
            adminFacade.updateCustomer(updatedCustomer);
        } catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {adminFacade.deleteCustomer(5);} //1
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {adminFacade.getAllCustomers().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {System.out.println(adminFacade.getOneCustomer(15));} //2
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

        try {System.out.println(adminFacade.getOneCategory(25));} //3
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }
}
