/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package com.fs.dataservice.impl.test.cases;

import java.util.Date;
import java.util.List;

import org.cellang.elasticnode.Node;
import org.cellang.elasticnode.operations.NodeSearchOperationI;

import com.fs.dataservice.impl.test.MockNode;
import com.fs.dataservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class TimestampQueryTest extends TestBase {

	public void testTimestampQuery() throws Exception {
		int size = 4;
		int fromIdx = 1;
		for (int i = 0; i < size; i++) {
			MockNode mn1 = new MockNode().forCreate(this.datas);
			mn1.setProperty(MockNode.FIELD1, "value" + i + "-1");
			mn1.setProperty(MockNode.FIELD2, "value" + i + "-2");
			mn1.setProperty(MockNode.FIELD3, "" + System.currentTimeMillis());

			mn1.save(true);

			Thread.sleep(1);// to distinct the timestamp value
		}

		// query the #1 node
		Date timestamp1 = null;
		{
			NodeSearchOperationI<MockNode> qo = this.datas.prepareNodeSearch(MockNode.class);
			qo.propertyEq(MockNode.FIELD1, "value1-" + fromIdx);
			qo.sortTimestamp(true);//
			{
				List<MockNode> mL = qo.execute().getResult().assertNoError().list();
				assertEquals(1, mL.size());
				MockNode mnX = mL.get(0);
				timestamp1 = mnX.getTimestamp();
				String f3 = (String) mnX.getProperty(MockNode.FIELD3, true);
				Long ts2 = Long.parseLong(f3);
				int span = (int) (timestamp1.getTime() - ts2);
				assertTrue("too much span,timestamp error,timestamp:" + timestamp1 + "," + "field3:"
						+ new Date(ts2), span < 100);

			}

		}
		// query from #1,so result is #1,#2,#3
		NodeSearchOperationI<MockNode> qo = this.datas.prepareNodeSearch(MockNode.class);
		qo.propertyGt(Node.PK_TIMESTAMP, timestamp1, true);
		qo.sortTimestamp(true);//
		{
			List<MockNode> mL = qo.execute().getResult().assertNoError().list();
			assertEquals(size - fromIdx, mL.size());
			for (int i = fromIdx; i < size; i++) {
				MockNode nodei = mL.get(i - fromIdx);//
				String field1 = (String) nodei.getProperty(MockNode.FIELD1);
				assertEquals("value" + (size - i) + "-1", field1);
			}
		}

	}
}
