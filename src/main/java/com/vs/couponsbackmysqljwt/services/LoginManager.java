package com.vs.couponsbackmysqljwt.services;

import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Customer;
import com.vs.couponsbackmysqljwt.enums.ClientType;
import com.vs.couponsbackmysqljwt.repositories.CompanyRepository;
import com.vs.couponsbackmysqljwt.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *Contain all log-in related methods and at the end creates a new instance of facade depending on user input
 */
@Service
@RequiredArgsConstructor
public class LoginManager{

    private final ApplicationContext applicationContext;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    /**
     * hard-coded admin login and e-mail
     */
    //private final StringBuffer adminMail = new StringBuffer("admin@admin.com");
    //private final StringBuffer adminPass = new StringBuffer("admin");
    private final StringBuffer adminMail = new StringBuffer("a");
    private final StringBuffer adminPass = new StringBuffer("a");

    /**
     * @param clientType type of the client, enum
     * @param email      entered by client email
     * @param password   entered by client password,
     *                   creates a new instance of facade depending on user input
     */

    public Facade login(ClientType clientType, String email, String password) {
        switch (clientType) {
            case ADMINISTRATOR:
                if (email.equals(adminMail.toString()) && password.equals(adminPass.toString())) {
                    return applicationContext.getBean(AdminFacade.class);
                }
            case COMPANY:
                Company company = companyRepository.findCompanyByEmailAndPassword(email, password);
                if (company != null) {
                    return applicationContext.getBean(CompanyFacade.class);
                }
            case CUSTOMER:
                Customer customer = customerRepository.findCustomerByEmailAndPassword(email, password);
                if (customer != null) {
                    return applicationContext.getBean(CustomerFacade.class);
                }
            default:
                return null;
        }
    }
}