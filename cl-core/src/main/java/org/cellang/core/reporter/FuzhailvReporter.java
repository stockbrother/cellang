package org.cellang.core.reporter;

import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.cellang.commons.jdbc.ResultSetProcessor;
import org.cellang.core.entity.BalanceItemEntity;
import org.cellang.core.entity.BalanceSheetEntity;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.DateInfoEntity;
import org.cellang.core.entity.EntityService;

import au.com.bytecode.opencsv.CSVWriter;

public class FuzhailvReporter extends Reporter {

	private static String SQL = //
	"select t.*,t.fzhj/t.zczj from(" //
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
			+ ")" //
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

	public FuzhailvReporter(EntityService es) {
		super(es);
	}

	@Override
	public void generate(Writer w) {
		es.getPool().executeQuery(SQL, new ResultSetProcessor() {

			@Override
			public Object process(ResultSet rs) throws SQLException {
				return FuzhailvReporter.this.processResultSet(rs, w);
			}
		});
	}

	private Object processResultSet(ResultSet rs, Writer w) throws SQLException {
		CSVWriter cw = new CSVWriter(w);
		int total = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			String[] row = new String[total];
			for (int i = 0; i < total; i++) {
				row[i] = "" + rs.getObject(i + 1);
			}
			cw.writeNext(row);
		}

		return null;
	}

}
