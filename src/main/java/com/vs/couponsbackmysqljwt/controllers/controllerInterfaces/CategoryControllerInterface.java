package com.vs.couponsbackmysqljwt.controllers.controllerInterfaces;

import com.vs.couponsbackmysqljwt.beans.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryControllerInterface {
    ResponseEntity<?> addCategory(Category category) throws Exception;
    ResponseEntity<?> getAllCategories() throws Exception;
}
