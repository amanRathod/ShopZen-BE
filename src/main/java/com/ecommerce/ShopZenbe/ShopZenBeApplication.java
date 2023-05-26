package com.ecommerce.ShopZenbe;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class ShopZenBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopZenBeApplication.class, args);
	}

}
