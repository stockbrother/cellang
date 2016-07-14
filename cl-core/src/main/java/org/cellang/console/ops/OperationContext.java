package org.cellang.console.ops;

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

import org.cellang.collector.NeteasePreprocessor;
import org.cellang.collector.SinaAllQuotesPreprocessor;
import org.cellang.console.view.EntityConfigTableView;
import org.cellang.console.view.EntityObjectTableView;
import org.cellang.console.view.ViewsPane;
import org.cellang.core.converter.DateStringConverter;
import org.cellang.core.entity.Converter;
import org.cellang.core.entity.CorpMetricEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.EntityCsvWriter;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.EntitySessionFactoryImpl;
import org.cellang.core.loader.DataLoader;
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

	private int queryLimit = 1000;

	static {
		converterMap.put(Date.class, new DateStringConverter("yyyy/MM/dd"));
	}

	private EntitySessionFactory entityService;

	private File dataHome;

	private EntityConfigFactory entityConfigFactory;

	private boolean started;
	private String[] matrics = new String[] { "负债权益比", "QUOTES" };
	ViewsPane views;
	DataLoader loader;

	private EntityConfig selectedEntityConfig;

	public EntityConfig getSelectedEntityConfig() {
		return selectedEntityConfig;
	}

	public void setSelectedEntityConfig(EntityConfig selectedEntityConfig) {
		this.selectedEntityConfig = selectedEntityConfig;
	}

	public ViewsPane getViewManager() {
		return views;
	}

	public OperationContext(File dataDir, ViewsPane views) {
		this.dataHome = dataDir;
		this.views = views;
	}

	public void addListener(Listener l) {
		this.listenerList.add(l);
	}

	public void start() throws IOException {
		if (this.started) {
			throw new RuntimeException("started already.");
		}
		entityConfigFactory = new EntityConfigFactory();

		File dbHome = FileUtil.newFile(dataHome, new String[] { "db" });
		entityService = EntitySessionFactoryImpl.newInstance(dbHome, "h2", entityConfigFactory);
		loader = new DataLoader(entityService);
		if (entityService.isNew()) {
			LOG.info("db is empty, load data...");//
			File idxdir = new File("src" + File.separator + "main" + File.separator + "doc" + File.separator + "1");
			loader.loadDir(idxdir);
		}

		this.started = true;
		for (Listener l : this.listenerList) {
			l.onStarted(this);
		}
	}

	/**
	 * Wash data from external to internal format.
	 * 
	 * @param source
	 */
	public void wash(String source) {
		new WashOp().set(this.dataHome, source).execute(this);
	}

	public void chart(String corpId, String itemKey) {
		
	}

	public void chart(int xColNumber, int yColNumber) {
		new ChartOp(this, xColNumber, yColNumber).execute(this);
	}

	/**
	 * Load data into DB from folder.
	 * 
	 * @param folder
	 */
	public void load(String folder) {
		File qfile = FileUtil.newFile(dataHome, new String[] { folder });
		if (!qfile.exists()) {
			LOG.error("folder not exists:" + qfile.getAbsolutePath());//
			return;
		}
		loader.loadDir(qfile);
	}

	public void reset() {
		this.clear();
		this.load("163pp");
	}

	public void clear() {
		views.clear();
		EntityOp<Void> op = new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				es.clear();
				return null;
			}
		};
		this.entityService.execute(op);
	}

	public void home() {

		EntityConfigTableView table = new EntityConfigTableView(this,
				this.getEntityConfigFactory().getEntityConfigList());
		views.addView(table, true);
	}

	/**
	 * Query entity data from DB by limit and offset. Open a view for the
	 * result.
	 */
	public void list() {
		if (this.selectedEntityConfig == null) {
			return;
		}

		EntityObjectTableView v = new EntityObjectTableView(this.selectedEntityConfig, this.entityService,
				this.queryLimit);
		this.views.addView(v, true);
	}

	/**
	 * Close the current view.
	 */
	public void closeView() {
		this.views.closeCurrentView();
	}

	public void update(int idx) throws IOException {
		String matric = this.matrics[idx];
		this.update(matric);
	}

	public void update(String metric) throws IOException {
		// new CorpMetricService(entityService);
		// ms.updateMetric(metric);//
		//
	}

	public void print(int idx) throws IOException {
		print(this.matrics[idx]);
	}

	public void print(String metric) throws IOException {

		List<CorpMetricEntity> metricL = null;// ms.getMetricList(metric);
		File output = FileUtil.newFile(dataHome, new String[] { "" + metric + ".csv" });
		Writer fw = new OutputStreamWriter(new FileOutputStream(output));
		EntityCsvWriter cw = new EntityCsvWriter(fw, entityConfigFactory.get(CorpMetricEntity.class), converterMap);
		cw.write(metricL);//
		cw.close();
		LOG.info("write to file:" + output);
	}

	public EntityConfigFactory getEntityConfigFactory() {
		return entityConfigFactory;
	}

	public void close() {
		entityService.close();
		LOG.info("closed");
	}

	public File getDataHome() {
		return this.dataHome;
	}

	public EntitySessionFactory getEntityService() {
		return entityService;
	}

}
