package org.cellang.core.loader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.cellang.core.entity.EntityService;

public class DataLoader {
	private EntityService es;
	private Map<String, FileProcessor> processMap = new HashMap<String, FileProcessor>();

	public DataLoader(EntityService es) {
		this.es = es;
		processMap.put("corplist", new CorpListFileProcessor(es));
		processMap.put("zcfzb", new BalanceSheetFileProcessor(es));
		processMap.put("lrb", new IncomeStatementFileProcessor(es));
		processMap.put("all-quotes", new AllQuotesFileProcessor(es));
	}

	public void loadDir(File dir) {

		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				this.loadDir(f);
				continue;
			}
			String fname = f.getName();
			String[] fnames = fname.split("\\.");

			if (fnames[fnames.length - 1].equals("csv")) {
				String ftype = fnames[fnames.length - 2];
				processMap.get(ftype).process(f);//
			}

		}

	}

}
