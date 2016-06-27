package org.cellang.core;

import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.converter.DateStringConverter;
import org.cellang.core.entity.Converter;
import org.cellang.core.entity.CorpMetricEntity;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.EntityCsvWriter;
import org.cellang.core.entity.EntityService;
import org.cellang.core.loader.DataLoader;
import org.cellang.core.util.FileUtil;

public class Main {
	private static Map<Class, Converter> converterMap = new HashMap<Class, Converter>();

	static {
		converterMap.put(Date.class, new DateStringConverter("yyyy/MM/dd"));
	}

	public static void main(String[] args) {
		File dbHome = FileUtil.createTempDir("main");
		EntityConfigFactory ecf = new EntityConfigFactory();
		EntityService es = EntityService.newInstance(dbHome, "h2", ecf);

		DataLoader dl = new DataLoader(es);
		File dfile = new File("src" + File.separator + "main" + File.separator + "doc");
		dl.loadDir(dfile);

		CorpMetricService ms = new CorpMetricService(es);
		String key = "负债权益比";
		ms.updateMetric(key);
		List<CorpMetricEntity> metricL = ms.getMetricList(key);

		StringWriter sw = new StringWriter();

		EntityCsvWriter cw = new EntityCsvWriter(sw, ecf.get(CorpMetricEntity.class), converterMap);
		cw.write(metricL);//
		System.out.println(sw.toString());

	}
}
