package org.cellang.viewsframework.view.report;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.collector.EnvUtil;
import org.cellang.commons.lang.Visitor;
import org.cellang.core.entity.AbstractReportEntity;
import org.cellang.core.entity.AbstractReportItemEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.metrics.ReportConfig;
import org.cellang.viewsframework.format.ReportItemLocator;
import org.cellang.viewsframework.format.ReportItemLocators;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.table.AbstractColumn;
import org.cellang.viewsframework.view.table.AbstractTableDataProvider;
import org.cellang.viewsframework.view.table.LineNumberColumn;

public class ReportTableDataProvider<T extends AbstractReportEntity> extends AbstractTableDataProvider<ReportRow> {

	public static class GetReportOp<T> extends EntityOp<T> {
		Class<T> rptEntityCls;
		String corpId;
		Date reportDate;

		GetReportOp<T> set(Class<T> rptEntityCls, String corpId, Date reportDate) {
			this.corpId = corpId;
			this.rptEntityCls = rptEntityCls;
			this.reportDate = reportDate;
			return this;
		}

		@Override
		public T execute(EntitySession es) {
			T rt = es.getSingle(rptEntityCls, new String[] { "corpId", "reportDate" },
					new Object[] { corpId, reportDate });

			return rt;
		}

	}

	static ReportItemLocators RIL = ReportItemLocators.getInstance();

	List<ReportRow> backList;
	ReportConfig rptCfg;
	EntitySessionFactory esf;
	List<Method> itemGetMethodList;
	String corpId;

	private int years;

	ReportItemLocatorFilter filter = new ReportItemLocatorFilter();
	ReportItemLocators.Group template;
	Class<T> reportEntityClass;

	public ReportTableDataProvider(OperationContext oc, Class<T> cls, int years, String corpId) {
		this.reportEntityClass = cls;
		this.template =	ReportItemLocators.getInstance().get(cls);
		this.years = years;
		this.rptCfg = oc.getReportConfigFactory().get(cls);
		this.esf = oc.getEntityService();
		this.corpId = corpId;
		itemGetMethodList = this.rptCfg.getItemEntityConfig().getGetMethodList();

		this.columnList.add(new LineNumberColumn<ReportRow>(this));
		this.columnList.add(new ReportRowKeyColumn(template, this, filter));

		for (int i = 0; i < years; i++) {
			this.columnList.add(new ReportRowValueColumn(i, "Y" + (2015 - i), this));
		}
		this.refresh();
	}

	/**
	 * Template
	 * 
	 * @return
	 */
	private List<ReportRow> newReportRowListFromLocaltors() {
		List<ReportRow> rL = new ArrayList<>();
		List<ReportItemLocator> locL = new ArrayList<>();

		template.getRoot().forEach(new Visitor<ReportItemLocator>() {

			@Override
			public void visit(ReportItemLocator t) {
				if (filter.accept(t)) {
					locL.add(t);
				}
			}
		}, false);

		for (ReportItemLocator ri : locL) {
			ReportRow rr = new ReportRow(years, ri.getKey(), ri);
			rL.add(rr);
		}
		return rL;
	}

	private void refresh() {

		List<List<? extends AbstractReportItemEntity>> ll = this.query();

		// create a sorted empty ReportRow.
		List<ReportRow> rL = this.newReportRowListFromLocaltors();
		// cache each Row by key.
		Map<String, ReportRow> rowMap = new HashMap<>();
		for (ReportRow rr : rL) {
			rowMap.put(rr.getKey(), rr);
		}

		//
		for (int i = 0; i < years; i++) {
			List<? extends AbstractReportItemEntity> l = ll.get(i);

			for (AbstractReportItemEntity eo : l) {
				String key = eo.getKey();
				ReportRow rr = rowMap.get(key);
				if (rr == null) {
					// ignore this value,it cannot found from the template.
					continue;
				}

				rr.set(i, eo);
			}
		}
		/*
		 * 
		 * <code> ReportRow[] oA = new ReportRow[] {}; Arrays.sort(oA, new
		 * Comparator<ReportRow>() {
		 * 
		 * @Override public int compare(ReportRow o1, ReportRow o2) {
		 * 
		 * ReportItemLocator l1 = o1.locator; ReportItemLocator l2 = o2.locator;
		 * int od1 = l1 == null ? Integer.MAX_VALUE / 2 : l1.getOrder(); int od2
		 * = l2 == null ? Integer.MAX_VALUE / 2 : l2.getOrder();
		 * 
		 * return od1 - od2; } });
		 * 
		 * this.list = Arrays.asList(oA); </code>
		 */
		this.backList = rL;
		AbstractColumn<ReportRow> ac = this.columnList.get(this.columnList.size() - 1);

		this.fireTableDataChanged();
	}

	/**
	 * Query year by year.
	 * 
	 * @return
	 */
	private List<List<? extends AbstractReportItemEntity>> query() {
		List<List<? extends AbstractReportItemEntity>> rt = new ArrayList<>();
		int year = 2015;

		for (int i = 0; i < years; i++) {
			List<? extends AbstractReportItemEntity> lI = this.query(year);
			year--;
			rt.add(lI);
		}

		return rt;
	}

	private List<? extends AbstractReportItemEntity> query(int year) {
		Date date = EnvUtil.newDateOfYearLastDay(year);
		GetReportOp<T> getRpt = new GetReportOp<>();
		T bsr = getRpt.set(this.reportEntityClass, corpId, date).execute(this.esf);
		if (bsr == null) {
			return new ArrayList<>();
		}
		String reportId = bsr.getId();
		List<? extends EntityObject> el = new EntityQuery<>(this.rptCfg.getItemEntityConfig(),
				new String[] { "reportId" }, new Object[] { reportId }).offset(0).execute(this.esf);

		return (List<? extends AbstractReportItemEntity>) el;
	}

	@Override
	public int getRowCount() {
		if (this.backList == null) {
			return 0;
		}
		return this.backList.size();
	}

	@Override
	public ReportRow getRowObject(int idx) {
		if (idx < 0 || this.backList == null || idx > this.backList.size() - 1) {
			return null;
		}
		return this.backList.get(idx);
	}

	@Override
	public int getRowNumber(int rowIndex) {
		return rowIndex;
	}

	public void changeCollapse(String key) {
		this.filter.changeCollapse(key);
		this.refresh();
	}

}
