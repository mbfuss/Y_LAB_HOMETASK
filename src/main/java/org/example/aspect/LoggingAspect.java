package org.example.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

    @Before("execution(* org.example.service.*.*(..))")
    public void logBeforeServiceMethods() {
        System.out.println("Before method execution");
    }

    @After("execution(* org.example.service.*.*(..))")
    public void logAfterServiceMethods() {
        System.out.println("After method execution");
    }
}
