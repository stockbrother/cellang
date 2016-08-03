package org.cellang.console.customized;

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
				doExecute(es);
				return null;
			}

		});
	}

	private void doExecute(EntitySession es) {

		es.delete(CustomizedReportEntity.class);
		es.delete(CustomizedItemEntity.class);
		LOG.info("doExecute");

		List<CorpInfoEntity> l = es.query(CorpInfoEntity.class).execute(es);
		for (CorpInfoEntity corp : l) {
			String corpId = corp.getId();
			for (int year = 2015; year > 2010; year--) {
				LOG.info("doExecute,year:" + year + ",corpId:" + corpId);
				CustomizedReportEntity report = new CustomizedReportEntity();
				report.setId(UUIDUtil.randomStringUUID());
				report.setCorpId(corpId);
				report.setReportDate(EnvUtil.newDateOfYearLastDay(year));//
				es.save(report);

				for (CustomizedReportItemDefine define : this.defineList) {
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
