/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
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
public class NotEqTest extends TestBase {

	public void testDeleteByUid() {
		MockObject mn1 = new MockObject().forCreate(this.datas);
		mn1.setProperty(MockObject.FIELD1, "value1");
		mn1.setProperty(MockObject.FIELD2, "value12");
		mn1.setProperty(MockObject.FIELD3, "value13");
		mn1.save(true);
		MockObject mn2 = new MockObject().forCreate(this.datas);
		mn2.setProperty(MockObject.FIELD1, "value1");
		mn2.setProperty(MockObject.FIELD2, "value22");
		mn2.setProperty(MockObject.FIELD3, "value23");
		mn2.save(true);
		MockObject mn3 = new MockObject().forCreate(this.datas);
		mn3.setProperty(MockObject.FIELD1, "value1");
		mn3.setProperty(MockObject.FIELD2, "value32");
		mn3.setProperty(MockObject.FIELD3, "value33");
		mn3.save(true);
		
		NodeSearchOperationI<MockObject> qo = this.datas.prepareNodeSearch(MockObject.class);
		qo.propertyEq(MockObject.FIELD1, "value1");
		qo.propertyNotEq(MockObject.FIELD2, "value12");
		qo.sortTimestamp(false);//

		List<MockObject> mL = qo.execute().getResult().assertNoError().list();
		assertEquals(2, mL.size());
		MockObject mnX = mL.get(0);
		assertEquals(mn2.getTarget(), mnX.getTarget());
		mnX = mL.get(1);
		assertEquals(mn3.getTarget(), mnX.getTarget());

	}

}
