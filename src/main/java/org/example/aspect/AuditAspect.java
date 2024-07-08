package org.example.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AuditAspect {

    @After("execution(* org.example.service.*.*(..))")
    public void auditServiceMethods() {
        System.out.println("Audit log: method executed");
    }
}
