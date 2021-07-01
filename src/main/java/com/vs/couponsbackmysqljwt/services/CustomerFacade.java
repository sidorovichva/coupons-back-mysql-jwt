package com.vs.couponsbackmysqljwt.services;

import com.vs.couponsbackmysqljwt.AOP.SpecifyException;
import com.vs.couponsbackmysqljwt.AOP.ValidEntry;
import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTException;
import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsbackmysqljwt.Exceptions.ExpReason;
import com.vs.couponsbackmysqljwt.beans.*;
import com.vs.couponsbackmysqljwt.repositories.CategoryRepository;
import com.vs.couponsbackmysqljwt.repositories.CouponRepository;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import com.vs.couponsbackmysqljwt.repositories.PurchaseRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Scope("prototype")
@RequiredArgsConstructor
@Data
@Lazy
public class CustomerFacade extends Facade{

    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;
    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;

    @SpecifyException(exception = CouponRESTException.PURCHASE_ADD)
    public void purchaseCoupon(@ValidEntry Customer customer, Coupon coupon) throws Exception {
        Purchase purchase = new Purchase(coupon, customer);
        Coupon changeAmountCoupon = couponRepository.findById(coupon.getId()).get();
        changeAmountCoupon.setAmount(changeAmountCoupon.getAmount() - 1);
        couponRepository.save(changeAmountCoupon);
        purchaseRepository.save(purchase);
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public Coupon getOneCoupon(int id) throws Exception {
        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon == null) throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_GET.getFailure(), ExpReason.COUPON_DOESNT_EXIST);
        return coupon.get();
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getAllCoupons() throws Exception{
        return couponRepository.findAll();
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getCustomerCoupons(Customer customer) throws Exception {
        return couponRepository.getCustomerCoupons(customer.getId());
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getNotCustomerCoupons(Customer customer) throws Exception {
        return couponRepository.getNotCustomerCoupons(customer.getId());
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getCustomerCoupons(Customer customer, Category category) throws Exception {
        return couponRepository.getCustomerCoupons(category, customer.getId());
    }

    @SpecifyException(exception = CouponRESTException.COUPON_GET)
    public List<Coupon> getCustomerCoupons(Customer customer, double maxPrice) throws Exception{
        return couponRepository.getCustomerCoupons(maxPrice, customer.getId());
    }

    @SpecifyException(exception = CouponRESTException.CUSTOMER_GET)
    public Customer getCustomerDetails(Customer customer) throws Exception {
        Customer details = customerRepository.findById(customer.getId()).get();
        details.setPurchases(purchaseRepository.getPurchasesByCustomer(customer));
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
