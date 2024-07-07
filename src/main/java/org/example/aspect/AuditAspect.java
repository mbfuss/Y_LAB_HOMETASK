package org.example.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    @After("execution(* org.example.service.*.*(..))")
    public void auditAction() {
        // Логика аудита, например запись действий в лог или базу данных
        System.out.println("Audit: User performed an action.");
    }
}