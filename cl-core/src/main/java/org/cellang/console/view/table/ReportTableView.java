package org.cellang.console.view.table;

import java.util.Date;
import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.metrics.ReportConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying entity list result.
 * 
 * @author wu
 *
 */
public class ReportTableView extends TableDataView<EntityObject>implements HasActions {

	static final Logger LOG = LoggerFactory.getLogger(ReportTableView.class);

	public ReportTableView(ReportConfig rptCfg, EntitySessionFactory es, Date date, String corpId) {
		super("Report of " + rptCfg.getReportEntityConfig().getTableName(),
				new ReportTableDataProvider(rptCfg, es, date, corpId));
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		return al;
	}

}
