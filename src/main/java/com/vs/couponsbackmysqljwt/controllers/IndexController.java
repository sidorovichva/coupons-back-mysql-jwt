package com.vs.couponsbackmysqljwt.controllers;

import com.vs.couponsbackmysqljwt.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class IndexController {

    private final CouponRepository couponRepository;

    @GetMapping()
    public ResponseEntity<?> getCompanyCoupons() throws Exception {
        return ResponseEntity.ok().body(couponRepository.findAll());
    }
}
