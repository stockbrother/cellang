package org.cellang.console.view;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.jdbc.ObjectArrayListResultSetProcessor;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;

public class ReportItemChartDataProvider extends AbstractChartDataProvider<Long> {
	EntitySessionFactory esf;
	EntityConfig reportCfg;
	EntityConfig itemCfg;
	long startDate;
	String itemKey;
	String[] corpIdArray;

	public ReportItemChartDataProvider(int pageSize, EntitySessionFactory esf, String[] corpIdArray, String itemKey,
			EntityConfig reportCfg, EntityConfig itemCfg, long startDate) {
		super(new ReportItemChartModel(), pageSize);
		this.corpIdArray = corpIdArray;
		this.itemKey = itemKey;
		for (String corpId : corpIdArray) {
			ReportItemChartSerial ser = new ReportItemChartSerial(corpId, pageSize);
			ser.setPreferedMin(BigDecimal.ZERO);//
			this.model.addSerail(ser);//
		}

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

		StringBuffer sql = new StringBuffer().append("select rpt.corpId, rpt.reportDate, itm.value from ")
				.append(itemCfg.getTableName()).append(" itm,").append(reportCfg.getTableName()).append(" rpt")//
				.append(" where rpt.id = itm.reportId")//
				.append(" and rpt.corpId in(");//
		for (int i = 0; i < this.corpIdArray.length; i++) {
			sql.append("?");
			if (i < this.corpIdArray.length - 1) {
				sql.append(",");
			}
		}
		sql.append(")")//
				.append(" and rpt.reportDate <= ?")//
				.append(" and rpt.reportDate >= ?")//
				.append(" and itm.key = ?")//
				.append(" order by rpt.reportDate desc")//
				;
		List<Object> args = new ArrayList<>();

		args.addAll(Arrays.asList(this.corpIdArray));
		args.add(new Date(dateLarge.getTimeInMillis()));
		args.add(new Date(dateSmall.getTimeInMillis()));
		args.add(itemKey);
		JdbcOperation<List<Object[]>> op = new JdbcOperation<List<Object[]>>() {

			@Override
			public List<Object[]> execute(Connection con) {

				return this.template.executeQuery(con, sql.toString(), args, new ObjectArrayListResultSetProcessor());

			}
		};

		List<Object[]> list = this.esf.execute(op);
		this.model.clearPoints();
		for (Object[] row : list) {
			int col = 0;
			String corpId = (String) row[col++];
			ReportItemChartSerial ser = (ReportItemChartSerial) this.model.getSerial(corpId);			
			Date reportDate = (Date) row[col++];
			BigDecimal value = (BigDecimal) row[col++];
			ser.addPoint(reportDate.getTime(), value);

		}

		this.view.updateUI();
	}

	@Override
	public void nextPage() {
		super.nextPage();
	}

}