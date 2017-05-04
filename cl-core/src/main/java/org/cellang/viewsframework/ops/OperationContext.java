package org.cellang.viewsframework.ops;

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
import org.cellang.corpsviewer.MenuBar;
import org.cellang.corpsviewer.myfavorites.OpenMyFavoritesListViewAction;
import org.cellang.viewsframework.EventBus;
import org.cellang.viewsframework.HomeView;
import org.cellang.viewsframework.PerspectivePanel;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.control.entity.EntityConfigManager;
import org.cellang.viewsframework.view.helper.EntityObjectHelperPane;
import org.cellang.viewsframework.view.helper.ViewHelperPane;
import org.cellang.viewsframework.view.report.ReportTemplateRow;
import org.cellang.viewsframework.view.table.EntityConfigTableView;
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
	PerspectivePanel views;
	OpExecutor opExecutor = new OpExecutor();
	private ReportConfigFactory reportConfigFactory;

	private EventBus eventBus = new EventBus();

	private ReportTemplateRow selectedReportTemplateRow;
	private SelectedObjectContext selectedObjectContext = new SelectedObjectContext();
	private MenuBar menuBar;

	public SelectedObjectContext getSelectedObjectContext() {
		return this.selectedObjectContext;
	}

	public MenuBar getMenuBar() {
		return this.menuBar;
	}

	public EventBus getEventBus() {
		return this.eventBus;
	}

	public ReportTemplateRow getReportTemplateRow() {
		return this.selectedReportTemplateRow;
	}

	public ReportTemplateRow setReportTemplateRow(ReportTemplateRow rtr) {
		ReportTemplateRow rt = this.selectedReportTemplateRow;
		this.selectedReportTemplateRow = rtr;
		return rt;
	}

	public <T> Future<T> execute(ConsoleOp<T> op) {
		return this.opExecutor.execute(op, this);
	}

	public PerspectivePanel getViewManager() {
		return views;
	}

	public OperationContext(File dataDir) {
		this.dataHome = dataDir;
		entityConfigFactory = new EntityConfigFactory();
		this.reportConfigFactory = new ReportConfigFactory(entityConfigFactory);

		File dbHome = FileUtil.newFile(dataHome, new String[] { "db" });
		entityService = EntitySessionFactoryImpl.newInstance(dbHome, "h2", entityConfigFactory);
		this.entityConfigManager = new EntityConfigManager(this, this.entityService);
		this.menuBar = new MenuBar(this);
	}

	public void addComponent(PerspectivePanel views) {
		this.views = views;
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

		// EntityConfigTableView table = new EntityConfigTableView(this,
		// this.getEntityConfigFactory().getEntityConfigList());
		// views.addView(table, true);

		views.addView(0, new HomeView(this), true);
		this.getMenuBar().executeAction(OpenMyFavoritesListViewAction.class);

		// views.addView(1, new EntityObjectHelperPane(this), true);
		// views.addView(1, new ViewHelperPane(this), true);

		// ExtendingPropertyMasterTableView table2 = new
		// ExtendingPropertyMasterTableView(this);
		// views.addView(table2, true);

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
