package org.cellang.collector;

import java.io.File;

public class CollectorMain {

	public static void main(String[] args) throws Exception {

		File output = new File(EnvUtil.getDataDir(), "163");

		new NeteaseCollector(output).types(NeteaseCollector.TYPE_xjllb).start();
		
	}

}
