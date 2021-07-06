package com.vs.couponsbackmysqljwt.controllers;

import com.vs.couponsbackmysqljwt.beans.Coupon;
import com.vs.couponsbackmysqljwt.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class IndexController {

    private final CouponRepository couponRepository;

    @GetMapping()
    public ResponseEntity<?> getCompanyCoupons() throws Exception {
        return ResponseEntity.ok().body(couponRepository.findAll());
    }

    /*@GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal User user) throws Exception {
        System.err.println("Role: " + user.getAuthorities() +
                " Username: " + user.getUsername());
        return ResponseEntity.ok().body(
            "Role: " + user.getAuthorities() +
            " Username: " + user.getUsername()
        );
    }*/

//    @GetMapping("/user")
//    public ResponseEntity<?> getUser(Principal principal) throws Exception {
//        System.err.println("Username: " + principal.getName());
//        return ResponseEntity.ok().body("Username: " + principal.getName());
//    }
}
