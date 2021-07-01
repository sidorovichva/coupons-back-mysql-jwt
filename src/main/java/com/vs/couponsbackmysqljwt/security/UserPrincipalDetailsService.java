package com.vs.couponsbackmysqljwt.security;

import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.repositories.CompanyRepository;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import com.vs.couponsbackmysqljwt.security.UserPrincipal;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

//    @Value("${admin.username}")
//    private String adminName;
//    @Value("${admin.password}")
//    private String adminPass;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = this.customerRepository.findCustomerByEmail(email);
        if (customer != null) {
            //UserPrincipal userPrincipal = new UserPrincipal(customer, null);
            UserDetails newUser = User.withUsername(customer.getEmail())
                    .password(customer.getPassword())
                    .roles(CUSTOMER.toString())
                    .build();
            return newUser;
        }
        Company company = this.companyRepository.findCompanyByEmail(email);
        if (company != null) {
            UserPrincipal userPrincipal = new UserPrincipal(null, company);
            return userPrincipal;
        }
        if (email.equals("admin")) {
            UserDetails newUser = User.withUsername("admin")
                    .password(passwordEncoder.encode("a"))
                    .roles(ADMINISTRATOR.toString())
                    .build();
            return newUser;
        }
        return null;
    }
}
