package com.vs.couponsbackmysqljwt.commandLineRunners;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsbackmysqljwt.beans.*;
import com.vs.couponsbackmysqljwt.services.AdminFacade;
import com.vs.couponsbackmysqljwt.services.CompanyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Order(2)
@RequiredArgsConstructor
public class CompanyTest implements CommandLineRunner {

    private final CompanyFacade companyFacade;
    private final AdminFacade adminFacade;

    @Override
    public void run(String... args) throws Exception {
        couponTestOne(adminFacade.getOneCompany(2));
        couponTestTwo(adminFacade.getOneCompany(1));
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
            Coupon updatedCoupon = companyFacade.getOneCouponByCompany(company, 3);
            updatedCoupon.setTitle("NewTitle");
            updatedCoupon.setDescription("newDescription");
            updatedCoupon.setStartDate(Date.valueOf("2021-02-07"));
            updatedCoupon.setEndDate(Date.valueOf("2021-08-08"));
            updatedCoupon.setAmount(222);
            updatedCoupon.setPrice(99.99);
            companyFacade.updateCoupon(company, updatedCoupon);
        } catch (CouponRESTExceptionHandler e) {System.out.println(e.getText());}

        try {companyFacade.deleteCoupon(company, 4);}
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
}
