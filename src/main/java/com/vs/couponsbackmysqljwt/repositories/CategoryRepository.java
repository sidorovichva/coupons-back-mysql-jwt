package com.vs.couponsbackmysqljwt.repositories;

import com.vs.couponsbackmysqljwt.beans.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);
}
