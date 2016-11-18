package org.cellang.viewsframework.ops;

import java.io.File;

import org.cellang.collector.NeteasePreprocessor;
import org.cellang.collector.SinaAllQuotesPreprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wash Data.
 * 
 * @author wu
 *
 */
public class WashOp extends ConsoleOp<Void> {

	private static final Logger LOG = LoggerFactory.getLogger(WashOp.class);

	String source;

	public WashOp(String source) {
		this.source = source;
	}

	@Override
	public Void execute(OperationContext oc) {
		File dataHome = oc.getDataHome();
		File from = new File(dataHome, source);
		if (!from.exists()) {
			LOG.error("no data folder found:" + from);
		}

		File to = new File(dataHome, source + "pp");
		if ("163".equals(source)) {
			new NeteasePreprocessor(from, to).xjllb().process();
		} else if ("sina".equals(source)) {
			new SinaAllQuotesPreprocessor(from, to).process();
		} else {
			LOG.error("no source found:" + source);
		}
		return null;
	}

}
