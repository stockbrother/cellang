package org.cellang.viewsframework.customized;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.cellang.collector.EnvUtil;
import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.CustomizedItemEntity;
import org.cellang.core.entity.CustomizedReportEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update all the property under the property key plus the entity type.
 * 
 * @author wu
 *
 */
public class CustomizedReportUpdater {
	private static final Logger LOG = LoggerFactory.getLogger(CustomizedReportUpdater.class);

	List<CustomizedReportItemDefine> defineList = new ArrayList<>();

	EntitySessionFactory esf;

	public CustomizedReportUpdater(EntitySessionFactory esf) {
		this.esf = esf;
		this.add(new RoeCustomizedReportItemDefine());
		//this.add(new InventoryTurnDaysCustomizedReportItemDefine());
		//this.add(new YingshouTurnDaysCustomizedReportItemDefine());
		//this.add(new YingfuTurnDaysCustomizedReportItemDefine());
		//this.add(new CashTurnDaysCustomizedReportItemDefine());
		this.add(new XiaoShouJingLiLvCustomizedReportItemDefine());		
		this.add(new ZongZiChanZhouZhuanLvCustomizedReportItemDefine());
		this.add(new QuanYiChengShuCustomizedReportItemDefine());
		

		for (CustomizedReportItemDefine define : this.defineList) {
			define.install(this.esf);
		}
	}

	private void add(CustomizedReportItemDefine def) {
		this.defineList.add(def);
	}

	public void execute() {
		this.esf.execute(new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				doExecuteDelete(es);
				return null;
			}

		});
		for (int year = 2015; year > 2010; year--) {

			List<CustomizedReportEntity> reportList = new ArrayList<CustomizedReportEntity>();
			int fyear = year;
			this.esf.execute(new EntityOp<Void>() {

				@Override
				public Void execute(EntitySession es) {
					doExecuteInsertReport(es, fyear, reportList);
					return null;
				}

			});
			for (CustomizedReportItemDefine define : this.defineList) {

				this.esf.execute(new EntityOp<Void>() {

					@Override
					public Void execute(EntitySession es) {
						doExecuteInsertItem(es, fyear, reportList, define);
						return null;
					}

				});
			}

		}
	}

	private void doExecuteDelete(EntitySession es) {

		LOG.info("Delete...");
		es.delete(CustomizedReportEntity.class);
		es.delete(CustomizedItemEntity.class);
	}

	private void doExecuteInsertReport(EntitySession es, int year, List<CustomizedReportEntity> reportList) {

		LOG.info("Insert Report,year:" + year + "...");

		List<CorpInfoEntity> l = es.query(CorpInfoEntity.class).execute(es);
		for (CorpInfoEntity corp : l) {
			String corpId = corp.getId();
			LOG.info("doExecute,year:" + year + ",corpId:" + corpId);
			CustomizedReportEntity report = new CustomizedReportEntity();
			report.setId(UUIDUtil.randomStringUUID());
			report.setCorpId(corpId);
			report.setReportDate(EnvUtil.newDateOfYearLastDay(year));//
			es.save(report);
			reportList.add(report);

		}
	}

	private void doExecuteInsertItem(EntitySession es, int year, List<CustomizedReportEntity> reportList,
			CustomizedReportItemDefine define) {

		LOG.info("Insert Item:" + define.getKey() + "...");

		for (CustomizedReportEntity report : reportList) {
			String corpId = report.getCorpId();
			String key = define.getKey();
			BigDecimal obj = define.getValue(corpId, year);
			CustomizedItemEntity ep = new CustomizedItemEntity();
			ep.setReportId(report.getId());
			ep.setId(UUIDUtil.randomStringUUID());
			ep.setKey(key);
			ep.setValue(obj);
			es.save(ep);

		}
	}

	private Double toDouble(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		}
		throw new RuntimeException("cannot cast to double from:" + obj);
	}
}
