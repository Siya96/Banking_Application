package com.safeBankAB.safebankapp;

import com.safeBankAB.safebankapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafeBankApp implements CommandLineRunner {

	@Autowired
	private AccountService as;

	public static void main(String[] args) {


		SpringApplication.run(SafeBankApp.class, args);


	}

	public void testCreate() {

		as.CreateAccountTest();
	}


	@Override
	public void run(String... args) throws Exception {


		testCreate();
	}

}
