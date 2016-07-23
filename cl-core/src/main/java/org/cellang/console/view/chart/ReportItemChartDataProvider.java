package org.cellang.console.view.chart;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.jdbc.ObjectArrayListResultSetProcessor;
import org.cellang.console.view.ReportItemChartSerial;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.util.ReportDate;

public class ReportItemChartDataProvider extends AbstractChartDataProvider<ReportDate> {
	EntitySessionFactory esf;
	EntityConfig reportCfg;
	EntityConfig itemCfg;
	ReportDate startDate;
	String itemKey;
	String[] corpIdArray;

	public ReportItemChartDataProvider(int pageSize, EntitySessionFactory esf, String[] corpIdArray, String itemKey,
			EntityConfig reportCfg, EntityConfig itemCfg, ReportDate startDate) {
		super(new ReportItemChartModel(), pageSize);
		this.corpIdArray = corpIdArray;
		this.itemKey = itemKey;
		for (String corpId : corpIdArray) {
			ReportItemChartSerial ser = new ReportItemChartSerial(corpId, ReportDate.valueOf(0), this.pageSize);
			ser.setPreferedMin(BigDecimal.ZERO);//
			this.model.addSerail(ser);//
		}

		this.startDate = startDate;
		this.esf = esf;
		this.reportCfg = reportCfg;
		this.itemCfg = itemCfg;
	}

	@Override
	protected void query() {
		int offset = this.pageNumber * this.pageSize;

		ReportDate dateLarge = this.startDate.add(-offset);//

		ReportDate dateSmall = dateLarge.add(-this.pageSize);

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
		// TODO Use reportDate type
		args.add(new Date(dateLarge.getTimeInMillis()));
		args.add(new Date(dateSmall.getTimeInMillis()));
		args.add(itemKey);
		JdbcOperation<List<Object[]>> op = new JdbcOperation<List<Object[]>>() {

			@Override
			public List<Object[]> doExecute(Connection con) {

				return this.template.executeQuery(con, sql.toString(), args, new ObjectArrayListResultSetProcessor());

			}
		};

		List<Object[]> list = this.esf.execute(op);

		this.model.moveWindowTo(dateLarge);//
		Calendar c = Calendar.getInstance();

		for (Object[] row : list) {
			int col = 0;
			String corpId = (String) row[col++];
			ReportItemChartSerial ser = (ReportItemChartSerial) this.model.getSerial(corpId);
			Date reportDate = (Date) row[col++];
			BigDecimal value = (BigDecimal) row[col++];
			c.setTime(reportDate);
			ser.addPoint(ReportDate.valueOf(c.get(Calendar.YEAR)), value);

		}

		this.view.updateUI();
	}

	@Override
	public void nextPage() {
		super.nextPage();
	}

	@Override
	public void getDescription(Map<String, Object> desMap) {
		super.getDescription(desMap);
		desMap.put("ItemKey", this.itemKey);
		desMap.put("CorpIdArray", this.corpIdArray);

	}

}