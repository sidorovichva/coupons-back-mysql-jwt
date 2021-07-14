package com.vs.couponsbackmysqljwt.security;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //Triggers when we issue POST request to /login
    //need to pass {"username": "dan", "password": "dan123"} in the request body
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginViewModel credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
            //inner token that spring will use to define authentication
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword()//,
                    //new ArrayList<>()
            );

            Authentication auth = authenticationManager.authenticate(authenticationToken);
            //UsernamePasswordAuthenticationToken [Principal=dd, Credentials=[PROTECTED], Authenticated=false, Details=null, Granted Authorities=[]]
            //UsernamePasswordAuthenticationToken [Principal=a, Credentials=[PROTECTED], Authenticated=false, Details=null, Granted Authorities=[]]

            return auth;
        } catch (JsonMappingException e) {
            System.out.println(e.getMessage());
        } catch (JsonParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

//        //inner token that spring will use to define authentication
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//            credentials.getUsername(),
//            credentials.getPassword()//,
//            //new ArrayList<>()
//        );
//
//        System.out.println("=========================================================================================");
//        System.out.println("JwtAuthenticationFilter, attemptAuthentication, authenticationToken");
//        System.out.println(authenticationToken);
//        System.out.println("=========================================================================================");
//
//        Authentication auth = authenticationManager.authenticate(authenticationToken);
//
//        System.out.println("=========================================================================================");
//        System.out.println("JwtAuthenticationFilter, attemptAuthentication, auth");
//        System.out.println("isAuthenticated: " + auth.isAuthenticated());
//        System.out.println("Authorities: " + auth.getAuthorities());
//        System.out.println("Credentials: " + auth.getCredentials());
//        System.out.println("Principal: " + auth.getPrincipal());
//        System.out.println("Details: " + auth.getDetails());
//        System.out.println("=========================================================================================");
//
//        return auth;
//        //return super.attemptAuthentication(request, response);
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // Grab principal
        UserDetails principal = (UserDetails) authResult.getPrincipal();
        //UserDetails principal = (UserDetails) authResult.getPrincipal();
        // Create JWT Token
//        String token = JWT.create()
//                .withSubject(principal.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
//                .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes())) //actual
                //.signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                //.signWith(SignatureAlgorithm.HS512, JwtProperties.SECRET)
                .compact();

        // Add token in response
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                "{\"" + JwtProperties.HEADER_STRING + "\":\"" + JwtProperties.TOKEN_PREFIX + token + "\"}"
//                "{\"" + JwtProperties.HEADER_STRING + "\":\"" + JwtProperties.TOKEN_PREFIX + token +
//                        " Username:" + principal.getUsername() +
//                        " Role:" + principal.getAuthorities() + "\"}"
        );
    }
}
