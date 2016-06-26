package org.cellang.core;

import java.io.File;
import java.io.StringWriter;

import org.cellang.core.entity.EntityService;
import org.cellang.core.reporter.FuzhailvReporter;
import org.cellang.core.util.FileUtil;

public class Main {
	public static void main(String[] args) {
		File dbHome = FileUtil.createTempDir("main");

		EntityService es = EntityService.newInstance(dbHome, "h2");

		DataLoader dl = new DataLoader(es);
		File dfile = new File("src" + File.separator + "main" + File.separator + "doc");
		dl.loadDir(dfile);

		FuzhailvReporter fr = new FuzhailvReporter(es);

		StringWriter sw = new StringWriter();

		fr.generate(sw);
		System.out.println(sw.toString());

	}
}
