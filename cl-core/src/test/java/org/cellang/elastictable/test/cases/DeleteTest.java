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
public class DeleteTest extends TestBase {

	public void testDeleteByUid() {
		MockObject mn = new MockObject().forCreate(this.datas);
		mn.setProperty(MockObject.FIELD1, "value1");
		mn.setProperty(MockObject.FIELD2, "value2");
		mn.setProperty(MockObject.FIELD3, "value3");
		mn.save(true);
		String uid = mn.getUniqueId();
		MockObject mn2 = this.datas.getByUid(MockObject.class, uid, false);

		assertEquals(mn.getTarget(), mn2.getTarget());

		this.datas.deleteByUid(MockObject.class, uid);

		MockObject mn3 = this.datas.getByUid(MockObject.class, uid, false);
		assertNull("delete not work.", mn3);
	}

	public void testDeleteById() {
		String id = "001";
		MockObject mn1 = new MockObject().forCreate(this.datas);
		mn1.setId(id);
		mn1.setProperty(MockObject.FIELD1, "value1");
		mn1.setProperty(MockObject.FIELD2, "value2");
		mn1.setProperty(MockObject.FIELD3, "value3");
		
		mn1.save(true);

		MockObject mn2 = new MockObject().forCreate(this.datas);
		mn2.setId(id);
		mn2.setProperty(MockObject.FIELD1, "value1");
		mn2.setProperty(MockObject.FIELD2, "value2");
		mn2.setProperty(MockObject.FIELD3, "value3");
		
		mn2.save(true);
		int count = this.datas.deleteById(MockObject.class, id);

		assertEquals("there should be 2 deleted by id:" + id, 2, count);
	}
}
