package org.cellang.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.collector.EnvUtil;
import org.cellang.core.converter.DateStringConverter;
import org.cellang.core.entity.Converter;
import org.cellang.core.entity.CorpMetricEntity;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.EntityCsvWriter;
import org.cellang.core.entity.EntityService;
import org.cellang.core.loader.DataLoader;
import org.cellang.core.metrics.CorpMetricService;
import org.cellang.core.util.FileUtil;

public class Main {
	private static Map<Class, Converter> converterMap = new HashMap<Class, Converter>();

	static {
		converterMap.put(Date.class, new DateStringConverter("yyyy/MM/dd"));
	}

	public static void main(String[] args) throws IOException {
		clojure.main.main(args);		
	}

	public static void mainx(String[] args) throws IOException {
		File dataHome = EnvUtil.getDataDir();
		File dbHome = FileUtil.newFile(dataHome, new String[] { "db" });

		EntityConfigFactory ecf = new EntityConfigFactory();
		EntityService es = EntityService.newInstance(dbHome, "h2", ecf);
		if (es.isNew()) {
			System.out.println("db is empty, load data...");//
			DataLoader dl = new DataLoader(es);
			// File dfile = new File("src" + File.separator + "main" +
			// File.separator + "doc");
			File dfile = FileUtil.newFile(dataHome, new String[] { "163pp" });
			if (!dfile.exists()) {
				dfile.mkdirs();
			}
			// File dfile = new File("target"+File.separator+"163tmp");
			File idxdir = new File("src" + File.separator + "main" + File.separator + "doc" + File.separator + "1");
			File qfile = FileUtil.newFile(dataHome, new String[] { "sinapp", });
			if (!qfile.exists()) {
				qfile.mkdirs();
			}
			dl.loadDir(idxdir);
			// dl.loadDir(dfile);
			dl.loadDir(qfile);
		}

		CorpMetricService ms = new CorpMetricService(es);
		String[] keys = new String[] { "负债权益比", "QUOTES" };// , "EPS" };
		ms.updateAllMetric();

		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			List<CorpMetricEntity> metricL = ms.getMetricList(keys[i]);
			File output = FileUtil.newFile(dataHome, new String[] { "" + key + ".csv" });
			Writer fw = new OutputStreamWriter(new FileOutputStream(output));
			EntityCsvWriter cw = new EntityCsvWriter(fw, ecf.get(CorpMetricEntity.class), converterMap);
			cw.write(metricL);//
			cw.close();
			System.out.println("write to file:" + output);
		}
		es.close();
	}
}
