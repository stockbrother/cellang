package org.cellang.core.reporter;

import java.io.Writer;
import java.util.List;

import org.cellang.core.entity.BalanceItemEntity;
import org.cellang.core.entity.BalanceSheetEntity;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.DateInfoEntity;
import org.cellang.core.entity.EntityService;
import org.cellang.core.entity.FuzhaiQuanyiBiEntity;

import au.com.bytecode.opencsv.CSVWriter;

public class FuzhaiQuanyiBiReporter extends Reporter {

	private static String SQL = //
			"select t.*,t.fzhj/t.syzqyhj from(" //
					+ "select "//
					+ " ci.code code"//
					+ ",ci.name name"//
					+ ",di.value date"//
					+ ",("//
					+ "  select bi.value from " + BalanceItemEntity.tableName + " bi" //
					+ "  ," + BalanceSheetEntity.tableName + " bs" //
					+ "  where bi.balanceSheetId = bs.id and bs.corpId=ci.code"//
					+ "  and bi.key='资产总计' and bs.reportDate=di.value" //
					+ ") zczj" //
					+ ",("//
					+ "  select bi.value from " + BalanceItemEntity.tableName + " bi" //
					+ "  ," + BalanceSheetEntity.tableName + " bs" //
					+ "  where bi.balanceSheetId = bs.id and bs.corpId=ci.code"//
					+ "  and bi.key='所有者权益合计' and bs.reportDate=di.value" //
					+ ") syzqyhj" //
					+ ",("//
					+ "  select bi.value from " + BalanceItemEntity.tableName + " bi" //
					+ "  ," + BalanceSheetEntity.tableName + " bs" //
					+ "  where bi.balanceSheetId = bs.id and bs.corpId=ci.code"//
					+ "  and bi.key='负债合计' and bs.reportDate=di.value" //
					+ ") fzhj" //
					+ " from " + CorpInfoEntity.tableName + " ci" //
					+ "," + DateInfoEntity.tableName + " di"//
					+ ") t"//
	;

	public FuzhaiQuanyiBiReporter(EntityService es) {
		super(es);
	}

	@Override
	public void generate(Writer w) {
		List<FuzhaiQuanyiBiEntity> fl = es.getList(FuzhaiQuanyiBiEntity.class);

		CSVWriter cw = new CSVWriter(w, ',', CSVWriter.NO_QUOTE_CHARACTER);

		for (int i = 0; i < fl.size(); i++) {
			FuzhaiQuanyiBiEntity e = fl.get(i);
			String[] row = new String[] { e.getCorpId(), "" + e.getFuzhai(), "" + e.getQuanyi(), "" + e.getFzqyb() };
			cw.writeNext(row);
		}

	}

}
