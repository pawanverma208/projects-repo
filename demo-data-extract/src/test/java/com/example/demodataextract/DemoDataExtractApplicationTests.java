package com.example.demodataextract;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest
class DemoDataExtractApplicationTests {

	@Autowired
	private QueryRequestRepository queryRequestRepository;

	@Test
	void contextLoads() {
		Set<QueryParam> queryParams1 = new HashSet<>();
		queryParams1.add(QueryParam.creat("type", "t1"));
		queryParams1.add(QueryParam.creat("name", "p1"));

		QueryRequest q1 = QueryRequest.create("PVQ1", "select * form product", "pverma", queryParams1);
		QueryRequest q2 = QueryRequest.create("PVQ2", "select * form product", "pverma", new HashSet<>());

		System.out.println(queryRequestRepository.save(q1));
		System.out.println(queryRequestRepository.save(q2));

		System.out.println(queryRequestRepository.findByUserName("pverma"));
	}

}
