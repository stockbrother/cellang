package org.cellang.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Date;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.balancesheet.CsvHeaderRowMap;
import org.cellang.core.balancesheet.CsvRow;
import org.cellang.core.balancesheet.CsvRowMap;
import org.cellang.core.entity.BalanceItemEntity;
import org.cellang.core.entity.BalanceSheetEntity;
import org.cellang.core.entity.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public class BalanceSheetFileProcessor extends FileProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(BalanceSheetFileProcessor.class);

	EntityService es;

	public BalanceSheetFileProcessor(EntityService es) {
		this.es = es;
	}

	@Override
	public void process(File file) {
		LOG.info("process file:" + file.getAbsolutePath());
		FileReader fr;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		this.load(fr);
	}

	public void load(Reader rd) {
		CSVReader reader = new CSVReader(rd);
		try {
			CsvHeaderRowMap headers = new CsvHeaderRowMap();
			CsvRowMap body = new CsvRowMap();
			CsvRowMap currentMap = null;
			int lineNumber = 0;
			while (true) {
				lineNumber++;
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
				if (key.length() == 0 && next.length <= 1) {
					// ignore this empty line.
					continue;
				}
				currentMap.put(key, new CsvRow(lineNumber, next));
			}
			//
			Date[] reportDateArray = headers.getReportDateArray();
			BigDecimal unit = headers.get("单位", true).getAsBigDecimal(1, true);
			String corpId = headers.get("公司代码", true).getString(1, true);

			for (int i = 0; i < reportDateArray.length; i++) {

				Date reportDate = headers.get("报告日期", true).getAsDate(i + 1, headers.getDateFormat());
				if (reportDate == null) {
					break;
				}
				BalanceSheetEntity be = new BalanceSheetEntity();
				be.setId(UUIDUtil.randomStringUUID());
				be.setCorpId(corpId);
				be.setReportDate(reportDate);
				es.save(be);

				for (String key : body.map.keySet()) {
					BigDecimal value = body.get(key, true).getAsBigDecimal(i + 1, false);
					if (value == null) {// ignore this value.
						continue;
					}
					BalanceItemEntity ie = new BalanceItemEntity();
					value = value.multiply(unit);
					ie.setId(UUIDUtil.randomStringUUID());//
					ie.setBalanceSheetId(be.getId());
					ie.setKey(key);
					ie.setValue(value);
					es.save(ie);
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
