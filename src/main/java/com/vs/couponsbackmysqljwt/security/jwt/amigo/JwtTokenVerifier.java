package com.vs.couponsbackmysqljwt.security.jwt.amigo;

import com.vs.couponsbackmysqljwt.security.jwt.romanian.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("=========================================================================================");
        System.out.println("JwtTokenVerifier, doFilterInternal, beginning");
        System.out.println("=========================================================================================");
        String authorizationHeader = request.getHeader("Authorization");

        System.out.println("=========================================================================================");
        System.out.println("JwtTokenVerifier, doFilterInternal, authorizationHeader");
        System.out.println(authorizationHeader);
        System.out.println("=========================================================================================");

        //if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");

        System.out.println("=========================================================================================");
        System.out.println("JwtTokenVerifier, doFilterInternal, token");
        System.out.println(token);
        System.out.println("=========================================================================================");

        try {
            /*Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(SignatureAlgorithm.HS512, JwtProperties.SECRET.getBytes("UTF-8"))
                    .setSigningKey(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes()))
                    .parseClaimsJws(token);*/

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes()))
                    .build().parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            String username = body.getSubject();
            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simple = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, null, simple);

            System.out.println("=========================================================================================");
            System.out.println("JwtTokenVerifier, doFilterInternal, authentication");
            System.out.println(authentication.getPrincipal());
            System.out.println(authentication.getDetails());
            System.out.println(authentication.getAuthorities());
            System.out.println(authentication.getCredentials());
            System.out.println(authentication.isAuthenticated());
            System.out.println(authentication.getName());
            System.out.println("=========================================================================================");

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }
        filterChain.doFilter(request, response);

        System.out.println("=========================================================================================");
        System.out.println("JwtTokenVerifier, doFilterInternal, finish");
        System.out.println("=========================================================================================");
    }
}

