/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package com.fs.dataservice.impl.test.cases;

import com.fs.dataservice.impl.test.MockNode;
import com.fs.dataservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class UpdateTest extends TestBase {

	public void testUpdateByUid() {
		// create a node
		MockNode mn = new MockNode().forCreate(this.datas);
		mn.setProperty(MockNode.FIELD1, "value1");
		mn.setProperty(MockNode.FIELD2, "value2");
		mn.setProperty(MockNode.FIELD3, "value3");
		mn.save(true);
		String uid = mn.getUniqueId();
		{
			// get by uid
			MockNode mn2 = this.datas.getByUid(MockNode.class, uid, false);
			assertEquals(mn.getTarget(), mn2.getTarget());
			//
		}
		String field1NewValue = "value1-updated";
		this.datas.updateByUid(MockNode.class, uid, MockNode.FIELD1, field1NewValue);

		{
			// get by uid
			MockNode mn2 = this.datas.getByUid(MockNode.class, uid, false);
			assertEquals(field1NewValue, mn2.getProperty(MockNode.FIELD1));

			//
		}
	}

}
