/**
 * 
 */
package com.fs.dataservice.impl.test.cases;

import java.util.List;

import org.cellang.elasticnode.operations.NodeSearchOperationI;

import com.fs.dataservice.impl.test.MockNode;
import com.fs.dataservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * <p>
 * 
 */
public class ESITest extends TestBase {

	public void testESIException() throws Exception{
		
		MockNode mn1 = new MockNode().forCreate(this.datas);
		mn1.setProperty(MockNode.FIELD1, "value1");
		mn1.setProperty(MockNode.FIELD2, "value12");
		mn1.setProperty(MockNode.FIELD3, "value13");
		mn1.save(true);
		
		Thread.sleep(100);
		
		MockNode mn2 = new MockNode().forCreate(this.datas);
		mn2.setProperty(MockNode.FIELD1, "value1");
		mn2.setProperty(MockNode.FIELD2, "value22");
		mn2.setProperty(MockNode.FIELD3, "value23");
		mn2.save(true);
		Thread.sleep(100);
		
		
		NodeSearchOperationI<MockNode> qo = this.datas.prepareNodeSearch(MockNode.class);
		qo.propertyEq(MockNode.FIELD1, "value1");
		qo.sortTimestamp(true);
		qo.first(0);
		qo.maxSize(10000);
		List<MockNode> mL = qo.execute().getResult().assertNoError().list();
		assertEquals(2, mL.size());
		MockNode mnX = mL.get(0);
		assertEquals(mn2.getTarget(), mnX.getTarget());
		mnX = mL.get(1);
		assertEquals(mn1.getTarget(), mnX.getTarget());
	}

}
