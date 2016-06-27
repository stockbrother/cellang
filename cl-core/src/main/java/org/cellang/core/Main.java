package org.cellang.core;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.cellang.commons.lang.Tuple3;
import org.cellang.core.entity.EntityService;
import org.cellang.core.util.FileUtil;

import au.com.bytecode.opencsv.CSVWriter;

public class Main {
	public static void main(String[] args) {
		File dbHome = FileUtil.createTempDir("main");

		EntityService es = EntityService.newInstance(dbHome, "h2");

		DataLoader dl = new DataLoader(es);
		File dfile = new File("src" + File.separator + "main" + File.separator + "doc");
		dl.loadDir(dfile);

		CorpMetricService ms = new CorpMetricService(es);
		List<Tuple3<String, Date, Double>> metricL = ms.getMetricList("负债权益比");

		StringWriter sw = new StringWriter();
		CSVWriter cw = new CSVWriter(sw);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		for (Tuple3<String, Date, Double> m : metricL) {
			String[] str = new String[] { m.a, df.format(m.b), m.c.toString() };
			cw.writeNext(str);
		}
		System.out.println(sw.toString());

	}
}
