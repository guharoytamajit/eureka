package com;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;

public class EurekaHealthCheckHandler implements HealthCheckHandler, ApplicationContextAware, InitializingBean {
	 
    private static final Map<Status, InstanceInfo.InstanceStatus> healthStatuses = new HashMap<Status, InstanceInfo.InstanceStatus>() {{
        put(Status.UNKNOWN, InstanceInfo.InstanceStatus.UNKNOWN);
        put(Status.OUT_OF_SERVICE, InstanceInfo.InstanceStatus.OUT_OF_SERVICE);
        put(Status.DOWN, InstanceInfo.InstanceStatus.DOWN);
        put(Status.UP, InstanceInfo.InstanceStatus.UP);
    }};
 
    private final CompositeHealthIndicator healthIndicator;
 
    private ApplicationContext applicationContext;
 
    public EurekaHealthCheckHandler(HealthAggregator healthAggregator) {
        Assert.notNull(healthAggregator, "HealthAggregator must not be null");
 
        this.healthIndicator = new CompositeHealthIndicator(healthAggregator);
    }
     
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
 
    @Override
    public void afterPropertiesSet() throws Exception {
 
        final Map<String, HealthIndicator> healthIndicators = applicationContext.getBeansOfType(HealthIndicator.class);
        for (Map.Entry<String, HealthIndicator> entry : healthIndicators.entrySet()) {
            healthIndicator.addHealthIndicator(entry.getKey(), entry.getValue());
        }
    }
 
    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus instanceStatus) {
 
        return getHealthStatus();
    }
 
    protected InstanceInfo.InstanceStatus getHealthStatus() {
        final Status status = healthIndicator.health().getStatus();
        return mapToInstanceStatus(status);
    }
 
    protected InstanceInfo.InstanceStatus mapToInstanceStatus(Status status) {
        if(!healthStatuses.containsKey(status)) {
            return InstanceInfo.InstanceStatus.UNKNOWN;
        }
        return healthStatuses.get(status);
    }
}