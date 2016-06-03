/**
 * 
 */
package org.cellang.elastictable.test.cases;

import java.util.List;

import org.cellang.elastictable.operations.NodeSearchOperationI;
import org.cellang.elastictable.test.MockObject;
import org.cellang.elastictable.test.cases.support.TestBase;

/**
 * @author wu
 * <p>
 * 
 */
public class ESITest extends TestBase {

	public void testESIException() throws Exception{
		
		MockObject mn1 = new MockObject().forCreate(this.datas);
		mn1.setProperty(MockObject.FIELD1, "value1");
		mn1.setProperty(MockObject.FIELD2, "value12");
		mn1.setProperty(MockObject.FIELD3, "value13");
		mn1.save(true);
		
		Thread.sleep(100);
		
		MockObject mn2 = new MockObject().forCreate(this.datas);
		mn2.setProperty(MockObject.FIELD1, "value1");
		mn2.setProperty(MockObject.FIELD2, "value22");
		mn2.setProperty(MockObject.FIELD3, "value23");
		mn2.save(true);
		Thread.sleep(100);
		
		
		NodeSearchOperationI<MockObject> qo = this.datas.prepareNodeSearch(MockObject.class);
		qo.propertyEq(MockObject.FIELD1, "value1");
		qo.sortTimestamp(true);
		qo.first(0);
		qo.maxSize(10000);
		List<MockObject> mL = qo.execute().getResult().assertNoError().list();
		assertEquals(2, mL.size());
		MockObject mnX = mL.get(0);
		assertEquals(mn2.getTarget(), mnX.getTarget());
		mnX = mL.get(1);
		assertEquals(mn1.getTarget(), mnX.getTarget());
	}

}
