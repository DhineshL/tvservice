package com.dhinesh.tvservice;

import com.dhinesh.tvservice.repository.ApplicationUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = ApplicationUserRepository.class)
@EnableCaching
public class TvserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TvserviceApplication.class, args);
	}

}
