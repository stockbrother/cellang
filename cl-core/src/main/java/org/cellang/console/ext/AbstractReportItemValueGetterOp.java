package org.cellang.console.ext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.cellang.collector.EnvUtil;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.util.DateUtil;

public class AbstractReportItemValueGetterOp extends EntityOp<BigDecimal> {

	public static final int AG_SUM = 1;
	public static final int AG_AVG = 0;

	String corpId;
	int yearFrom;
	int yearTo;
	String key;
	String sql;

	AbstractReportItemValueGetterOp(String tableNameReport, String tableNameItem, int agFun) {
		String agS = "sum";
		sql = "select count(itm.id)," + agS + "(itm.value) from " + tableNameItem + " itm," + tableNameReport
				+ " rpt where rpt.corpId=? and rpt.reportDate >= ? and rpt.reportDate <= ? and itm.reportId = rpt.id and itm.key=?"//
		;

	}

	AbstractReportItemValueGetterOp set(String corpId, int year, String key) {
		return set(corpId, year, year, key);
	}

	public AbstractReportItemValueGetterOp set(String corpId, int yearFrom, int yearTo, String key) {
		this.corpId = corpId;
		if (yearFrom > yearTo) {
			int tmp = yearTo;
			yearTo = yearFrom;
			yearFrom = tmp;
		}
		this.yearFrom = yearFrom;
		this.yearTo = yearTo;

		this.key = key;
		return this;
	}

	@Override
	public BigDecimal execute(EntitySession es) {
		int years = this.yearTo - this.yearFrom + 1;
		Date dateFrom = EnvUtil.newDateOfYearLastDay(this.yearFrom);
		Date dateTo = EnvUtil.newDateOfYearLastDay(this.yearTo);

		List<Object[]> oL = es.getDataAccessTemplate().executeQuery(es.getConnection(), sql,
				new Object[] { corpId, dateFrom, dateTo, key });

		BigDecimal bd = null;
		if (oL.size() == 1) {
			Object[] row = oL.get(0);
			long count = (Long) row[0];
			if (count == years) {
				bd = (BigDecimal) row[1];
			} else if (count < years) {
				bd = null;
			} else {
				bd = null;
				throw new RuntimeException("bug");
			}
		} else {
			throw new RuntimeException("too many result.");
		}

		return bd;
	}
}