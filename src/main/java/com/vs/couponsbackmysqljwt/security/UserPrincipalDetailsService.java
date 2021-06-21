package com.vs.couponsbackmysqljwt.security;

import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.repositories.CompanyRepository;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import com.vs.couponsbackmysqljwt.security.UserPrincipal;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.vs.couponsbackmysqljwt.enums.ClientType.ADMINISTRATOR;
import static com.vs.couponsbackmysqljwt.enums.ClientType.CUSTOMER;

@Service
@RequiredArgsConstructor
@Data
public class UserPrincipalDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("=========================================================================================");
        System.out.println("UserPrincipalDetailsService, loadUserByUsername");
        System.out.println("=========================================================================================");
        Customer customer = this.customerRepository.findCustomerByEmail(email);
        System.out.println("=========================================================================================");
        System.out.println("UserPrincipalDetailsService, loadUserByUsername, customer");
        System.out.println(customer);
        System.out.println("=========================================================================================");
        if (customer != null) {
            //UserPrincipal userPrincipal = new UserPrincipal(customer, null);
            UserDetails newUser = User.withUsername(customer.getEmail())
                    .password(customer.getPassword())
                    .roles(CUSTOMER.toString())
                    .build();
            System.out.println("=========================================================================================");
            System.out.println("UserPrincipalDetailsService, loadUserByUsername, if customer");
            System.out.println(newUser.getUsername());
            System.out.println(newUser.getPassword());
            System.out.println(newUser.getAuthorities());
            System.out.println("=========================================================================================");
            return newUser;
        }
        Company company = this.companyRepository.findCompanyByEmail(email);
        System.out.println("=========================================================================================");
        System.out.println("UserPrincipalDetailsService, loadUserByUsername, company");
        System.out.println(company);
        System.out.println("=========================================================================================");
        if (company != null) {
            UserPrincipal userPrincipal = new UserPrincipal(null, company);
            System.out.println("=========================================================================================");
            System.out.println("UserPrincipalDetailsService, loadUserByUsername, if company");
            System.out.println(userPrincipal.getUsername());
            System.out.println(userPrincipal.getPassword());
            System.out.println(userPrincipal.getAuthorities());
            System.out.println("=========================================================================================");
            return userPrincipal;
        }
        if (email.equals("admin")) {
            UserDetails newUser = User.withUsername("admin")
                    .password(passwordEncoder.encode("a"))
                    .roles(ADMINISTRATOR.toString())
                    .build();
            System.out.println("=========================================================================================");
            System.out.println("UserPrincipalDetailsService, loadUserByUsername, if admin");
            System.out.println(newUser.getUsername());
            System.out.println(newUser.getPassword());
            System.out.println(newUser.getAuthorities());
            System.out.println("=========================================================================================");
            return newUser;
        }
        System.out.println("=========================================================================================");
        System.out.println("UserPrincipalDetailsService, loadUserByUsername, return null");
        System.out.println("=========================================================================================");
        return null;
    }
}
