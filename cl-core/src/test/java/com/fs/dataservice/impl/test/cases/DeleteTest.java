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
public class DeleteTest extends TestBase {

	public void testDeleteByUid() {
		MockNode mn = new MockNode().forCreate(this.datas);
		mn.setProperty(MockNode.FIELD1, "value1");
		mn.setProperty(MockNode.FIELD2, "value2");
		mn.setProperty(MockNode.FIELD3, "value3");
		mn.save(true);
		String uid = mn.getUniqueId();
		MockNode mn2 = this.datas.getByUid(MockNode.class, uid, false);

		assertEquals(mn.getTarget(), mn2.getTarget());

		this.datas.deleteByUid(MockNode.class, uid);

		MockNode mn3 = this.datas.getByUid(MockNode.class, uid, false);
		assertNull("delete not work.", mn3);
	}

	public void testDeleteById() {
		String id = "001";
		MockNode mn1 = new MockNode().forCreate(this.datas);
		mn1.setId(id);
		mn1.setProperty(MockNode.FIELD1, "value1");
		mn1.setProperty(MockNode.FIELD2, "value2");
		mn1.setProperty(MockNode.FIELD3, "value3");
		
		mn1.save(true);

		MockNode mn2 = new MockNode().forCreate(this.datas);
		mn2.setId(id);
		mn2.setProperty(MockNode.FIELD1, "value1");
		mn2.setProperty(MockNode.FIELD2, "value2");
		mn2.setProperty(MockNode.FIELD3, "value3");
		
		mn2.save(true);
		int count = this.datas.deleteById(MockNode.class, id);

		assertEquals("there should be 2 deleted by id:" + id, 2, count);
	}
}
