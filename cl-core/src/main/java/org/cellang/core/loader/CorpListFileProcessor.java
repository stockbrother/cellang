package org.cellang.core.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public class CorpListFileProcessor extends FileProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(BalanceSheetFileProcessor.class);
	private Map<String, String> header2columnMap = new HashMap<String, String>();
	EntityService es;

	public CorpListFileProcessor(EntityService es) {
		this.es = es;
		header2columnMap.put("公司代码", "code");
		header2columnMap.put("公司名称", "name");
	}

	@Override
	public void process(Reader fr) {
		
		CSVReader reader = new CSVReader(fr);
		try {

			String[] next = reader.readNext();

			// skip header
			while (true) {
				next = reader.readNext();
				if (next == null) {
					break;
				}
				String code = next[0].trim();
				String name = next[1].trim();
				CorpInfoEntity ce = new CorpInfoEntity();
				ce.setId(code);
				ce.setCode(code);
				ce.setName(name);
				this.es.save(ce);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);

		}
	}

}
