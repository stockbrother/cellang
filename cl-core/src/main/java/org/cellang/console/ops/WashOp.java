package org.cellang.console.ops;

import java.io.File;

import org.cellang.collector.NeteasePreprocessor;
import org.cellang.collector.SinaAllQuotesPreprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WashOp extends ConsoleOp<Void>{

	private static final Logger LOG = LoggerFactory.getLogger(WashOp.class);

	String source;
	File dataHome;
	@Override
	public Void execute(OperationContext oc) {
		File from = new File(this.dataHome, source);
		if (!from.exists()) {
			LOG.error("no data folder found:" + from);
		}

		File to = new File(this.dataHome, source + "pp");
		if ("163".equals(source)) {
			new NeteasePreprocessor(from, to).process();
		} else if ("sina".equals(source)) {
			new SinaAllQuotesPreprocessor(from, to).process();
		} else {
			LOG.error("no source found:" + source);
		}
		return null;
	}

	public WashOp set(File dataHome,String source) {
		this.dataHome = dataHome;
		this.source = source;
		return this;
	}

}
