package org.cellang.console.format;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class ReportItemLocators {
	static String content = "\n"//
			+ ",资产总计                       \n"//
			+ ",,流动资产合计                  \n"//
			+ ",,,货币资金                     \n"//
			+ ",,,结算备付金                   \n"//
			+ ",,,拆出资金                     \n"//
			+ ",,,交易性金融资产               \n"//
			+ ",,,衍生金融资产                 \n"//
			+ ",,,应收票据                     \n"//
			+ ",,,应收账款                     \n"//
			+ ",,,预付款项                     \n"//
			+ ",,,应收保费                     \n"//
			+ ",,,应收分保账款                 \n"//
			+ ",,,应收分保合同准备金           \n"//
			+ ",,,应收利息                     \n"//
			+ ",,,应收股利                     \n"//
			+ ",,,其他应收款                   \n"//
			+ ",,,应收出口退税                 \n"//
			+ ",,,应收补贴款                   \n"//
			+ ",,,应收保证金                   \n"//
			+ ",,,内部应收款                   \n"//
			+ ",,,买入返售金融资产             \n"//
			+ ",,,存货                         \n"//
			+ ",,,待摊费用                     \n"//
			+ ",,,待处理流动资产损益           \n"//
			+ ",,,一年内到期的非流动资产       \n"//
			+ ",,,其他流动资产                 \n"//
			+ ",,非流动资产合计                \n"//
			+ ",,,发放贷款及垫款               \n"//
			+ ",,,可供出售金融资产             \n"//
			+ ",,,持有至到期投资               \n"//
			+ ",,,长期应收款                   \n"//
			+ ",,,长期股权投资                 \n"//
			+ ",,,其他长期投资                 \n"//
			+ ",,,投资性房地产                 \n"//
			+ ",,,固定资产原值                 \n"//
			+ ",,,累计折旧                     \n"//
			+ ",,,固定资产净值                 \n"//
			+ ",,,固定资产减值准备             \n"//
			+ ",,,固定资产                     \n"//
			+ ",,,在建工程                     \n"//
			+ ",,,工程物资                     \n"//
			+ ",,,固定资产清理                 \n"//
			+ ",,,生产性生物资产               \n"//
			+ ",,,公益性生物资产               \n"//
			+ ",,,油气资产                     \n"//
			+ ",,,无形资产                     \n"//
			+ ",,,开发支出                     \n"//
			+ ",,,商誉                         \n"//
			+ ",,,长期待摊费用                 \n"//
			+ ",,,股权分置流通权               \n"//
			+ ",,,递延所得税资产               \n"//
			+ ",,,其他非流动资产               \n"//
			+ ",负债合计                       \n"//
			+ ",,流动负债合计                  \n"//
			+ ",,,短期借款                     \n"//
			+ ",,,向中央银行借款               \n"//
			+ ",,,吸收存款及同业存放           \n"//
			+ ",,,拆入资金                     \n"//
			+ ",,,交易性金融负债               \n"//
			+ ",,,衍生金融负债                 \n"//
			+ ",,,应付票据                     \n"//
			+ ",,,应付账款                     \n"//
			+ ",,,预收账款                     \n"//
			+ ",,,卖出回购金融资产款           \n"//
			+ ",,,应付手续费及佣金             \n"//
			+ ",,,应付职工薪酬                 \n"//
			+ ",,,应交税费                     \n"//
			+ ",,,应付利息                     \n"//
			+ ",,,应付股利                     \n"//
			+ ",,,其他应交款                   \n"//
			+ ",,,应付保证金                   \n"//
			+ ",,,内部应付款                   \n"//
			+ ",,,其他应付款                   \n"//
			+ ",,,预提费用                     \n"//
			+ ",,,预计流动负债                 \n"//
			+ ",,,应付分保账款                 \n"//
			+ ",,,保险合同准备金               \n"//
			+ ",,,代理买卖证券款               \n"//
			+ ",,,代理承销证券款               \n"//
			+ ",,,国际票证结算                 \n"//
			+ ",,,国内票证结算                 \n"//
			+ ",,,递延收益                     \n"//
			+ ",,,应付短期债券                 \n"//
			+ ",,,一年内到期的非流动负债       \n"//
			+ ",,,其他流动负债                 \n"//
			+ ",,非流动负债合计                \n"//
			+ ",,,长期借款                     \n"//
			+ ",,,应付债券                     \n"//
			+ ",,,长期应付款                   \n"//
			+ ",,,专项应付款                   \n"//
			+ ",,,预计非流动负债               \n"//
			+ ",,,长期递延收益                 \n"//
			+ ",,,递延所得税负债               \n"//
			+ ",,,其他非流动负债               \n"//
			+ ",所有者权益合计                 \n"//
			+ ",,实收资本              \n"//
			+ ",,资本公积                      \n"//
			+ ",,减:库存股                     \n"//
			+ ",,专项储备                      \n"//
			+ ",,盈余公积                      \n"//
			+ ",,一般风险准备                  \n"//
			+ ",,未确定的投资损失              \n"//
			+ ",,未分配利润                    \n"//
			+ ",,拟分配现金股利                \n"//
			+ ",,外币报表折算差额              \n"//
			+ ",,归属于母公司股东权益合计      \n"//
			+ ",,少数股东权益\n"//

	;
	ReportItemLocator root;
	Map<String, ReportItemLocator> keyMap = new HashMap<>();

	public ReportItemLocators(ReportItemLocator root2) {
		this.root = root2;
		this.doInit(this.root);
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

	public static ReportItemLocators load() {
		ReportItemLocator root = new ReportItemLocator(null, null);
		
		StringReader sr = new StringReader(content);
		CSVReader cr = new CSVReader(sr);
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

		return new ReportItemLocators(root);
	}
	

	public ReportItemLocator getRoot() {
		return this.root;
	}

	public ReportItemLocator get(String key) {
		return this.keyMap.get(key);//
	}

}
