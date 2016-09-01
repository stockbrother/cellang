package org.cellang.core.loader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataLoader {
	private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);

	private EntitySessionFactory esf;
	private Map<String, FileProcessor> processMap = new HashMap<String, FileProcessor>();

	public DataLoader(EntitySessionFactory esf) {
		this.esf = esf;
		processMap.put("corplist", new CorpListFileProcessor(esf));
		processMap.put("zcfzb", new BalanceSheetFileProcessor(esf));
		processMap.put("lrb", new IncomeStatementFileProcessor(esf));
		processMap.put("xjllb", new CashFlowStatementFileProcessor(esf));
		processMap.put("all-quotes", new AllQuotesFileProcessor(esf));
	}

	public void load(File dirOrFile) {
		EntityOp<Void> op = new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				doLoadDirOrFile(dirOrFile, es);
				return null;
			}

		};
		esf.execute(op);
	}

	private void doLoadDirOrFile(File dir, EntitySession es) {

		if (dir.isFile()) {
			File f = dir;
			String fname = f.getName();
			String[] fnames = fname.split("\\.");

			if (fnames[fnames.length - 1].equals("csv")) {
				String ftype = fnames[fnames.length - 2];
				FileProcessor fp = processMap.get(ftype);
				if (fp == null) {
					LOG.warn("no processor found for file:" + f.getAbsolutePath() + ",type:" + ftype);
				}
				fp.process(f);//
			}
			return;
		}

		// is directory

		for (File f : dir.listFiles()) {
			this.doLoadDirOrFile(f, es);
		}

	}

}
