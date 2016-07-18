package org.cellang.collector;

import java.io.File;

public class CollectorMain {

	public static void main(String[] args) throws Exception {

		File output = new File(EnvUtil.getDataDir(), "163");
		String typesS = System.getProperty("cellang.collector.types");
		String[] types = typesS.split(",");

		String piS = System.getProperty("cellang.collector.pause-interval");
		long pi = 10 * 1000;
		if (piS == null) {
			pi = Long.parseLong(piS);
		}
		new NeteaseCollector(output).pauseInterval(pi).types(types).start();

	}

}
