package com.example.demodataextract;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@RestController
public class DemoDataExtractApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDataExtractApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(QueryRequestRepository queryRequestRepository) {
		return arg -> {
			Set<QueryParam> queryParams1 = new HashSet<>();
			queryParams1.add(QueryParam.creat("type", "t1"));
			queryParams1.add(QueryParam.creat("name", "p1"));

			QueryRequest q1 = QueryRequest.create("PVQ1", "select * form product", "pverma", queryParams1);
			QueryRequest q2 = QueryRequest.create("PVQ2", "select * form product", "pverma", new HashSet<>());

			System.out.println(queryRequestRepository.save(q1));
			System.out.println(queryRequestRepository.save(q2));

			System.out.println(queryRequestRepository.findByUserName("pverma"));
		};
	}
	// http://localhost:8080/hello?name=Pawan
//	@GetMapping("/hello")
//	private String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//		return String.format("Hello %s!!", name);
//	}
}
