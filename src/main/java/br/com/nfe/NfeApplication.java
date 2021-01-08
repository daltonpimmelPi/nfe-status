package br.com.nfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class NfeApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(NfeApplication.class, args);
	}

}
