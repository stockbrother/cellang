package org.cellang.collector;

import java.io.File;

public class EnvUtil {
	public static File getDataDir() {
		File d = new File("D:\\data");
		if (d.exists()) {
			return d;
		}
		d = new File("C:\\D\\data");
		if (d.exists()) {
			return d;
		}
		throw new RuntimeException("no data dir found.");
	}
}
