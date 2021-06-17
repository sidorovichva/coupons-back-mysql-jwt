package com.vs.couponsbackmysqljwt.repositories;

import com.vs.couponsbackmysqljwt.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByNameOrEmail(String name, String email);

    boolean existsByNameAndIdNot(String name, int id);
    boolean existsByEmailAndIdNot(String email, int id);

    boolean existsByEmail(String email);

    Company findCompanyByEmailAndPassword(String email, String password);

    Company findCompanyByEmail(String email);
}
