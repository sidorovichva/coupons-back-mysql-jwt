package com.vs.couponsbackmysqljwt.security.jwt.romanian;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.repositories.CompanyRepository;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            //AuthenticationEntryPoint authenticationEntryPoint,
            CustomerRepository customerRepository,
            CompanyRepository companyRepository) {
        //super(authenticationManager, authenticationEntryPoint);
        super(authenticationManager);
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        System.out.println("=========================================================================================");
        System.out.println("JwtAuthorizationFilter, doFilterInternal, beginning");
        System.out.println("=========================================================================================");
        //Read the Authorization header where the JWT token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        //If header does not contain BEARER or is null, delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        System.out.println("=========================================================================================");
        System.out.println("JwtAuthorizationFilter, doFilterInternal, after if");
        System.out.println("=========================================================================================");

        /*System.out.println("amigo");
        String token = header.replace(JwtProperties.TOKEN_PREFIX, "");
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    //.setSigningKey(SignatureAlgorithm.HS512, JwtProperties.SECRET)
                    .setSigningKey(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes()))
                    .parseClaimsJws(token);
        }*/


        //If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        System.out.println("=========================================================================================");
        System.out.println("JwtAuthorizationFilter, getUsernamePasswordAuthentication, beginning");
        System.out.println("=========================================================================================");

        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        if (token != null) {
            //parse the token and validate it
            String userName = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            /*String userName = Jwts.parser()
                    .setSigningKey(Keys.keyPairFor(SignatureAlgorithm.HS512, JwtProperties.SECRET.getBytes()))
                    .setSigningKeyResolver(Keys.keyPairFor(SignatureAlgorithm.HS512, JwtProperties.SECRET))
                    .build()
                    .verify(token)
                    .getSubject();*/

            System.out.println("=========================================================================================");
            System.out.println("JwtAuthorizationFilter, getUsernamePasswordAuthentication, if 1: userName");
            System.out.println(userName);
            System.out.println("=========================================================================================");

            //Search in the DB if we find the user by token subject ( username)
            //If so, then grab the user details and create spring auth token using username password and authorities/roles
            if (userName != null) {
                Customer customer = this.customerRepository.findCustomerByEmail(userName);
                Company company = this.companyRepository.findCompanyByEmail(userName);

                UserPrincipal principal = new UserPrincipal(customer, company);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());

                System.out.println("=========================================================================================");
                System.out.println("JwtAuthorizationFilter, getUsernamePasswordAuthentication, auth");
                System.out.println(auth);
                System.out.println("=========================================================================================");

                return auth;
            }
            return null;
        }
        return null;
    }
}
