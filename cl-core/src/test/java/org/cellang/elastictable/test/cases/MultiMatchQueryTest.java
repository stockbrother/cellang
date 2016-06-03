/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 19, 2013
 */
package org.cellang.elastictable.test.cases;

import java.util.List;

import org.cellang.elastictable.operations.NodeSearchOperationI;
import org.cellang.elastictable.test.MockObject;
import org.cellang.elastictable.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class MultiMatchQueryTest extends TestBase {

	public void testQueryByMultiMatch() {
		MockObject mn1 = new MockObject().forCreate(this.datas);
		mn1.setProperty(MockObject.FIELD1, "value11");
		mn1.setProperty(MockObject.FIELD2, "valuex1 valuex2 valuex3");
		mn1.setProperty(MockObject.FIELD3, "value13");
		mn1.save(true);

		MockObject mn2 = new MockObject().forCreate(this.datas);
		mn2.setProperty(MockObject.FIELD1, "value21");
		mn2.setProperty(MockObject.FIELD2, "value22");
		mn2.setProperty(MockObject.FIELD3, "valuex1 valuex2 valuex3");
		mn2.save(true);

		NodeSearchOperationI<MockObject> qo = this.datas.prepareNodeSearch(MockObject.class);
		qo.multiMatch(new String[] { MockObject.FIELD2, MockObject.FIELD3 }, "valuex1 valuex3", 0);
		List<MockObject> mnl = qo.execute().getResult().assertNoError().list();
		assertEquals("slop=0,should not match,but got:" + mnl, 0, mnl.size());

		qo = this.datas.prepareNodeSearch(MockObject.class);
		qo.multiMatch(new String[] { MockObject.FIELD2, MockObject.FIELD3 }, "valuex1 valuex3", 2);
		mnl = qo.execute().getResult().assertNoError().list();
		assertEquals("slop=2,should match two", 2, mnl.size());

	}

}
