package org.cellang.core.loader;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.QuotesEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public class AllQuotesFileProcessor extends FileProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(BalanceSheetFileProcessor.class);
	private Map<String, String> header2columnMap = new HashMap<String, String>();
	EntitySessionFactory esf;

	public AllQuotesFileProcessor(EntitySessionFactory es) {
		this.esf = es;
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
		this.esf.execute(op);
	}

	public void doProcess(EntitySession es, Reader fr) {

		CSVReader reader = new CSVReader(fr);
		Map<String, Integer> columnIndexMap = new HashMap<String, Integer>();
		try {

			String[] header = reader.readNext();
			for (int i = 0; i < header.length; i++) {
				columnIndexMap.put(header[i], i);

			}
			String[] line = null;
			// skip header
			while (true) {
				line = reader.readNext();
				if (line == null) {
					break;
				}
				String code = getColumn(columnIndexMap, "code", line);
				String name = getColumn(columnIndexMap, "name", line);
				QuotesEntity ce = new QuotesEntity();
				ce.setId(code);
				ce.setCode(code);
				ce.setName(name);

				ce.setSettlement(new BigDecimal(getColumn(columnIndexMap, "settlement", line)));//
				es.save(ce);
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);

		}
	}

	private String getColumn(Map<String, Integer> columnIndexMap, String key, String[] line) {
		Integer idxO = columnIndexMap.get(key);
		if (idxO == null) {
			throw new RuntimeException("no this key:" + key);
		}
		return line[idxO];

	}

}
