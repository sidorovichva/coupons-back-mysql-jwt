package com.vs.couponsbackmysqljwt.security;

import com.vs.couponsbackmysqljwt.repositories.CompanyRepository;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.vs.couponsbackmysqljwt.enums.ClientType.*;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) //to enable @PreAuthorized on each method separately
//on method @PreAuthorize("hasRole('ROLE_Administrator')") //individual authorization
@RequiredArgsConstructor
@Data
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;


    //private final UserAuthenticationService userAuthenticationService;
    private final UserPrincipalDetailsService userPrincipalDetailsService;
    //private final RestAuthEntryPoint restAuthEntryPoint;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    //private final BasicAuthenticationEntryPoint basicAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /*.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())//to avoid cross site request forgery
                .and()*/
                .cors().and()
                .csrf().disable() //use for formBase authentication
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //this state "disables" cookie check. No need it to work with JWT
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) //JWT first filter
                .addFilterBefore(new JwtTokenVerifier(), JwtAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/login", "/customers").permitAll()
                    .antMatchers(HttpMethod.GET, "/companies", "/user/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/categories").hasRole(COMPANY.toString())
                    .antMatchers("/", "/index", "/static/**").permitAll()
                    .antMatchers("/companies/**", "/categories/**", "/customers/**").hasRole(ADMINISTRATOR.toString())
                    .antMatchers("/coupons/**").hasRole(COMPANY.toString())
                    .antMatchers("/purchases/**").hasRole(CUSTOMER.toString())
                    .anyRequest()
                    .authenticated();
                /*.and()
                .exceptionHandling().authenticationEntryPoint(restAuthEntryPoint) //has nothing with the JWT implementation
                .and()*/
    }

    @Bean
    public DaoAuthenticationProvider userAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        //provider.setUserDetailsService(userAuthenticationService);
        provider.setUserDetailsService(this.userPrincipalDetailsService);
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        /*configuration.setAllowedOriginPatterns(Arrays.asList(
                "netlify.app/"
        ));*/
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "https://boring-brown-e3f522.netlify.app/",
                "https://thirsty-nobel-143a4c.netlify.app/"
        ));

        //https://stackoverflow.com/questions/40418441/spring-security-cors-filter
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}