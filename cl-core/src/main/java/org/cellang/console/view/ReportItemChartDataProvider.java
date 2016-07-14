package org.cellang.console.view;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.jdbc.ObjectArrayListResultSetProcessor;
import org.cellang.console.chart.ChartModel;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;

public class ReportItemChartDataProvider extends AbstractChartDataProvider {
	EntitySessionFactory esf;
	EntityConfig reportCfg;
	EntityConfig itemCfg;
	private static final long ONEDAY = 3600 * 1000 * 24;

	ReportItemChartModel model;
	long startDate;
	String itemKey;
	String corpId;

	public ReportItemChartDataProvider(int pageSize, EntitySessionFactory esf, String corpId, String itemKey,
			EntityConfig reportCfg, EntityConfig itemCfg, long startDate) {
		super(pageSize);
		this.corpId = corpId;
		this.itemKey = itemKey;
		this.model = new ReportItemChartModel(pageSize);
		this.startDate = startDate;
		this.esf = esf;
		this.reportCfg = reportCfg;
		this.itemCfg = itemCfg;
	}

	protected void query() {
		int offset = this.pageNumber * this.pageSize;

		Calendar dateLarge = Calendar.getInstance();
		dateLarge.setTimeInMillis(this.startDate);
		dateLarge.add(Calendar.YEAR, -offset);//

		Calendar dateSmall = Calendar.getInstance();
		dateSmall.setTimeInMillis(dateLarge.getTimeInMillis());
		dateSmall.add(Calendar.YEAR, -this.pageSize);

		StringBuffer sql = new StringBuffer().append("select rpt.reportDate, itm.value from ")
				.append(itemCfg.getTableName()).append(" itm,").append(reportCfg.getTableName()).append(" rpt")//
				.append(" where rpt.id = itm.reportId")//
				.append(" and rpt.corpId = ?")//
				.append(" and rpt.reportDate <= ?")//
				.append(" and rpt.reportDate >= ?")//
				.append(" and itm.key = ?")//
				;
		Object[] args = new Object[] { this.corpId, new Date(dateLarge.getTimeInMillis()),
				new Date(dateSmall.getTimeInMillis()), itemKey };
		JdbcOperation<List<Object[]>> op = new JdbcOperation<List<Object[]>>() {

			@Override
			public List<Object[]> execute(Connection con) {

				return this.template.executeQuery(con, sql.toString(), args, new ObjectArrayListResultSetProcessor());

			}
		};

		List<Object[]> list = this.esf.execute(op);
		List<Long> dateL = new ArrayList<Long>(list.size());
		List<BigDecimal> valueL = new ArrayList<BigDecimal>(list.size());

		for (Object[] row : list) {
			Date reportDate = (Date) row[0];
			BigDecimal value = (BigDecimal) row[1];
			dateL.add(reportDate.getTime());
			valueL.add(value);
		}

		this.model.setSerial(dateL, valueL);
		this.view.updateUI();
	}

	@Override
	public void nextPage() {
		super.nextPage();
	}

	@Override
	public ChartModel getModel() {
		return this.model;
	}

}
