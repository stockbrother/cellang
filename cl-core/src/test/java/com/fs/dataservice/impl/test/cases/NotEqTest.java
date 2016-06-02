/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
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
public class NotEqTest extends TestBase {

	public void testDeleteByUid() {
		MockNode mn1 = new MockNode().forCreate(this.datas);
		mn1.setProperty(MockNode.FIELD1, "value1");
		mn1.setProperty(MockNode.FIELD2, "value12");
		mn1.setProperty(MockNode.FIELD3, "value13");
		mn1.save(true);
		MockNode mn2 = new MockNode().forCreate(this.datas);
		mn2.setProperty(MockNode.FIELD1, "value1");
		mn2.setProperty(MockNode.FIELD2, "value22");
		mn2.setProperty(MockNode.FIELD3, "value23");
		mn2.save(true);
		MockNode mn3 = new MockNode().forCreate(this.datas);
		mn3.setProperty(MockNode.FIELD1, "value1");
		mn3.setProperty(MockNode.FIELD2, "value32");
		mn3.setProperty(MockNode.FIELD3, "value33");
		mn3.save(true);
		
		NodeSearchOperationI<MockNode> qo = this.datas.prepareNodeSearch(MockNode.class);
		qo.propertyEq(MockNode.FIELD1, "value1");
		qo.propertyNotEq(MockNode.FIELD2, "value12");
		qo.sortTimestamp(false);//

		List<MockNode> mL = qo.execute().getResult().assertNoError().list();
		assertEquals(2, mL.size());
		MockNode mnX = mL.get(0);
		assertEquals(mn2.getTarget(), mnX.getTarget());
		mnX = mL.get(1);
		assertEquals(mn3.getTarget(), mnX.getTarget());

	}

}
