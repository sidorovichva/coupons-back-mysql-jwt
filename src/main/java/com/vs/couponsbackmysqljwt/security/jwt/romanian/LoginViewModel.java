package com.vs.couponsbackmysqljwt.security.jwt.romanian;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginViewModel {
    private String username;
    private String password;
}
