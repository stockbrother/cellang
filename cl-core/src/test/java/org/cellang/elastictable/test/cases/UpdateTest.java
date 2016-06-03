/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package org.cellang.elastictable.test.cases;

import org.cellang.elastictable.test.MockObject;
import org.cellang.elastictable.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class UpdateTest extends TestBase {

	public void testUpdateByUid() {
		// create a node
		MockObject mn = new MockObject().forCreate(this.datas);
		mn.setProperty(MockObject.FIELD1, "value1");
		mn.setProperty(MockObject.FIELD2, "value2");
		mn.setProperty(MockObject.FIELD3, "value3");
		mn.save(true);
		String uid = mn.getUniqueId();
		{
			// get by uid
			MockObject mn2 = this.datas.getByUid(MockObject.class, uid, false);
			assertEquals(mn.getTarget(), mn2.getTarget());
			//
		}
		String field1NewValue = "value1-updated";
		this.datas.updateByUid(MockObject.class, uid, MockObject.FIELD1, field1NewValue);

		{
			// get by uid
			MockObject mn2 = this.datas.getByUid(MockObject.class, uid, false);
			assertEquals(field1NewValue, mn2.getProperty(MockObject.FIELD1));

			//
		}
	}

}
