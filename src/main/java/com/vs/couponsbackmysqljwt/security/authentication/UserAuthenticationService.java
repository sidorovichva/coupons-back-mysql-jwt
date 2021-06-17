package com.vs.couponsbackmysqljwt.security.authentication;

import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.repositories.CompanyRepository;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.vs.couponsbackmysqljwt.enums.ClientType.*;

@Service
@RequiredArgsConstructor
@Data
public class UserAuthenticationService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("=========================================================================================");
        System.out.println("UserAuthenticationService, loadUserByUsername, email");
        System.out.println(email);
        System.out.println("=========================================================================================");

        Customer customer = this.customerRepository.findCustomerByEmail(email);
        System.out.println("=========================================================================================");
        System.out.println("UserAuthenticationService, loadUserByUsername, customer");
        System.out.println(customer);
        System.out.println("=========================================================================================");
        if (customer != null) {
            UserDetails newUser = User.withUsername(customer.getEmail())
                    .password(customer.getPassword())
                    .roles(CUSTOMER.toString())
                    .build();
//            CustomerAuthentication newUser = new CustomerAuthentication(customer);
            return newUser;
        }
        Company company = this.companyRepository.findCompanyByEmail(email);
        System.out.println("=========================================================================================");
        System.out.println("UserAuthenticationService, loadUserByUsername, company");
        System.out.println(company);
        System.out.println("=========================================================================================");
        if (company != null) {
            CompanyAuthentication newUser = new CompanyAuthentication(company);
            return newUser;
        }

        //only for romanian JWT option
        if (email.equals("a")) {
            UserDetails newUser = User.withUsername("a")
                    .password("a")
                    .roles(ADMINISTRATOR.toString())
                    .build();
//            AdminAuthentication newUser = new AdminAuthentication();
            return newUser;
        }
        System.out.println("user not found");
        throw new UsernameNotFoundException("user not found");
    }
}