package org.cellang.console.ops;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.cellang.console.control.entity.EntityConfigManager;
import org.cellang.console.view.View;
import org.cellang.console.view.ViewsPane;
import org.cellang.console.view.helper.HelpersPane;
import org.cellang.console.view.table.EntityConfigTableView;
import org.cellang.console.view.table.EntityObjectTableView;
import org.cellang.core.converter.DateStringConverter;
import org.cellang.core.entity.Converter;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.CorpMetricEntity;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.EntityCsvWriter;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.EntitySessionFactoryImpl;
import org.cellang.core.metrics.ReportConfigFactory;
import org.cellang.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationContext {
	public static interface Listener {
		public void onStarted(OperationContext oc);
	}

	private static final Logger LOG = LoggerFactory.getLogger(OperationContext.class);

	private static Map<Class, Converter> converterMap = new HashMap<Class, Converter>();

	static {
		converterMap.put(Date.class, new DateStringConverter("yyyy/MM/dd"));
	}

	private List<Listener> listenerList = new ArrayList<Listener>();

	private EntityConfigManager entityConfigManager;

	private EntitySessionFactory entityService;

	private File dataHome;

	private EntityConfigFactory entityConfigFactory;

	private boolean started;
	private String[] matrics = new String[] { "负债权益比", "QUOTES" };
	ViewsPane views;
	OpExecutor opExecutor = new OpExecutor();
	private ReportConfigFactory reportConfigFactory;
	
	public OperationContext() {

	}

	public <T> Future<T> execute(ConsoleOp<T> op) {
		return this.opExecutor.execute(op, this);
	}

	public ViewsPane getViewManager() {
		return views;
	}

	public OperationContext(File dataDir, ViewsPane views, HelpersPane helpers) {
		this.dataHome = dataDir;
		this.views = views;
		entityConfigFactory = new EntityConfigFactory();
		this.reportConfigFactory = new ReportConfigFactory(entityConfigFactory);

		File dbHome = FileUtil.newFile(dataHome, new String[] { "db" });
		entityService = EntitySessionFactoryImpl.newInstance(dbHome, "h2", entityConfigFactory);
		this.entityConfigManager = new EntityConfigManager(this, this.entityService, helpers);
	}

	public void addListener(Listener l) {
		this.listenerList.add(l);
	}

	public void start() throws IOException {
		if (this.started) {
			throw new RuntimeException("started already.");
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
		this.execute(new WashOp(source));
	}

	public void chart(String[] corpIdArray, String itemKey, int pageSize) {
		this.execute(new ChartOp2(this).set(corpIdArray, itemKey, pageSize));
	}

	public void chart(int xColNumber, int yColNumber) {
		this.execute(new ChartOp(this, xColNumber, yColNumber));
	}

	/**
	 * Load data into DB from folder.
	 * 
	 * @param folder
	 */
	public void load(String folder) {
		this.execute(new LoadOp(folder));
	}

	public void reset() {
		this.execute(new ResetOp());
	}

	public void clear() {
		this.execute(new ClearOp());
	}

	public void home() {

		EntityConfigTableView table = new EntityConfigTableView(this,
				this.getEntityConfigFactory().getEntityConfigList());
		views.addView(table, true);
		
		View view = this.getEntityConfigManager().newEntityListView(CorpInfoEntity.class);
		this.getViewManager().addView(view, true);//
		
		//ExtendingPropertyMasterTableView table2 = new ExtendingPropertyMasterTableView(this);
		//views.addView(table2, true);
		
	}

	/**
	 * Close the current view.
	 */
	public void closeView() {
		this.views.closeCurrentView();
	}

	public void sql(String sql) {

		StringBuffer sb = new StringBuffer();
		this.entityService.execute(new EntityOp<StringBuffer>() {

			@Override
			public StringBuffer execute(EntitySession es) {
				List<Object[]> rst = es.getDataAccessTemplate().executeQuery(es.getConnection(), sql);
				int i = 0;
				for (Object[] row : rst) {
					sb.append(++i);
					sb.append("\t");
					sb.append(Arrays.asList(row));
					sb.append("\n");
				}

				return sb;
			}
		});

		LOG.info(sb.toString());

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

	public EntityConfigManager getEntityConfigManager() {
		return entityConfigManager;
	}

	public ReportConfigFactory getReportConfigFactory() {
		return reportConfigFactory;
	}

}
