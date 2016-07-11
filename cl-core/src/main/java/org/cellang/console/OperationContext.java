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
import org.cellang.collector.NeteasePreprocessor;
import org.cellang.collector.SinaAllQuotesPreprocessor;
import org.cellang.core.converter.DateStringConverter;
import org.cellang.core.entity.Converter;
import org.cellang.core.entity.CorpMetricEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.EntityCsvWriter;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityService;
import org.cellang.core.loader.DataLoader;
import org.cellang.core.metrics.CorpMetricService;
import org.cellang.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationContext {
	public static interface Listener {
		public void onStarted(OperationContext oc);
	}

	private static final Logger LOG = LoggerFactory.getLogger(OperationContext.class);

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
	DataLoader loader;

	private EntityConfig selectedEntityConfig;

	public EntityConfig getSelectedEntityConfig() {
		return selectedEntityConfig;
	}

	public void setSelectedEntityConfig(EntityConfig selectedEntityConfig) {
		this.selectedEntityConfig = selectedEntityConfig;
	}

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
		loader = new DataLoader(entityService);
		if (entityService.isNew()) {
			LOG.info("db is empty, load data...");//
			File idxdir = new File("src" + File.separator + "main" + File.separator + "doc" + File.separator + "1");
			loader.loadDir(idxdir);
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

	public void wash(String source) {

		File from = new File(this.dataHome, source);
		if (!from.exists()) {
			LOG.error("no data folder found:" + from);
		}

		File to = new File(this.dataHome, source + "pp");
		if ("163".equals(source)) {
			new NeteasePreprocessor(from, to).process();
		} else if ("sina".equals(source)) {
			new SinaAllQuotesPreprocessor(from, to).process();
		} else {
			LOG.error("no source found:" + source);
		}
	}

	public void load(String folder) {
		File qfile = FileUtil.newFile(dataHome, new String[] { folder });
		if (!qfile.exists()) {
			LOG.error("folder not exists:" + qfile.getAbsolutePath());//
			return;
		}
		loader.loadDir(qfile);
	}

	public void liste() {
		if (this.selectedEntityConfig == null) {
			return;
		}
		List<? extends EntityObject> el = this.entityService.query(this.selectedEntityConfig.getEntityClass())
				.executeQuery();
		EntityObjectTableView v = new EntityObjectTableView(this.selectedEntityConfig, el);
		this.viewManager.addView(v);
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
