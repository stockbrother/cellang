package org.cellang.viewsframework.format;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.cellang.core.entity.AbstractReportEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.CashFlowStatementReportEntity;
import org.cellang.core.entity.CustomizedReportEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;

import au.com.bytecode.opencsv.CSVReader;

public class ReportItemLocators {
	public static class Group {
		ReportItemLocator root;
		Map<String, ReportItemLocator> keyMap = new HashMap<>();

		public Group(ReportItemLocator root) {
			this.root = root;
			this.doInit(root);//
		}

		public ReportItemLocator getRoot() {
			return root;
		}

		private void doInit(ReportItemLocator ril) {
			ReportItemLocator old = keyMap.put(ril.getKey(), ril);
			if (old != null) {
				throw new RuntimeException("duplicated:" + ril);
			}
			for (ReportItemLocator child : ril.getChildList()) {
				doInit(child);
			}
		}

		public ReportItemLocator get(String key) {
			return this.keyMap.get(key);//
		}

	}

	Map<Class<? extends AbstractReportEntity>, Group> groupMap = new HashMap<>();

	static ReportItemLocators ME;

	private ReportItemLocators() {

	}

	public static ReportItemLocators getInstance() {
		if (ME != null) {
			return ME;
		}
		ME = new ReportItemLocators();
		{

			Group g1 = load(new StringReader(BalanceSheetContent.content));
			ME.groupMap.put(BalanceSheetReportEntity.class, g1);
		}
		{

			Group g1 = load(new StringReader(BalanceSheetContent.incomeStatement));
			ME.groupMap.put(IncomeStatementReportEntity.class, g1);
		}
		{

			Group g1 = load(new StringReader(BalanceSheetContent.cashFlowStatement));
			ME.groupMap.put(CashFlowStatementReportEntity.class, g1);
		}
		
		{

			Group g2 = load(new StringReader(CustomizedReport.content));
			ME.groupMap.put(CustomizedReportEntity.class, g2);
		}
		return ME;
	}

	private static Group load(Reader reader) {
		ReportItemLocator root = new ReportItemLocator(null, null);

		CSVReader cr = new CSVReader(reader);
		ReportItemLocator current = root;
		int preDepth = 0;
		int lineNum = -1;
		root.setOrder(lineNum);

		while (true) {
			lineNum++;
			String[] line;
			try {
				line = cr.readNext();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (line == null) {
				break;
			}
			int depth = -1;

			for (int i = 0; i < line.length; i++) {
				String field = line[i].trim();
				if (field.length() > 0) {
					if (depth == -1) {
						depth = i;
					}
				} else {
					field = null;
				}
				line[i] = field;
			}

			if (depth == -1) {
				continue;// ignore this line.
			}
			String key = line[depth];
			int diff = depth - preDepth;

			for (; diff <= 0; diff++) {
				current = current.getParent();
			}
			current = current.newChild(key);
			current.setOrder(lineNum);

			preDepth = depth;
		}

		return new Group(root);
	}

	public Group get(Class<? extends AbstractReportEntity> cls) {
		return this.groupMap.get(cls);
	}

}
