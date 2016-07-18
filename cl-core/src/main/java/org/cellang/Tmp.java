package org.cellang;

import java.io.File;

import org.cellang.collector.EnvUtil;

public class Tmp {

	public static void main(String[] args) {
		File dd = EnvUtil.getDataDir();
		System.out.println(dd.getAbsolutePath());
	}
}
