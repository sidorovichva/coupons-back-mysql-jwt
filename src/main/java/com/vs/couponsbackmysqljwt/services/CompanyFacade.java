package com.vs.couponsbackmysqljwt.services;

import com.vs.couponsbackmysqljwt.AOP.SpecifyException;
import com.vs.couponsbackmysqljwt.AOP.ValidEntry;
import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTException;
import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsbackmysqljwt.Exceptions.ExpReason;
import com.vs.couponsbackmysqljwt.beans.Category;
import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Coupon;
import com.vs.couponsbackmysqljwt.repositories.CategoryRepository;
import com.vs.couponsbackmysqljwt.repositories.CompanyRepository;
import com.vs.couponsbackmysqljwt.repositories.CouponRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Scope("prototype")
@Data
@Lazy
public class CompanyFacade extends Facade{

    private final CouponRepository couponRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    @SpecifyException(exception = CouponRESTException.COUPON_ADD)
    public void addCoupon(Company company, @ValidEntry Coupon coupon) throws Exception{
        this.couponRepository.save(coupon);
    }

    @SpecifyException(exception = CouponRESTException.COUPON_UPDATE)
    public void updateCoupon(Company company, @ValidEntry Coupon coupon) throws Exception{
        this.couponRepository.save(coupon);
    }

    @SpecifyException(exception = CouponRESTException.COUPON_DELETE)
    public void deleteCoupon(Company company, int couponID) throws Exception {
//        if (couponID == 1) throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_DELETE.getFailure(), ExpReason.TEST_ENTITY);
//        if (!couponRepository.existsByIdAndCompany(couponID, company))
//            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_UPDATE.getFailure(), ExpReason.COUPON_NOT_AVAILABLE);
//        System.out.println("coupon id: " + couponID);
        System.out.println("=============================================================================deleteCoupon");
        this.couponRepository.deleteById(couponID);
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getAllCoupons() throws Exception {
        return couponRepository.findAll();
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getCompanyCoupons(Company company) throws Exception {
        return couponRepository.findCouponsByCompany(company);
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getCompanyCoupons(Company company, Category category) throws Exception {
        return couponRepository.getCouponsByCategoryAndCompany(category, company);
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getCompanyCoupons(Company company, double maxPrice) throws Exception {
        return couponRepository.getCouponsByCompanyAndPriceGreaterThanEqual(company, maxPrice);
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public Coupon getOneCouponByCompany(@ValidEntry Company company, int couponID) throws Exception {
        Coupon coupon = couponRepository.getCouponByIdAndCompany(couponID, company);
        return coupon;
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public Coupon getOneCouponByTitleAndCompany(Company company, String title) throws Exception {
        Coupon coupon = couponRepository.getCouponByTitleAndCompany(title, company);
        if (coupon == null) throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_GET.getFailure(), ExpReason.COUPON_DOESNT_EXIST);
        return coupon;
    }

    @SpecifyException(exception = CouponRESTException.COMPANY_GET)
    public Company getCompanyDetails(Company company) throws Exception {
        Company details = companyRepository.findById(company.getId()).get();
        details.setCoupons(couponRepository.getCouponsByCompany(company));
        return details;
    }

    @SpecifyException(exception = CouponRESTException.CATEGORY_GET)
    public List<Category> getAllCategories() throws Exception {
        return categoryRepository.findAll();
    }

    @SpecifyException(exception = CouponRESTException.CATEGORY_GET)
    public Category getOneCategory(int id) throws Exception{
        return categoryRepository.findById(id).get();
    }
}
