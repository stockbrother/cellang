package org.cellang.corpsviewer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.lang.Visitor;
import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.corpsviewer.BalanceSheetQinSuanView.Row;
import org.cellang.corpsviewer.corpdata.ItemDefine;
import org.cellang.corpsviewer.corpdata.ItemDefines;
import org.cellang.corpsviewer.utils.EntityServiceUtil;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.report.ReportItemLocatorFilter;
import org.cellang.viewsframework.report.ReportRow;
import org.cellang.viewsframework.report.ReportRowKeyColumn;
import org.cellang.viewsframework.table.AbstractColumn;
import org.cellang.viewsframework.table.AbstractTableDataProvider;
import org.cellang.viewsframework.table.LineNumberColumn;
import org.cellang.viewsframework.table.TableDataView;

public class BalanceSheetQinSuanView extends TableDataView<Row> {
	public static class ReportRowKeyColumn extends AbstractColumn<Row> {

		ReportItemLocatorFilter filter;
		ItemDefines.Group template;
		public ReportRowKeyColumn(ItemDefines.Group template,AbstractTableDataProvider<Row> model, ReportItemLocatorFilter filter) {
			super(model, "Key");
			this.template = template;
			this.filter = filter;
		}

		@Override
		public Object getValue(int rowIndex, Row ec) {
			String key = ec.getKey();
			String rt = key;
			
			ItemDefine ril = template.get(key);

			int dep = 0;

			while (ril != null) {
				ril = ril.getParent();
				if (dep > 0) {
					rt = "  " + rt;
				}

				dep++;
			}
			return "" + rt;
		}
	}
	public static class RowValueColumn extends AbstractColumn<Row> {

		private int year;

		public RowValueColumn(int year, String name, DataProvider model) {
			super(model, name);
			this.year = year;
		}

		@Override
		public Object getValue(int rowIdx, Row rowObj) {
			return rowObj.getValue(year);
		}

		@Override
		public Class<?> getValueRenderingClass() {
			return BigDecimal.class;
		}
	}

	public static class Row {

		private Map<Integer, BalanceSheetItemEntity> itemMap = new HashMap<>();

		private String key;

		public Row(int years, ItemDefine def) {
			this.key = def.getKey();
		}
		
		public String getKey(){
			return key;
		}

		public void set(int year, BalanceSheetItemEntity eo) {
			itemMap.put(year, eo);
		}

		public Object getValue(int year) {
			BalanceSheetItemEntity item = itemMap.get(year);
			if (item == null) {
				return null;
			}
			return item.getValue();
		}

	}

	private static class DataProvider extends AbstractTableDataProvider<Row> {

		private String corpId;
		private int years;

		private OperationContext oc;
		private List<Row> rowList;
		ReportItemLocatorFilter filter = new ReportItemLocatorFilter();

		public DataProvider(OperationContext oc, int years, String corpId) {
			this.oc = oc;
			this.years = years;
			this.corpId = corpId;
			this.columnList.add(new LineNumberColumn<Row>(this));
			ItemDefines.Group group = ItemDefines.getInstance().get(BalanceSheetReportEntity.class);
			this.columnList.add(new ReportRowKeyColumn(group, this, filter));
			for (int i = 0; i < years; i++) {
				this.columnList.add(new RowValueColumn(i, "Y" + (2015 - i), this));
			}

			this.refresh();

		}

		private List<Row> newReportRowListFromLocaltors(ItemDefine root) {
			List<Row> rL = new ArrayList<>();
			List<ItemDefine> locL = new ArrayList<>();

			root.forEach(new Visitor<ItemDefine>() {

				@Override
				public void visit(ItemDefine t) {
					if (filter.accept(t)) {
						locL.add(t);
					}
				}
			}, false);

			for (ItemDefine ri : locL) {
				Row rr = new Row(years, ri);
				rL.add(rr);
			}
			return rL;
		}

		private void refresh() {
			ItemDefines.Group group = ItemDefines.getInstance().get(BalanceSheetReportEntity.class);
			ItemDefine idR = group.getRoot().find("资产总计");
			List<Row> rL = newReportRowListFromLocaltors(idR);

			Map<String, Row> rowMap = new HashMap<>();
			for (Row rr : rL) {
				rowMap.put(rr.key, rr);
			}

			List<List<BalanceSheetItemEntity>> ll = EntityServiceUtil.queryBalanceSheetItem(oc, corpId, 2015, years);

			List<ItemDefine> idL = new ArrayList<ItemDefine>();
			idR.addAllToList(idL, false);

			for (int i = 0; i < years; i++) {
				List<BalanceSheetItemEntity> colList = ll.get(i);
				for (BalanceSheetItemEntity cel : colList) {

					Row r = rowMap.get(cel.getKey());
					if (r == null) {
						continue;
					}
					r.set(i, cel);
				}

			}
			this.rowList = rL;
		}

		@Override
		public int getRowCount() {
			return this.rowList.size();
		}

		@Override
		public Row getRowObject(int idx) {
			return this.rowList.get(idx);
		}

	}

	public BalanceSheetQinSuanView(OperationContext oc, int years, String corpId) {
		super("Balance Sheet(Qing Suan)", oc, new DataProvider(oc, years, corpId));
	}

}
