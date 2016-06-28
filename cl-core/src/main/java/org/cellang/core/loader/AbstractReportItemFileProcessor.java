package org.cellang.core.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Date;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.entity.AbstractReportEntity;
import org.cellang.core.entity.AbstractReportItemEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public abstract class AbstractReportItemFileProcessor<T extends AbstractReportEntity, I extends AbstractReportItemEntity>
		extends FileProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractReportItemFileProcessor.class);

	EntityService es;
	Class<T> reportEntityCls;
	Class<I> itemEntityCls;
	EntityConfig reportEntityConfig;

	EntityConfig itemEntityConfig;

	public AbstractReportItemFileProcessor(EntityService es, Class<T> entityCls, Class<I> cls2) {
		this.es = es;
		this.reportEntityCls = entityCls;
		this.itemEntityCls = cls2;
		this.reportEntityConfig = this.es.getEntityConfigFactory().get(this.reportEntityCls);
		this.itemEntityConfig = this.es.getEntityConfigFactory().get(this.itemEntityCls);

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
				AbstractReportEntity be = (AbstractReportEntity) this.reportEntityConfig.newEntity();

				be.setId(UUIDUtil.randomStringUUID());
				be.setCorpId(corpId);
				be.setReportDate(reportDate);
				es.save(be);

				for (String key : body.map.keySet()) {
					BigDecimal value = body.get(key, true).getAsBigDecimal(i + 1, false);
					if (value == null) {// ignore this value.
						continue;
					}
					AbstractReportItemEntity ie = (AbstractReportItemEntity) this.itemEntityConfig.newEntity();

					value = value.multiply(unit);
					ie.setId(UUIDUtil.randomStringUUID());//
					ie.setReportId(be.getId());
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
