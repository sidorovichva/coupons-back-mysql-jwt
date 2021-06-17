package com.vs.couponsbackmysqljwt.security;

import com.vs.couponsbackmysqljwt.repositories.CompanyRepository;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import com.vs.couponsbackmysqljwt.security.jwt.amigo.JwtTokenVerifier;
import com.vs.couponsbackmysqljwt.security.jwt.romanian.JwtAuthenticationFilter;
import com.vs.couponsbackmysqljwt.security.jwt.romanian.UserPrincipalDetailsService;
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
        System.out.println("=========================================================================================");
        System.out.println("ApplicationSecurityConfig, configure, beginning");
        System.out.println(auth.toString());
        System.out.println("=========================================================================================");
        auth.authenticationProvider(userAuthProvider());
        /*auth.inMemoryAuthentication()
                .withUser("a")
                .password(passwordEncoder.encode("a"))
                .roles(ADMINISTRATOR.toString());*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /*.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())//to avoid cross site request forgery
                .and()*/
                .cors().and()
                .csrf().disable() //use for formBase authentication
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //this state "disables" cookie check. We dont need it to work with JWT
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) //JWT first filter
                //.addFilter(new JwtAuthorizationFilter(authenticationManager(), customerRepository, companyRepository))
                //.addFilterAfter(new JwtTokenVerifier(), JwtAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenVerifier(), JwtAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/login").permitAll()
                    .antMatchers("/", "/index", "/static/**").permitAll() //don't need authentication
                    .antMatchers("/companies/**", "/categories/**", "/customers/**").hasRole(ADMINISTRATOR.toString())
                    .antMatchers("/coupons").hasRole(COMPANY.toString())
                    .antMatchers("/purchases/**").hasRole(CUSTOMER.toString())
                    .anyRequest()
                    .authenticated();
                /*.and()
                .exceptionHandling().authenticationEntryPoint(restAuthEntryPoint) //has nothing with the JWT implementation
                .and()*/
    }

    @Bean
    public DaoAuthenticationProvider userAuthProvider() {
        System.out.println("=========================================================================================");
        System.out.println("ApplicationSecurityConfig, userAuthProvider");
        System.out.println("=========================================================================================");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        //provider.setUserDetailsService(userAuthenticationService);
        provider.setUserDetailsService(this.userPrincipalDetailsService);
        return provider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        System.out.println("=========================================================================================");
        System.out.println("ApplicationSecurityConfig, corsConfigurationSource");
        System.out.println("=========================================================================================");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "*",
                "http://localhost:3000",
                "http://localhost:8080"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

// *******************************************
// FormBase Authentication
// *******************************************
/*package com.vs.coupons.security;

        import com.vs.coupons.security.authentication.UserAuthenticationService;
        import lombok.RequiredArgsConstructor;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
        import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
        import org.springframework.web.cors.CorsConfiguration;
        import org.springframework.web.cors.CorsConfigurationSource;
        import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

        import java.util.Arrays;
        import java.util.concurrent.TimeUnit;

        import static com.vs.coupons.enums.ClientType.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //to enable @PreAuthorized on each method separately
//on method @PreAuthorize("hasRole('ROLE_Administrator')") //individual authorization
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationService userAuthenticationService;
    private final RestAuthEntryPoint restAuthEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthProvider());
        auth.inMemoryAuthentication()
                .withUser("a")
                .password(passwordEncoder.encode("a"))
                .roles(ADMINISTRATOR.toString());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                *//*.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())//to avoid cross site request forgery
                .and()*//*
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/static/**").permitAll() //don't need authentication
                .antMatchers("/companies/**", "/categories/**", "/customers/**").hasRole(ADMINISTRATOR.toString())
                .antMatchers("/coupons/**").hasRole(COMPANY.toString())
                .antMatchers("/purchases/**").hasRole(CUSTOMER.toString())
                *//*.antMatchers("/coupons/**").hasAuthority(COUPONS.toString())
                .antMatchers(HttpMethod.DELETE, "/**").hasRole(ADMINISTRATOR.getDescription().toString())
                .antMatchers(HttpMethod.POST, "/**").hasRole(ADMINISTRATOR.getDescription().toString())
                .antMatchers(HttpMethod.PUT, "/**").hasRole(ADMINISTRATOR.getDescription().toString())
                .antMatchers(HttpMethod.GET, "/**").hasRole(ADMINISTRATOR.getDescription().toString())
                .antMatchers(HttpMethod.GET, "/**").hasAnyRole(
                        ADMINISTRATOR.getDescription().toString(), COMPANY.getDescription().toString())*//*
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthEntryPoint)
                .and()
                .formLogin()
                //.loginPage("/login")
                .loginProcessingUrl("/login").permitAll()
                .defaultSuccessUrl("/home", true)
                .failureUrl("/failure")
                .passwordParameter("password")
                .usernameParameter("username")
                .and()
                .rememberMe() //default to 2 weeks
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1))
                .key("somethingverysecure")
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //only if csrf is disabled
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login");
    }

    @Bean
    public DaoAuthenticationProvider userAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userAuthenticationService);
        return provider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "*",
                "http://localhost:3000"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}*/
