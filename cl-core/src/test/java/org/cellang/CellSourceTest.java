package org.cellang;

import java.math.BigDecimal;
import java.util.List;

import org.cellang.core.lang.CellRecord;
import org.cellang.core.lang.CellSource;
import org.cellang.core.lang.CellValue;
import org.cellang.core.lang.jdbc.JdbcCellSourceConfiguration;
import org.cellang.core.lang.jdbc.RelationsTable;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CellSourceTest extends TestCase {

	public void test() {

		JdbcCellSourceConfiguration cfg = new JdbcCellSourceConfiguration();
		cfg.setUrl("jdbc:h2:./target/test/h2db");

		CellSource as = cfg.create();		
		as.open();
		as.clear();
		try {
			CellRecord ao1 = new CellRecord();
			ao1.setTypeId("type1");
			ao1.setValue(CellValue.getInstance(new BigDecimal("1.2")));
			String id1 = as.save(ao1);
			Assert.assertEquals(id1, ao1.getId());

			CellRecord ao2 = new CellRecord();
			ao2.setTypeId("type2");
			ao2.setValue(CellValue.getInstance(new BigDecimal("2.3")));
			String id2 = as.save(ao2);
			Assert.assertEquals(id2, ao2.getId());

			as.saveRelation(id1, RelationsTable.FV_CHILD, id2);
			List<CellRecord> crL = as.getCellList(id1, "type2");
			Assert.assertEquals(1, crL.size());

			Assert.assertEquals(id2, crL.get(0).getId());
			as.clear();
			CellRecord ao3 = as.getCell(id1);
			Assert.assertNull(ao3);//
		} finally {
			as.close();
		}
	}
}
