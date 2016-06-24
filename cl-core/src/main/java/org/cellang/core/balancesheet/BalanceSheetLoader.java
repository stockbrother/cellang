package org.cellang.core.balancesheet;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.entity.BalanceItemEntity;
import org.cellang.core.entity.BalanceSheetEntity;
import org.cellang.core.entity.EntityService;

import au.com.bytecode.opencsv.CSVReader;

public class BalanceSheetLoader {
	EntityService es;

	public BalanceSheetLoader(EntityService es) {
		this.es = es;
	}

	public void load(Reader rd) {
		CSVReader reader = new CSVReader(rd);
		try {
			MapWrapper headers = new MapWrapper();
			MapWrapper body = new MapWrapper();
			MapWrapper currentMap = null;
			while (true) {

				String[] next = reader.readNext();
				if (next == null) {
					break;
				}
				if ("Header".equals(next[0])) {
					currentMap = headers;
					continue;
				} else if ("Body".equals(next[0])) {
					currentMap = body;
					continue;
				}
				String key = next[0];
				key = key.trim();
				String value = next[1];
				value = value.trim();
				currentMap.put(key, value);
			}
			BigDecimal unit = headers.getAsBigDecimal("单位", true);
			String corpId = headers.get("公司代码", true);
			String corpName = headers.get("公司名称", true);
			Date reportDate = headers.getAsDate("报告日期");
			BalanceSheetEntity be = new BalanceSheetEntity();
			be.setId(UUIDUtil.randomStringUUID());
			be.setCorpId(corpId);
			be.setCorpName(corpName);
			be.setQuanter(4);
			be.setReportDate(reportDate);
			es.save(be);

			for (String key : body.map.keySet()) {
				BalanceItemEntity ie = new BalanceItemEntity();
				BigDecimal value = body.getAsBigDecimal(key, true);
				value = value.multiply(unit);
				ie.setId(UUIDUtil.randomStringUUID());//
				ie.setBalanceSheetId(be.getId());
				ie.setKey(key);
				ie.setValue(value);
				es.save(ie);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void doLoad() {

	}
}
