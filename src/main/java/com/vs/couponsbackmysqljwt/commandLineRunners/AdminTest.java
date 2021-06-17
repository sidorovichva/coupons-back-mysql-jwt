package com.vs.couponsbackmysqljwt.commandLineRunners;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsbackmysqljwt.beans.Category;
import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.enums.ClientType;
import com.vs.couponsbackmysqljwt.services.AdminFacade;
import com.vs.couponsbackmysqljwt.services.LoginManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class AdminTest implements CommandLineRunner {

    private final LoginManager loginManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        AdminFacade admin = (AdminFacade) loginManager.login(ClientType.ADMINISTRATOR, "a", "a");
        companyTest(admin);
        customerTest(admin);
        categoryTest(admin);
    }

    private void companyTest(AdminFacade facade) throws Exception {
        System.out.println("================================================================================COMPANIES");
        try {facade.addCompany(Company.builder().name("companyA").email("aaa").password(passwordEncoder.encode("111")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {facade.addCompany(Company.builder().name("companyB").email("bbb").password(passwordEncoder.encode("222")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {facade.addCompany(Company.builder().name("companyC").email("ccc").password(passwordEncoder.encode("333")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {
            Company updatedCompany = facade.getOneCompany(1);
            updatedCompany.setEmail("ddd");
            updatedCompany.setPassword(passwordEncoder.encode("444"));
            facade.updateCompany(updatedCompany);
        } catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {facade.deleteCompany(3);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {facade.getAllCompanies().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {System.out.println(facade.getOneCompany(3));}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void customerTest(AdminFacade facade) throws Exception {
        System.out.println("\n==============================================================================CUSTOMERS");
        try {facade.addCustomer(Customer.builder().firstName("Adam").lastName("Alef").email("aa").password(passwordEncoder.encode("11")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {facade.addCustomer(Customer.builder().firstName("Bob").lastName("Bet").email("bb").password(passwordEncoder.encode("22")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {facade.addCustomer(Customer.builder().firstName("Clare").lastName("Gimel").email("cc").password(passwordEncoder.encode("33")).build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {
            Customer updatedCustomer = facade.getOneCustomer(3);
            updatedCustomer.setEmail("dd");
            updatedCustomer.setPassword(passwordEncoder.encode("44"));
            facade.updateCustomer(updatedCustomer);
        } catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {facade.deleteCustomer(1);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {facade.getAllCustomers().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {System.out.println(facade.getOneCustomer(2));}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }

    private void categoryTest(AdminFacade facade) throws Exception {
        System.out.println("\n=============================================================================CATEGORIES");
        try {facade.addCategory(Category.builder().id(0).name("FOOD").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {facade.addCategory(Category.builder().id(0).name("TRAVEL").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
        try {facade.addCategory(Category.builder().id(0).name("EDUCATION").build());}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {facade.getAllCategories().forEach(System.out::println);}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {System.out.println(facade.getOneCategory(3));}
        catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}
    }
}
