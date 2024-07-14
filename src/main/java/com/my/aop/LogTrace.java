package com.my.aop;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.internal.DefaultGauge;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.GaugeMetricFamily;
import io.prometheus.client.Summary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogTrace {
//    private final MeterRegistry meterRegistry;
//
//
//    @Pointcut("@within(com.my.aop.LogClass)")
//    public void apiCounter() {
//
//    }
//
//    @Around("apiCounter()")
//    public Object apiStart(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object[] args = joinPoint.getArgs();
//        long startMills = System.currentTimeMillis();
//        log.info("{} 메서드 실행 : {}", joinPoint.getSignature().toShortString(), args);
//        Object result = null;
//        // 메서드 실행 시간 측정
//
//        Timer.Sample sample = Timer.start(meterRegistry);
//
//        try {
//            result = joinPoint.proceed();
//            Counter.builder(joinPoint.getSignature().toShortString()) // 이름
//                    .tag("class", joinPoint.getSignature().toString()) // 태그1 ( class )
//                    .description(joinPoint.getSignature().toShortString()) // 설명
//                    .register(meterRegistry).increment();
//
//        } catch (Exception e) {
//            log.info("exception 발생 {} ", e);
//            throw e;
//        } finally {
//            sample.stop(Timer.builder(joinPoint.getSignature().toShortString() + "_gauge")
//                    .description("소요시간 측정")
//                    .register(meterRegistry));
//        }
//        long endMills = System.currentTimeMillis() - startMills;
//        log.info("{} 메서드 실행 소요 시간 : {}", joinPoint.getSignature(), endMills);
//        return result;
//    }

}
