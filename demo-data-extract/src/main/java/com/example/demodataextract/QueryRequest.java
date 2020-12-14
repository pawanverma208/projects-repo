package com.example.demodataextract;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class QueryRequest {

	@Id
	private Long queryId;
	private String name;
	private String query;
	private String userName;
	private Set<QueryParam> queryParams;

	public QueryRequest() {
	}

	public QueryRequest(Long queryId, String name, String query, String userName, Set<QueryParam> queryParams) {
		super();
		this.queryId = queryId;
		this.name = name;
		this.query = query;
		this.userName = userName;
		this.queryParams = queryParams;
	}

	public static QueryRequest create(String name, String query, String userName, Set<QueryParam> queryParams) {
		return new QueryRequest(null, name, query, userName, queryParams);
	}

	public void addQueryParam(QueryParam queryParam) {
		this.queryParams.add(queryParam);
	}

	@Override
	public String toString() {
		return "QueryRequest [queryId=" + queryId + ", name=" + name + ", query=" + query + ", userName=" + userName
				+ "]";
	}

}
