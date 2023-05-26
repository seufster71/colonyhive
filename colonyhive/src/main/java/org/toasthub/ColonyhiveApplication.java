package org.toasthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ColonyhiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColonyhiveApplication.class, args);
	}

}
