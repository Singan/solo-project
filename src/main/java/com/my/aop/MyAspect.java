package com.my.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MyAspect {
    public MyAspect() {
        System.out.println("생성");
    }

    @Pointcut("@within(com.my.aop.LogClass)")
    public void logPointcut() {

    }

    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("{} 메서드 실행 : {}", joinPoint.getSignature().toShortString(), args);
        Object result = null;
        try {
            result = joinPoint.proceed();
        }catch (Exception e){
            log.info("exception 발생 {} " , e);
        }
        long startMills = System.currentTimeMillis();
        long endMills = System.currentTimeMillis() - startMills;
        log.info("{} 메서드 실행 소요 시간 : {}", joinPoint.getSignature(), endMills);
        return result;
    }


}
