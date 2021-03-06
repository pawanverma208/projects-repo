package com.sample;

import java.io.File;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

public class FileMessageToJobRequest {
	private Job job;
	private String fileParameterName;

	public void setFileParameterName(String fileParameterName) {
		this.fileParameterName = fileParameterName;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Transformer
	public JobLaunchRequest toRequest(Message<File> message) {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

		jobParametersBuilder.addString(fileParameterName, message.getPayload().getAbsolutePath());
		jobParametersBuilder.addParameter("timeStamp", new JobParameter(new Date()));

		return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
	}

	public Job getJob() {
		return job;
	}

	public String getFileParameterName() {
		return fileParameterName;
	}
}
