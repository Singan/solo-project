package com.my.config;

import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MyConfig {

    @Bean
    public CountedAspect countedAspect(MeterRegistry meterRegistry){
        return new CountedAspect(meterRegistry);
    }
    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry){
        return new TimedAspect(meterRegistry);
    }

}
