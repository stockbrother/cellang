package org.cellang.console.view.report;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cellang.collector.EnvUtil;
import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.jdbc.ResultSetProcessor;
import org.cellang.commons.lang.Tuple2;
import org.cellang.console.chart.ChartModel;
import org.cellang.console.chart.ChartSerial;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.chart.AbstractChartDataProvider;
import org.cellang.console.view.report.ReportTemplateRowChartDataProvider.Scope;
import org.cellang.core.metrics.ReportConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportTemplateRowChartDataProvider extends AbstractChartDataProvider<Scope> {
	private static final Logger LOG = LoggerFactory.getLogger(ReportTemplateRowChartDataProvider.class);

	MathContext MC2UP = new MathContext(2, RoundingMode.UP);

	public static class Scope {
		int idx;
		int total;

		public Scope(int idx, BigDecimal from, BigDecimal to) {
			this.idx = idx;
			this.from = from;
			this.to = to;
		}

		BigDecimal from;
		BigDecimal to;

		public boolean add(String a, BigDecimal b) {
			if (from.compareTo(b) <= 0 && to.compareTo(b) > 0) {
				this.total++;
				return true;
			}
			return false;
		}

	}

	public static class ReportRowChartSerial extends ChartSerial<Scope> {

		List<Tuple2<String, BigDecimal>> list;

		BigDecimal minValue;

		BigDecimal maxValue;

		BigDecimal step;

		List<Scope> target;

		public ReportRowChartSerial(String key, List<Tuple2<String, BigDecimal>> list) {
			super(key);
			this.list = list;
			this.target = new ArrayList<>();
			if (list.isEmpty()) {
				this.minValue = new BigDecimal(0);
				this.maxValue = new BigDecimal(1);

			} else {

				for (Tuple2<String, BigDecimal> tp : list) {
					if (minValue == null || tp.b.compareTo(minValue) < 0) {
						minValue = tp.b;
					}
					if (maxValue == null || tp.b.compareTo(maxValue) > 0) {
						maxValue = tp.b;
					}

				}
			}
			if (this.minValue.compareTo(this.maxValue) == 0) {
				this.step = new BigDecimal(1);
			}
			int SIZE = 20;
			int LEFT = 1;
			int RIGHT = 1;
			step = maxValue.subtract(minValue).divide(new BigDecimal(SIZE - LEFT - RIGHT), 2, RoundingMode.UP);

			// adjust step.
			int pow = String.valueOf(step.unscaledValue()).length() - step.scale();
			if (pow > 0) {
				step = new BigDecimal(10).pow(pow);//
			} else {
				step = new BigDecimal("0.1").pow(-pow);//
			}

			BigDecimal steps = this.minValue.divide(step, 0, RoundingMode.CEILING);

			BigDecimal from = step.multiply(steps.subtract(BigDecimal.ONE));

			if (from.compareTo(this.minValue) > 0) {
				throw new RuntimeException("bug.");
			}
			// adjust left/from.

			// counting.
			int j = 0;
			for (int i = 0;; i++) {

				BigDecimal to = from.add(this.step);
				Scope scope = new Scope(i, from, to);

				this.target.add(scope);
				while (j < list.size()) {
					Tuple2<String, BigDecimal> item = list.get(j);
					if (item.b == null) {
						j++;
					} else if (scope.add(item.a, item.b)) {
						LOG.debug("add corpId:" + item.a + ",value:" + item.b + " to scope:" + scope.from + "-"
								+ scope.to);

						j++;
					} else {
						break;
					}
				}
				if (j > list.size() - 1) {
					break;
				}
				from = to;
			}

		}

		@Override
		public BigDecimal getYValue(Scope xValue) {
			return new BigDecimal(this.target.get(xValue.idx).total);
		}

		@Override
		public int getWindowSize() {
			return this.target.size();
		}

		@Override
		public void moveWindowTo(Scope startXValue) {
			throw new RuntimeException("not supported.");
		}

		@Override
		public Scope getXValue(int idx) {
			return this.target.get(idx);
		}
	}

	OperationContext oc;

	public ReportTemplateRowChartDataProvider(OperationContext oc) {
		super(new ChartModel<Scope>(), 10);

		this.oc = oc;
	}

	@Override
	protected void query() {

	}

	public boolean addReportRow(ReportTemplateRow rowObj) {
		String key = rowObj.getKey();
		ReportConfig rc = rowObj.getReportConfig();
		if (this.model.getSerial(key) == null) {

			String sql = "select rpt.corpId, itm.value from " + rc.getItemEntityConfig().getTableName() + " itm"//
					+ "," + rc.getReportEntityConfig().getTableName() + " rpt"//
					+ " where itm.reportId = rpt.id and rpt.reportDate=? and itm.key = ?"//
					+ " order by itm.value"//
			;
			Date date = EnvUtil.newDateOfYearLastDay(2015);

			JdbcOperation<List<Tuple2<String, BigDecimal>>> op = new JdbcOperation<List<Tuple2<String, BigDecimal>>>() {

				@Override
				public List<Tuple2<String, BigDecimal>> doExecute(Connection con) {
					return this.template.executeQuery(con, sql, new Object[] { date, key },
							new ResultSetProcessor<List<Tuple2<String, BigDecimal>>>() {

								@Override
								public List<Tuple2<String, BigDecimal>> process(ResultSet rs) throws SQLException {
									List<Tuple2<String, BigDecimal>> rt = new ArrayList<>();
									while (rs.next()) {
										BigDecimal value = rs.getBigDecimal("value");
										String corpId = rs.getString("corpId");
										Tuple2<String, BigDecimal> tu = new Tuple2<>(corpId, value);
										rt.add(tu);
										LOG.debug("corpId:" + corpId + ",value:" + value);
									}
									return rt;
								}
							});
				}
			};

			List<Tuple2<String, BigDecimal>> list = this.oc.getEntityService().execute(op);

			ChartSerial<Scope> cs = new ReportRowChartSerial(key, list);
			this.model.addSerail(cs);
			return true;
		} else {
			return false;
		}
	}

}
