package com.dashaun.demo.eurekaserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaserverApplication.class, args);
    }

}

@Component
class SpringInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> springBootDetails = new HashMap<>();
        springBootDetails.put("version", SpringBootVersion.getVersion());
        Map<String, Object> springDetails = new HashMap<>();
        springDetails.put("boot", springBootDetails);
        builder.withDetail("spring", springDetails);
    }
}

@Component
class JavaInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> javaDetails = new HashMap<>();
        javaDetails.put("version", System.getProperty("java.version"));
        javaDetails.put("vendor", System.getProperty("java.vendor"));
        javaDetails.put("vm", System.getProperty("java.vm.name"));
        builder.withDetail("java", javaDetails);
    }
}

@Component
class StartupInfoContributor implements InfoContributor {

    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("h:mma 'on' MMMM d, yyyy", Locale.US);

    @Value("${demo.timezone:America/Chicago}")
    private String displayZone;

    @Override
    public void contribute(Info.Builder builder) {
        long startedAtMillis = ManagementFactory.getRuntimeMXBean().getStartTime();
        long uptimeSeconds = ManagementFactory.getRuntimeMXBean().getUptime() / 1000L;
        Map<String, Object> startupDetails = new HashMap<>();
        startupDetails.put("startedAt", DISPLAY_FORMAT.format(Instant.ofEpochMilli(startedAtMillis)
                .atZone(ZoneId.of(displayZone))));
        startupDetails.put("epochMillis", startedAtMillis);
        startupDetails.put("uptimeSeconds", uptimeSeconds);
        builder.withDetail("startup", startupDetails);
    }
}
