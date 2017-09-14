package com.sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class FileResourcePartitioner implements Partitioner {

	private static final Logger log = LoggerFactory.getLogger(FileResourcePartitioner.class);
	private Resource resource;

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		// TODO Auto-generated method stub

		if (!resource.exists())
			return new HashMap<>();
		Assert.state(resource.exists(), "Resource does not exist: " + resource);

		String fileName;
		try {
			fileName = resource.getFile().getAbsolutePath();
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		log.info(">>> partition>>>>> : " + fileName);
		File theDir = new File(FilenameUtils.removeExtension(fileName));
		theDir.mkdir();
		File file = new File(fileName);
		Path src = Paths.get(fileName);
		String trgt1 = theDir.getAbsolutePath() + File.separator + file.getName();
		String trgt2 = theDir.getAbsolutePath() + File.separator + "2_" + file.getName();
		Path dest1 = Paths.get(trgt1);
		Path dest2 = Paths.get(trgt2);
		try {
			if (dest1.toFile().exists())
				dest1.toFile().delete();
			Files.copy(src, dest1);

			if (dest2.toFile().exists())
				dest2.toFile().delete();
			Files.copy(src, dest2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		Map<String, ExecutionContext> map = new HashMap<>(2);

		ExecutionContext context = new ExecutionContext();
		context.putString("partition.file.name", dest1.toFile().getAbsolutePath());
		context.putString("name", "Thread" + 1);
		map.put("partition" + 1, context);

		System.out.println("=============>>>>>>>>>>>>>>>>>>>>" + context.getString("partition.file.name"));

		ExecutionContext context2 = new ExecutionContext();
		context2.putString("partition.file.name", dest2.toFile().getAbsolutePath());
		context2.putString("name", "Thread" + 2);
		map.put("partition" + 2, context2);

		System.out.println("=============>>>>>>>>>>>>>>>>>>>>" + context2.getString("partition.file.name"));

		return map;
	}

}
