package org.cellang.console.view.report;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.console.view.table.TableDataView;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.metrics.ReportConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying report item for specified report date and corp.
 * 
 * @author wu
 *
 */
public class ReportTableView extends TableDataView<ReportRow> implements HasActions {

	static final Logger LOG = LoggerFactory.getLogger(ReportTableView.class);

	public ReportTableView(ReportConfig rptCfg, EntitySessionFactory es, int years, String corpId) {
		super("Report of " + rptCfg.getReportEntityConfig().getTableName(),
				new ReportTableDataProvider(rptCfg, es, years, corpId));
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		return al;
	}

}
