package org.cellang.core.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(FileProcessor.class);

	public void process(File file) {
		LOG.info("processor:" + this.getClass().getName() + " going to process file:" + file.getAbsolutePath());

		InputStream is;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		Reader reader = new InputStreamReader(is);
		this.process(reader);//
	}

	protected abstract void process(Reader reader);

}
