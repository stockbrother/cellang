package org.cellang.core.metrics;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.jdbc.ResultSetProcessor;
import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.CorpMetricEntity;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorpMetricService {
	private static final Logger LOG = LoggerFactory.getLogger(CorpMetricService.class);

	private Map<String, MetricCalculator> metricDefineMap = new HashMap<String, MetricCalculator>();

	private EntitySession entityService;

	private ReportConfigFactory reportConfigFactory;

	public CorpMetricService(EntitySession ef) {
		this.entityService = ef;
		this.reportConfigFactory = new ReportConfigFactory(ef.getEntityConfigFactory());
		this.add(new FuzhaiQuanyiBiMetricCalculator(this.reportConfigFactory));
		this.add(new EarningPerShareMetricCalculator(this.reportConfigFactory));
		this.add(new QuotesMetricCalculator(this.reportConfigFactory));//
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

	public Double getReportItem(ReportConfig rc, String corpId, Date date, String key) {

		String sql = "select itm.value from " + rc.getItemEntityConfig().getTableName() + " itm,"
				+ rc.getReportEntityConfig().getTableName()
				+ " rpt where itm.reportId = rpt.id and rpt.corpId=? and rpt.reportDate=? and itm.key = ?";
		JdbcOperation<Double> op = new JdbcOperation<Double>() {

			@Override
			public Double execute(Connection con) {
				return (Double) this.template.executeQuery(con, sql, new Object[] { corpId, date, key },
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
		};

		return this.entityService.execute(op);

	}

	public void updateAllMetric() {
		for (Map.Entry<String, MetricCalculator> en : this.metricDefineMap.entrySet()) {
			this.updateMetric(en.getKey());
		}

	}

	public void updateMetric(String key) {
		// delete by key.
		LOG.info("update metric:" + key + " ... start.");

		this.entityService.delete(CorpMetricEntity.class, new String[] { "key" }, new Object[] { key });
		MetricCalculator mc = this.metricDefineMap.get(key);

		List<CorpInfoEntity> corpL = this.getCorpInfoList();
		int year = 2015 - 1900;
		for (int i = 0; i < 10; i++) {
			Date date = new Date(year, 11, 31);
			year--;
			for (CorpInfoEntity c : corpL) {
				String corpId = c.getCode();
				Double value = mc.calculate(this, c.getCode(), date);
				if (value == null) {
					continue;
				}
				CorpMetricEntity me = new CorpMetricEntity();
				me.setId(UUIDUtil.randomStringUUID());//
				me.setCorpId(corpId);
				me.setReportDate(date);
				me.setKey(key);
				me.setValue(value);//
				this.entityService.save(me);//
			}
		}
		LOG.info("update metric:" + key + " ... done.");

	}

	public List<CorpMetricEntity> getMetricList(String key) {

		return this.entityService.query(CorpMetricEntity.class, "key", key)
				.orderBy(new String[] { "corpId,reportDate desc" }).execute(this.entityService);//
	}

	public EntitySession getEntityService() {
		return entityService;
	}
}
