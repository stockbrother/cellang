package org.cellang.core;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.jdbc.ResultSetProcessor;
import org.cellang.commons.lang.Tuple3;
import org.cellang.core.entity.BalanceItemEntity;
import org.cellang.core.entity.BalanceSheetEntity;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityService;

public class CorpMetricService {
	private Map<String, MetricCalculator> metricDefineMap = new HashMap<String, MetricCalculator>();

	private EntityService entityService;

	public CorpMetricService(EntityService es) {
		this.entityService = es;
		this.add(new FuzhaiQuanyiBiMetricCalculator());
	}

	public void add(MetricCalculator mc) {
		this.metricDefineMap.put(mc.getKey(), mc);
	}

	public List<CorpInfoEntity> getCorpInfoList() {
		return this.entityService.getList(CorpInfoEntity.class);
	}

	public Double getMetric(String corpId, Date date, String key) {
		MetricCalculator md = this.metricDefineMap.get(key);
		Double rt = md.calculate(this, corpId, date);
		return rt;
	}

	public Double getBlanceSheetItem(String corpId, Date date, String key) {
		String sql = "select bi.value from " + BalanceItemEntity.tableName + " bi," + BalanceSheetEntity.tableName
				+ " bs where bi.balanceSheetId = bs.id and bs.corpId=? and bs.reportDate=? and bi.key = ?";
		return (Double) this.entityService.getPool().executeQuery(sql, new Object[] { corpId, date, key },
				new ResultSetProcessor() {

					@Override
					public Object process(ResultSet rs) throws SQLException {
						if (rs.next()) {
							BigDecimal v = rs.getBigDecimal("value");
							return v.doubleValue();
						}
						return null;
					}
				});

	}

	public List<Tuple3<String, Date, Double>> getMetricList(String key) {

		List<Tuple3<String, Date, Double>> rt = new ArrayList<Tuple3<String, Date, Double>>();
		MetricCalculator mc = this.metricDefineMap.get(key);
		List<CorpInfoEntity> corpL = this.getCorpInfoList();
		int year = 2015 - 1900;
		for (int i = 0; i < 10; i++) {
			Date date = new Date(year, 11, 31);
			for (CorpInfoEntity c : corpL) {
				String corpId = c.getCode();
				Double value = this.getMetric(c.getCode(), date, key);
				rt.add(new Tuple3<String, Date, Double>(corpId, date, value));
			}
		}

		return rt;
	}
}
