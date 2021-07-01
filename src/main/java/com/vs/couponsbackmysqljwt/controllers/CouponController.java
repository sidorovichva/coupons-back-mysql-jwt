package com.vs.couponsbackmysqljwt.controllers;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTException;
import com.vs.couponsbackmysqljwt.beans.Category;
import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Coupon;
import com.vs.couponsbackmysqljwt.controllers.controllerInterfaces.CouponControllerInterface;
import com.vs.couponsbackmysqljwt.services.CompanyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("coupons")
@RequiredArgsConstructor
public class CouponController implements CouponControllerInterface {

    private final CompanyFacade companyFacade;

    private Company user(String email) throws Exception {
        return companyFacade.getCompanyRepository().findCompanyByEmail(email);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> addCoupon(Principal principal, @RequestBody Coupon coupon) throws Exception {
        Company company = companyFacade.getCompanyRepository().findCompanyByEmail(principal.getName());
        coupon.setCompany(company);
        companyFacade.addCoupon(company, coupon);
        return new ResponseEntity<>(CouponRESTException.COUPON_ADD.getSuccess(), HttpStatus.OK);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> updateCoupon(Principal principal, @RequestBody Coupon coupon) throws Exception {
        Company company = companyFacade.getCompanyRepository().findCompanyByEmail(principal.getName());
        coupon.setCompany(company);
        companyFacade.updateCoupon(company, coupon);
        return new ResponseEntity<>(CouponRESTException.COUPON_UPDATE.getSuccess(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoupon(Principal principal, @PathVariable int id) throws Exception {
        Company company = companyFacade.getCompanyRepository().findCompanyByEmail(principal.getName());
        companyFacade.deleteCoupon(company, id);
        return new ResponseEntity<>(CouponRESTException.COUPON_DELETE.getSuccess(), HttpStatus.OK);
    }

    @Override
    @GetMapping()
    public ResponseEntity<?> getCompanyCoupons(Principal principal) throws Exception {
        //Company company = companyFacade.getCompanyRepository().findCompanyByEmail(principal.getName());
        //return ResponseEntity.ok().body(companyFacade.getCompanyCoupons(company));
        return ResponseEntity.ok().body(companyFacade.getCompanyCoupons(user(principal.getName())));
    }

    @Override
    @GetMapping("/{category}")
    public ResponseEntity<?> getCompanyCoupons(Principal principal, @PathVariable Category category) throws Exception {
        Company company = companyFacade.getCompanyRepository().findCompanyByEmail(principal.getName());
        return ResponseEntity.ok().body(companyFacade.getCompanyCoupons(company, category));
    }

    @Override
    @GetMapping("/{maxPrice}")
    public ResponseEntity<?> getCompanyCoupons(Principal principal, @PathVariable double maxPrice) throws Exception {
        Company company = companyFacade.getCompanyRepository().findCompanyByEmail(principal.getName());
        return ResponseEntity.ok().body(companyFacade.getCompanyCoupons(company, maxPrice));
    }

    @Override
    @GetMapping("/getone/{id}")
    public ResponseEntity<?> getOneCouponByCompany(Principal principal, @PathVariable int id) throws Exception {
        //Company company = companyFacade.getCompanyRepository().findCompanyByEmail(principal.getName());
        //return ResponseEntity.ok().body(companyFacade.getOneCouponByCompany(company, id));
        return ResponseEntity.ok().body(companyFacade.getOneCouponByCompany(user(principal.getName()), id));
    }

    @Override
    @GetMapping("/details")
    public ResponseEntity<?> getCompanyDetails(Principal principal) throws Exception {
        Company company = companyFacade.getCompanyRepository().findCompanyByEmail(principal.getName());
        return ResponseEntity.ok().body(companyFacade.getCompanyDetails(company));
    }
}
