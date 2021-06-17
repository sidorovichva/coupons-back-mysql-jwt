package com.vs.couponsbackmysqljwt.AOP;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Order(2)
@RequiredArgsConstructor
public class TestMethodHandler {

    @Pointcut("execution(* com.vs.coupons.services.*.*(..))")
    public void requestMapping() {}

    //@Before(value = "@annotation(TestMessage)")
    @Before("requestMapping()")
    public void handler(JoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        if (args.length > 0) System.out.printf("\nMethod \"%s\" is starting with the following arguments:\n", method.getName());
        else System.out.printf("\nMethod \"%s\" is starting:\n", method.getName());
        Arrays.stream(args).forEach(p -> System.out.println(p.getClass().getSimpleName() + ": " + p));
    }

    //@AfterReturning(value = "@annotation(TestMessage)")
    @AfterReturning("requestMapping()")
    public void handlerAfterReturning(JoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Executed successfully");
    }
}
