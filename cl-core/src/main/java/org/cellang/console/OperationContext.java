package org.cellang.console;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
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

public class OperationContext {
	public static interface Listener {
		public void onStarted(OperationContext oc);
	}

	private List<Listener> listenerList = new ArrayList<Listener>();

	private static Map<Class, Converter> converterMap = new HashMap<Class, Converter>();

	static {
		converterMap.put(Date.class, new DateStringConverter("yyyy/MM/dd"));
	}

	private EntityService entityService;

	private File dataHome;

	private EntityConfigFactory entityConfigFactory;

	private CorpMetricService ms;
	private boolean started;
	private String[] matrics = new String[] { "负债权益比", "QUOTES" };
	ViewManager viewManager;

	public ViewManager getViewManager() {
		return viewManager;
	}

	public OperationContext() {
		viewManager = new ViewManager(this);
	}

	public void addListener(Listener l) {
		this.listenerList.add(l);
	}

	public void start() throws IOException {
		if (this.started) {
			throw new RuntimeException("started already.");
		}
		entityConfigFactory = new EntityConfigFactory();
		dataHome = EnvUtil.getDataDir();
		File dbHome = FileUtil.newFile(dataHome, new String[] { "db" });
		entityService = EntityService.newInstance(dbHome, "h2", entityConfigFactory);

		if (entityService.isNew()) {
			System.out.println("db is empty, load data...");//
			DataLoader dl = new DataLoader(entityService);
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

		ms = new CorpMetricService(entityService);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				OperationContext.this.close();
			}
		});
		this.started = true;
		for (Listener l : this.listenerList) {
			l.onStarted(this);
		}
	}

	public void list() {

		for (int i = 0; i < this.matrics.length; i++) {
			System.out.println(this.matrics[i]);
		}

	}

	public void update(int idx) throws IOException {
		String matric = this.matrics[idx];
		this.update(matric);
	}

	public void update(String metric) throws IOException {
		ms.updateMetric(metric);//
	}

	public void print(int idx) throws IOException {
		print(this.matrics[idx]);
	}

	public void print(String metric) throws IOException {
		List<CorpMetricEntity> metricL = ms.getMetricList(metric);
		File output = FileUtil.newFile(dataHome, new String[] { "" + metric + ".csv" });
		Writer fw = new OutputStreamWriter(new FileOutputStream(output));
		EntityCsvWriter cw = new EntityCsvWriter(fw, entityConfigFactory.get(CorpMetricEntity.class), converterMap);
		cw.write(metricL);//
		cw.close();
		System.out.println("write to file:" + output);
	}

	public EntityConfigFactory getEntityConfigFactory() {
		return entityConfigFactory;
	}

	public void close() {
		entityService.close();
		System.out.println("closed");
	}

}
