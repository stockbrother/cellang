package org.cellang.console.view.table;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.cellang.console.format.ReportItemLocator;
import org.cellang.console.format.ReportItemLocators;
import org.cellang.core.entity.AbstractReportItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.metrics.ReportConfig;

public class ReportTableDataProvider extends AbstractTableDataProvider<EntityObject> {
	public static class KeyColumn extends GetterMethodColumn {

		public KeyColumn(Method m, AbstractTableDataProvider<EntityObject> model) {
			super(m, model);
		}

		@Override
		public Object getValue(int rowIndex, EntityObject ec) {
			Object rt = super.getValue(rowIndex, ec);
			if (this.name.equals("key")) {
				String key = (String) rt;
				ReportItemLocator ril = ReportTableDataProvider.RIL.get(key);
				int depth = 0;
				while (ril != null) {
					ril = ril.getParent();
					depth++;
					key = ">" + key;
				}

				return key;
			}

			return rt;
		}
	}

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

	static ReportItemLocators RIL = ReportItemLocators.load();

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
			AbstractColumn<EntityObject> col = new KeyColumn(m, this);
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

		EntityObject[] oA = el.toArray(new EntityObject[] {});
		Arrays.sort(oA, new Comparator<EntityObject>() {

			@Override
			public int compare(EntityObject o1, EntityObject o2) {
				AbstractReportItemEntity e1 = (AbstractReportItemEntity) o1;
				AbstractReportItemEntity e2 = (AbstractReportItemEntity) o2;

				ReportItemLocator l1 = RIL.get(e1.getKey());
				ReportItemLocator l2 = RIL.get(e2.getKey());
				int od1 = l1 == null ? Integer.MAX_VALUE / 2 : l1.getOrder();
				int od2 = l2 == null ? Integer.MAX_VALUE / 2 : l2.getOrder();

				return od1 - od2;
			}
		});

		this.list = Arrays.asList(oA);

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
