package org.cellang.console.view.table;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.metrics.ReportConfig;

public class ReportTableDataProvider extends AbstractTableDataProvider<EntityObject> {

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

	List<? extends EntityObject> list;
	ReportConfig rptCfg;
	EntitySessionFactory esf;
	List<Method> itemGetMethodList;
	Date reportDate;
	String corpId;

	public ReportTableDataProvider(ReportConfig rptCfg, EntitySessionFactory es, Date date, String corpId) {
		this.rptCfg = rptCfg;
		this.esf = es;
		this.reportDate = date;
		this.corpId = corpId;
		itemGetMethodList = this.rptCfg.getItemEntityConfig().getGetMethodList();

		this.columnList.add(new LineNumberColumn<EntityObject>(this));
		for (Method m : itemGetMethodList) {
			AbstractColumn<EntityObject> col = new GetterMethodColumn(m, this);
			this.columnList.add(col);
		}
		this.query();
	}

	private void query() {
		GetReportOp<BalanceSheetReportEntity> getRpt = new GetReportOp<>();
		BalanceSheetReportEntity bsr = getRpt.set(BalanceSheetReportEntity.class, corpId, reportDate).execute(this.esf);
		if (bsr == null) {
			return;
		}
		String reportId = bsr.getId();
		List<? extends EntityObject> el = new EntityQuery<>(this.rptCfg.getItemEntityConfig(),
				new String[] { "reportId" }, new Object[] { reportId }).offset(0).execute(this.esf);

		this.list = el;
		this.fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		if (this.list == null) {
			return 0;
		}
		return this.list.size();
	}

	@Override
	public EntityObject getRowObject(int idx) {
		if (idx < 0 || this.list == null || idx > this.list.size() - 1) {
			return null;
		}
		return this.list.get(idx);
	}

	@Override
	public int getRowNumber(int rowIndex) {
		return rowIndex;
	}

}
