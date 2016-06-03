/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package org.cellang.elastictable.test.cases;

import java.util.Date;
import java.util.List;

import org.cellang.elastictable.TableRow;
import org.cellang.elastictable.operations.NodeSearchOperationI;
import org.cellang.elastictable.test.MockObject;
import org.cellang.elastictable.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class TimestampQueryTest extends TestBase {

	public void testTimestampQuery() throws Exception {
		int size = 4;
		int fromIdx = 1;
		for (int i = 0; i < size; i++) {
			MockObject mn1 = new MockObject().forCreate(this.datas);
			mn1.setProperty(MockObject.FIELD1, "value" + i + "-1");
			mn1.setProperty(MockObject.FIELD2, "value" + i + "-2");
			mn1.setProperty(MockObject.FIELD3, "" + System.currentTimeMillis());

			mn1.save(true);

			Thread.sleep(1);// to distinct the timestamp value
		}

		// query the #1 node
		Date timestamp1 = null;
		{
			NodeSearchOperationI<MockObject> qo = this.datas.prepareNodeSearch(MockObject.class);
			qo.propertyEq(MockObject.FIELD1, "value1-" + fromIdx);
			qo.sortTimestamp(true);//
			{
				List<MockObject> mL = qo.execute().getResult().assertNoError().list();
				assertEquals(1, mL.size());
				MockObject mnX = mL.get(0);
				timestamp1 = mnX.getTimestamp();
				String f3 = (String) mnX.getProperty(MockObject.FIELD3, true);
				Long ts2 = Long.parseLong(f3);
				int span = (int) (timestamp1.getTime() - ts2);
				assertTrue("too much span,timestamp error,timestamp:" + timestamp1 + "," + "field3:"
						+ new Date(ts2), span < 100);

			}

		}
		// query from #1,so result is #1,#2,#3
		NodeSearchOperationI<MockObject> qo = this.datas.prepareNodeSearch(MockObject.class);
		qo.propertyGt(TableRow.PK_TIMESTAMP, timestamp1, true);
		qo.sortTimestamp(true);//
		{
			List<MockObject> mL = qo.execute().getResult().assertNoError().list();
			assertEquals(size - fromIdx, mL.size());
			for (int i = fromIdx; i < size; i++) {
				MockObject nodei = mL.get(i - fromIdx);//
				String field1 = (String) nodei.getProperty(MockObject.FIELD1);
				assertEquals("value" + (size - i) + "-1", field1);
			}
		}

	}
}
