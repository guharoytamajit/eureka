package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaServer
public class MyEurekaServerApplication {
/*	public class EurekaHealthCheckHandlerConfiguration {
		 
	    @Autowired(required = false)
	    private HealthAggregator healthAggregator = new OrderedHealthAggregator();
	 
	    @Bean
	    @ConditionalOnMissingBean
	    public EurekaHealthCheckHandler eurekaHealthCheckHandler() {
	        return new EurekaHealthCheckHandler(healthAggregator);
	    }
	}*/
	public static void main(String[] args) {
		SpringApplication.run(MyEurekaServerApplication.class, args);
	}
}
