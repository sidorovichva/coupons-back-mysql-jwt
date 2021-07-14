package com.vs.couponsbackmysqljwt.AOP;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTException;
import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import com.vs.couponsbackmysqljwt.Exceptions.ExpReason;
import com.vs.couponsbackmysqljwt.beans.*;
import com.vs.couponsbackmysqljwt.repositories.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Date;

@Aspect
@Component
@Order(2)
@RequiredArgsConstructor
public class ValidEntryHandler extends CustomHandler{

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final CouponRepository couponRepository;
    private final PurchaseRepository purchaseRepository;

    @Before("execution(public * *(.., @ValidEntry (*), ..))")
    public void handler(JoinPoint joinPoint) throws Throwable {
        Object[] arg = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (arg.length == 1) {
            if (arg[0] instanceof Company) {
                Company company = (Company) arg[0];
                if (method.getName().contains("add")) companyValidityToAdd(company);
                if (method.getName().contains("update")) companyValidityToUpdate(company);
            }
            if (arg[0] instanceof Customer) {
                Customer customer = (Customer) arg[0];
                if (method.getName().contains("add")) customerValidityToAdd(customer);
                if (method.getName().contains("update")) customerValidityToUpdate(customer);
            }
            if (arg[0] instanceof Category) {
                Category category = (Category) arg[0];
                categoryValidityToAdd(category);
            }
        }
        if (arg.length == 2) {
            if (arg[0] instanceof Company && arg[1] instanceof Coupon) {
                Company company = (Company) arg[0];
                Coupon coupon = (Coupon) arg[1];
                if (method.getName().contains("add")) couponValidityToAdd(company, coupon);
                if (method.getName().contains("update")) couponValidityToUpdate(company, coupon);
                couponValidity(company, coupon);
            }
            if (arg[0] instanceof Customer && arg[1] instanceof Coupon) {
                Customer customer = (Customer) arg[0];
                Coupon coupon = (Coupon) arg[1];
                purchaseValidity(customer, coupon);
            }
            if (arg[0] instanceof Company && arg[1] instanceof Integer) {
                Company company = (Company) arg[0];
                int id = (int) arg[1];
                companyAccessValidity(company, id);
            }
        }
    }

    public void companyValidityToAdd(Company company) throws CouponRESTExceptionHandler {
        if (companyRepository.existsById(company.getId()))
            throw new CouponRESTExceptionHandler(CouponRESTException.COMPANY_ADD.getFailure(), ExpReason.COMPANY_ALREADY_EXISTS_ID);
        if (companyRepository.existsByNameOrEmail(company.getName(), company.getEmail()))
            throw new CouponRESTExceptionHandler(CouponRESTException.COMPANY_ADD.getFailure(), ExpReason.COMPANY_ALREADY_EXISTS);
        if (company.getName() == "" || company.getEmail() == "" || company.getPassword() == "" )
            throw new CouponRESTExceptionHandler(CouponRESTException.COMPANY_ADD.getFailure(), ExpReason.FIELD_IS_EMPTY);
    }

    public void companyValidityToUpdate(Company company) throws CouponRESTExceptionHandler {
        if (!companyRepository.existsById(company.getId()))
            throw new CouponRESTExceptionHandler(CouponRESTException.COMPANY_UPDATE.getFailure(), ExpReason.COMPANY_DOESNT_EXIST);
        if (companyRepository.existsByNameAndIdNot(company.getName(), company.getId())
                || companyRepository.existsByEmailAndIdNot(company.getEmail(), company.getId())) {
            throw new CouponRESTExceptionHandler(CouponRESTException.COMPANY_UPDATE.getFailure(), ExpReason.COMPANY_ALREADY_EXISTS); }
    }

    public void customerValidityToAdd(Customer customer) throws CouponRESTExceptionHandler {
        if (customerRepository.existsById(customer.getId()))
            throw new CouponRESTExceptionHandler(CouponRESTException.CUSTOMER_ADD.getFailure(), ExpReason.CUSTOMER_ALREADY_EXISTS_ID);
        if (customerRepository.existsByEmail(customer.getEmail()))
            throw new CouponRESTExceptionHandler(CouponRESTException.CUSTOMER_ADD.getFailure(), ExpReason.CUSTOMER_ALREADY_EXISTS);
    }

    public void customerValidityToUpdate(Customer customer) throws CouponRESTExceptionHandler {
        if (!customerRepository.existsById(customer.getId()))
            throw new CouponRESTExceptionHandler(CouponRESTException.CUSTOMER_UPDATE.getFailure(), ExpReason.CUSTOMER_DOESNT_EXIST);
        if (customerRepository.existsByEmailAndIdNot(customer.getEmail(), customer.getId())) {
            throw new CouponRESTExceptionHandler(CouponRESTException.CUSTOMER_UPDATE.getFailure(), ExpReason.CUSTOMER_ALREADY_EXISTS); }
    }

    public void categoryValidityToAdd(Category category) throws CouponRESTExceptionHandler {
        if (categoryRepository.existsByName(category.getName().toUpperCase()))
            throw new CouponRESTExceptionHandler(CouponRESTException.CATEGORY_ADD.getFailure(), ExpReason.CATEGORY_ALREADY_EXISTS);
    }

    public void couponValidityToAdd(Company company, Coupon coupon) throws CouponRESTExceptionHandler {
        if (couponRepository.existsByTitleAndCompany(coupon.getTitle(), company))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.COUPON_ALREADY_EXISTS);
        if (coupon.getStartDate().before(new Date(System.currentTimeMillis())))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.TOO_LATE);
    }

    public void couponValidityToUpdate(Company company, Coupon coupon) throws CouponRESTExceptionHandler {
        if (!couponRepository.existsByIdAndCompany(coupon.getId(), company))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_UPDATE.getFailure(), ExpReason.COUPON_NOT_AVAILABLE);
        if (couponRepository.existsByTitleAndCompanyAndIdNot(coupon.getTitle(), company, coupon.getId()))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_UPDATE.getFailure(), ExpReason.COUPON_ALREADY_EXISTS);
    }

    public void couponValidity(Company company, Coupon coupon) throws CouponRESTExceptionHandler {
        if (coupon.getAmount() < 1)
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.WRONG_AMOUNT);
        if (coupon.getEndDate().before(new Date(System.currentTimeMillis())))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.TOO_LATE);
        if (coupon.getEndDate().before(coupon.getStartDate()))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.END_BEFORE_FINISH);
        if (coupon.getTitle().isEmpty() || coupon.getDescription().isEmpty())
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.EMPTY_FIELDS);
        if (coupon.getPrice() <= 0)
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_ADD.getFailure(), ExpReason.WRONG_PRICE);
    }

    public void purchaseValidity(Customer customer, Coupon coupon) throws CouponRESTExceptionHandler {
        if (purchaseRepository.existsByCouponAndCustomer(coupon, customer))
            throw new CouponRESTExceptionHandler(CouponRESTException.PURCHASE_ADD.getFailure(), ExpReason.PURCHASE_EXISTS);
    }

    private void companyAccessValidity(Company company, int id) throws CouponRESTExceptionHandler {
        if (!couponRepository.existsById(id))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_GET.getFailure(), ExpReason.COUPON_DOESNT_EXIST);
        if (!couponRepository.existsByIdAndCompany(id, company))
            throw new CouponRESTExceptionHandler(CouponRESTException.COUPON_GET.getFailure(), ExpReason.COUPON_NOT_AVAILABLE);
    }
}
