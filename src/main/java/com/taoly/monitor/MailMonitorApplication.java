package com.taoly.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/27 10:26
 * @ Description：
 */
@EnableJpaAuditing
@EnableTransactionManagement //开启事务
@SpringBootApplication(scanBasePackages = {"com.taoly.monitor.*"})
@EnableScheduling //定时任务
public class MailMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailMonitorApplication.class, args);
    }
}
