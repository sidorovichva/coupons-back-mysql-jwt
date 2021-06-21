package com.vs.couponsbackmysqljwt.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginViewModel {
    private String username;
    private String password;
}
