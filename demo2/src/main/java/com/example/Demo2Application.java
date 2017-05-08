package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.InfoProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Demo2Application {
	
	@Autowired
	EurekaClient eurekaClient;
	
	@Value("${server.port}")
	String port;


	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	@Autowired
	RestTemplate restTemplate;
	@Controller
	class MyController{
	@RequestMapping("test")
	@ResponseBody
	public Info test(){
		
		return restTemplate.getForObject("http://DEMO/info", Info.class);
	}
	@RequestMapping("info")
	@ResponseBody
	public Info info(){
		return new Info("demo2",port);
	}
	@RequestMapping("nextService")
	@ResponseBody
	public void nextService(){
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("DEMO", false);
		System.out.println(instanceInfo.getHomePageUrl());
	}
	}
	public static void main(String[] args) {
		SpringApplication.run(Demo2Application.class, args);
	}
	
}
