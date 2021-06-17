package com.vs.couponsbackmysqljwt.AOP;

import com.vs.couponsbackmysqljwt.Exceptions.CouponRESTExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class SpecifyExceptionReason {

    @Around(value = "@annotation(specifyException)")
    public Object handlerAround(ProceedingJoinPoint joinPoint, SpecifyException specifyException) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (CouponRESTExceptionHandler e) {
            throw e;
        } catch (Exception e) {
            throw new CouponRESTExceptionHandler(specifyException.exception().getFailure(), e.getMessage());
        }
    }
}
