package com.example.demodataextract;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QueryRequestRepository extends CrudRepository<QueryRequest, Long> {
	@Query("select * from query_request where user_name = :username ")
	List<QueryRequest> findByUserName(@Param("username") String userName);
}
