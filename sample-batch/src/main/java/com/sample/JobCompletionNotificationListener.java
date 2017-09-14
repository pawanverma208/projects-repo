package com.sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.sample.domain.Person;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		String fileName = jobExecution.getJobParameters().getString("input.file.name");
		log.info(">>> beforeJob : " + fileName);
		if (true)
			return;
		File theDir = new File(FilenameUtils.removeExtension(fileName));
		theDir.mkdir();
		File file = new File(fileName);
		Path src = Paths.get(fileName);
		Path dest = Paths.get(theDir.getAbsolutePath() + "\\" + file.getName());
		try {
			if (dest.toFile().exists())
				dest.toFile().delete();
			Files.copy(src, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM people",
					new RowMapper<Person>() {
						@Override
						public Person mapRow(ResultSet rs, int row) throws SQLException {
							return new Person(rs.getString(1), rs.getString(2));
						}
					});

			for (Person person : results) {
				log.info("Found <" + person + "> in the database.");
			}

		}
	}
}
