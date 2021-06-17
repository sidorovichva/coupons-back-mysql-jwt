package com.vs.couponsbackmysqljwt.repositories;

import com.vs.couponsbackmysqljwt.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, int id);

    Customer findCustomerByEmailAndPassword(String email, String password);

    Customer findCustomerByEmail(String email);
}
