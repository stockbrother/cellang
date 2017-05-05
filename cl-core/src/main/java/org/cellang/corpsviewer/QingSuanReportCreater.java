package org.cellang.corpsviewer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.collector.EnvUtil;
import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.QingSuanItemEntity;
import org.cellang.core.entity.QingSuanReportEntity;
import org.cellang.corpsviewer.utils.ValueMap;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.report.ReportCreater;

public class QingSuanReportCreater implements ReportCreater {

	private String corpId;

	private int years;

	private OperationContext oc;

	private static Map<String, BigDecimal> weightMap = new HashMap<String, BigDecimal>();

	private static BigDecimal oneH = new BigDecimal(100);

	{
		setWeightPercent("货币资金", 100);
		setWeightPercent("应收票据", 100);
		setWeightPercent("应收账款", 80);
		setWeightPercent("存货", 50);
		setWeightPercent("固定资产", 20);
	}

	private static void setWeightPercent(String key, int percent) {
		weightMap.put(key, new BigDecimal(percent).divide(oneH));
	}

	public QingSuanReportCreater(OperationContext oc, String sid, int i) {
		this.oc = oc;
		this.corpId = sid;
		this.years = i;
	}

	public void delete(String corpId) {
		oc.getEntityService().execute(new EntityOp<Object>() {

			@Override
			public Object execute(EntitySession es) {
				return es.deleteReport(QingSuanReportEntity.class, QingSuanItemEntity.class, corpId);
			}
		});
	}

	public void update(boolean force) {
		this.delete(corpId);
		oc.getEntityService().execute(new EntityOp<Object>() {

			@Override
			public Object execute(EntitySession es) {
				int year = 2015;
				for (int i = 0; i < years; i++) {
					Date rDate = EnvUtil.newDateOfYearLastDay(year);
					String rId = UUIDUtil.randomStringUUID();
					QingSuanReportEntity r = new QingSuanReportEntity();
					r.setId(rId);
					r.setCorpId(corpId);
					r.setReportDate(rDate);
					es.save(r);

					BigDecimal qingSuan = new BigDecimal(0L);

					List<BalanceSheetItemEntity> riL = es.getReportItemList(BalanceSheetReportEntity.class,
							BalanceSheetItemEntity.class, corpId, new Date[] { rDate });

					ValueMap<String, BigDecimal> valueMap = new ValueMap<>();

					for (BalanceSheetItemEntity ri : riL) {
						String key = ri.getKey();
						BigDecimal weight = weightMap.get(key);
						BigDecimal value = ri.getValue();
						if (weight != null && value != null) {
							qingSuan = qingSuan.add(value.multiply(weight));
						}
						valueMap.put(key, value);
					}

					{

						QingSuanItemEntity qsI = new QingSuanItemEntity();
						qsI.setId(UUIDUtil.randomStringUUID());
						qsI.setKey("清算价值");
						qsI.setReportId(rId);
						qsI.setValue(qingSuan);
						es.save(qsI);
					}
					{
						//
						BigDecimal jingZiChan = valueMap.get("归属于母公司股东权益合计", BigDecimal.ZERO);
						QingSuanItemEntity qsI = new QingSuanItemEntity();
						qsI.setId(UUIDUtil.randomStringUUID());
						qsI.setKey("净资产");
						qsI.setReportId(rId);
						qsI.setValue(jingZiChan);
						es.save(qsI);
					}
					{
						//
						BigDecimal liuDong = valueMap.get("流动资产合计", BigDecimal.ZERO);
						BigDecimal fuZhai = valueMap.get("负债合计", BigDecimal.ZERO);
						BigDecimal liuDongZiChanJiaZhi = liuDong.subtract(fuZhai);
						QingSuanItemEntity qsI = new QingSuanItemEntity();
						qsI.setId(UUIDUtil.randomStringUUID());
						qsI.setKey("流动资产价值");
						qsI.setReportId(rId);
						qsI.setValue(liuDongZiChanJiaZhi);
						es.save(qsI);
					}
					{
						BigDecimal huoBi = valueMap.get("货币资金", BigDecimal.ZERO);
						BigDecimal yinShouPiaoJu = valueMap.get("应收票据", BigDecimal.ZERO);
						BigDecimal fuZhai = valueMap.get("负债合计", BigDecimal.ZERO);
						BigDecimal xianJinJiaZhi = huoBi.add(yinShouPiaoJu).subtract(fuZhai);

						QingSuanItemEntity qsI = new QingSuanItemEntity();
						qsI.setId(UUIDUtil.randomStringUUID());
						qsI.setKey("现金价值");
						qsI.setReportId(rId);
						qsI.setValue(xianJinJiaZhi);
						es.save(qsI);
					}
					year--;
				}
				return null;
			}
		});
	}

}
