/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 19, 2013
 */
package com.fs.dataservice.impl.test.cases;

import java.util.List;

import org.cellang.elasticnode.operations.NodeSearchOperationI;

import com.fs.dataservice.impl.test.MockNode;
import com.fs.dataservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class MultiMatchQueryTest extends TestBase {

	public void testQueryByMultiMatch() {
		MockNode mn1 = new MockNode().forCreate(this.datas);
		mn1.setProperty(MockNode.FIELD1, "value11");
		mn1.setProperty(MockNode.FIELD2, "valuex1 valuex2 valuex3");
		mn1.setProperty(MockNode.FIELD3, "value13");
		mn1.save(true);

		MockNode mn2 = new MockNode().forCreate(this.datas);
		mn2.setProperty(MockNode.FIELD1, "value21");
		mn2.setProperty(MockNode.FIELD2, "value22");
		mn2.setProperty(MockNode.FIELD3, "valuex1 valuex2 valuex3");
		mn2.save(true);

		NodeSearchOperationI<MockNode> qo = this.datas.prepareNodeSearch(MockNode.class);
		qo.multiMatch(new String[] { MockNode.FIELD2, MockNode.FIELD3 }, "valuex1 valuex3", 0);
		List<MockNode> mnl = qo.execute().getResult().assertNoError().list();
		assertEquals("slop=0,should not match,but got:" + mnl, 0, mnl.size());

		qo = this.datas.prepareNodeSearch(MockNode.class);
		qo.multiMatch(new String[] { MockNode.FIELD2, MockNode.FIELD3 }, "valuex1 valuex3", 2);
		mnl = qo.execute().getResult().assertNoError().list();
		assertEquals("slop=2,should match two", 2, mnl.size());

	}

}
