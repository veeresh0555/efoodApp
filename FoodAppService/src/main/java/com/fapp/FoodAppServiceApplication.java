package com.fapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.fapp.config.RibbonConfiguration;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RibbonClient(name="foodappsevice",configuration = RibbonConfiguration.class)
public class FoodAppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodAppServiceApplication.class, args);
	}

}
