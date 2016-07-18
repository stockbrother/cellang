package org.cellang.console.ops;

import java.io.File;

import org.cellang.core.loader.DataLoader;
import org.cellang.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadOp extends ConsoleOp<Void> {

	private static final Logger LOG = LoggerFactory.getLogger(LoadOp.class);

	private String folderName;

	public LoadOp(String folderName) {
		this.folderName = folderName;
	}

	@Override
	public Void execute(OperationContext oc) {
		DataLoader loader = new DataLoader(oc.getEntityService());
		File qfile = FileUtil.newFile(oc.getDataHome(), new String[] { folderName });
		if (!qfile.exists()) {
			LOG.error("folder not exists:" + qfile.getAbsolutePath());//
			return null;
		}
		loader.load(qfile);
		LOG.info("done of load,folder:" + folderName);
		return null;
	}

}
