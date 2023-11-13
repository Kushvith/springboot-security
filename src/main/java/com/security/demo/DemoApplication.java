package com.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.security.demo.entity.Role;
import com.security.demo.entity.user;
import com.security.demo.repository.UserRespository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private UserRespository userRespository;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		user AdminUser = userRespository.findByRole(Role.ADMIN);
		if(AdminUser == null){
			user user = new user();
			user.setEmail("Admin@gmail.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRole(Role.ADMIN);
			userRespository.save(user);
		}
	}

}
