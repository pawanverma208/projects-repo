package com.example.demodataextract;

import org.springframework.data.annotation.Id;

public class QueryParam {

	@Id
	private Long id;
	private String name;
	private String value;

	public QueryParam() {
	}

	public QueryParam(Long id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public static QueryParam creat(String name, String value) {
		return new QueryParam(null, name, value);
	}
}
