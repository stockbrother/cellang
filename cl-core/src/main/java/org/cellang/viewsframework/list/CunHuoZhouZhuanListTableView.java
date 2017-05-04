package org.cellang.viewsframework.list;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityQuery;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.customized.InventoryTurnDaysCustomizedReportItemDefine;
import org.cellang.viewsframework.list.CunHuoZhouZhuanListTableView.ReportRow;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.table.AbstractColumn;
import org.cellang.viewsframework.table.AbstractTableDataProvider;
import org.cellang.viewsframework.table.ColumnDefine;
import org.cellang.viewsframework.table.LineNumberColumn;
import org.cellang.viewsframework.table.TableDataView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying report item for specified report date and corp.
 * 
 * @author wu
 *
 */
public class CunHuoZhouZhuanListTableView extends TableDataView<ReportRow> implements HasActions {
	public static class CorpIdColumn extends AbstractColumn<ReportRow> {

		public CorpIdColumn(AbstractTableDataProvider<ReportRow> model) {
			super(model, "CorpId");
		}

		@Override
		public Object getValue(int rowIdx, ReportRow rowObj) {

			return rowObj.getCorpId();
		}

	}

	public static class CorpNameColumn extends AbstractColumn<ReportRow> {

		public CorpNameColumn(AbstractTableDataProvider<ReportRow> model) {
			super(model, "CorpId");
		}

		@Override
		public Object getValue(int rowIdx, ReportRow rowObj) {
			return rowObj.getCorpName();
		}
	}

	static class DaysColumn extends AbstractColumn<ReportRow> {

		public DaysColumn(AbstractTableDataProvider<ReportRow> model) {
			super(model, "Days");
		}

		@Override
		public Object getValue(int rowIdx, ReportRow rowObj) {
			return rowObj.getDays();
		}
	}

	static class ReportRow {

		CorpInfoEntity corpInfo;
		OperationContext oc;
		InventoryTurnDaysCustomizedReportItemDefine riDefine;

		String getCorpId() {
			return this.corpInfo.getId();
		}

		String getCorpName() {
			return this.corpInfo.getName();
		}

		BigDecimal getDays() {
			return this.riDefine.getValue(this.getCorpId(), 2015);//
		}
	}

	static class DataProvider extends AbstractTableDataProvider<ReportRow> {
		OperationContext oc;
		List<ReportRow> list = null;
		InventoryTurnDaysCustomizedReportItemDefine riDefine;

		public DataProvider(OperationContext oc) {
			this.oc = oc;
			this.columnList.add(new LineNumberColumn<ReportRow>(this));
			this.columnList.add(new CorpIdColumn(this));
			this.columnList.add(new CorpNameColumn(this));
			this.columnList.add(new DaysColumn(this));
			
			riDefine = new InventoryTurnDaysCustomizedReportItemDefine();
			riDefine.install(oc.getEntityService());

			this.refresh();
		}

		private void refresh() {
			EntityConfig cfg = oc.getEntityConfigFactory().get(CorpInfoEntity.class);

			List<CorpInfoEntity> el = new EntityQuery<CorpInfoEntity>(cfg).orderBy("code")
					.execute(oc.getEntityService());

			this.list = new ArrayList<ReportRow>(el.size());
			for (CorpInfoEntity e : el) {
				ReportRow r = new ReportRow();
				r.oc = oc;
				r.riDefine = riDefine;
				r.corpInfo = e;
				this.list.add(r);
			}
			this.fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return this.list.size();
		}

		@Override
		public ReportRow getRowObject(int idx) {
			return this.list.get(idx);
		}

	}

	static final Logger LOG = LoggerFactory.getLogger(CunHuoZhouZhuanListTableView.class);

	OperationContext oc;

	public CunHuoZhouZhuanListTableView(OperationContext oc) {
		super("List of?", oc, new DataProvider(oc));
		this.oc = oc;
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		return al;
	}

	@Override
	protected void onColumnSelected(Integer col, ColumnDefine<ReportRow> colDef) {
		super.onColumnSelected(col, colDef);
	}

	@Override
	protected void onRowSelected(Integer row, ReportRow rowObj) {
		super.onRowSelected(row, rowObj);
	}

}
