package org.cellang.core.loader;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public class CorpListFileProcessor extends FileProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(BalanceSheetFileProcessor.class);
	private Map<String, String> header2columnMap = new HashMap<String, String>();
	EntitySessionFactory es;

	public CorpListFileProcessor(EntitySessionFactory es) {
		this.es = es;
		header2columnMap.put("公司代码", "code");
		header2columnMap.put("公司名称", "name");
	}

	@Override
	public void process(Reader fr) {
		EntityOp<Void> op = new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				doProcess(es, fr);
				return null;
			}
		};
		this.es.execute(op);
	}

	public void doProcess(EntitySession es, Reader fr) {
	
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
				es.save(ce);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);

		}
	}

}
