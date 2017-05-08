package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DemoApplication {
	@Autowired
	EurekaClient eurekaClient;
	
//	@Value("${server.port}")
//	String port;

	@Autowired
	Environment env;
	@Controller
	class MyController{
	@RequestMapping("info")
	@ResponseBody
	public Info info(){
		return new Info("demo",env.getProperty("server.port"));
	}
	
	@RequestMapping("nextService")
	@ResponseBody
	public void nextService(){
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("DEMO2", false);
		System.out.println(instanceInfo.getHomePageUrl());
	}
	
	
	@RequestMapping("test")
	@ResponseBody
	public Info test(){
		
		return restTemplate.getForObject("http://DEMO2/info", Info.class);
	}
	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	@Autowired
	RestTemplate restTemplate;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
