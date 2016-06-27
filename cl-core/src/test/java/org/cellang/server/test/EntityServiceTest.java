package org.cellang.server.test;

import java.io.File;

import org.cellang.core.entity.AccountEntity;
import org.cellang.core.entity.EntityService;
import org.cellang.core.util.FileUtil;

import junit.framework.Assert;
import junit.framework.TestCase;

public class EntityServiceTest extends TestCase {

	public void test() {
		File dbHome = FileUtil.createTempDir("cl-test-home");
		dbHome = new File(dbHome, "db");
		String dbName = "h2db";
		EntityService es = EntityService.newInstance(dbHome, dbName);
		String email = "email1";
		String nick = "nick1";
		String password = "password1";
		AccountEntity ae = new AccountEntity();
		ae.setId(email);//
		ae.setEmail(email);
		ae.setNick(nick);
		ae.setPassword(password);

		es.save(ae);
		Exception exp = null;
		try {
			es.save(ae);
		} catch (Exception e) {
			exp = e;
		}

		assertNotNull("duplicated exception not raised.", exp);

		AccountEntity ae2 = es.getSingle(AccountEntity.class, "email", email);
		assertNotNull(ae2);
		assertEquals(email, ae2.getId());
		assertEquals(email, ae2.getEmail());
		assertEquals(nick, ae2.getNick());
		assertEquals(password, ae2.getPassword());

		es.close();

		EntityService es2 = EntityService.newInstance(dbHome, dbName);
		AccountEntity aeX = es2.getSingle(AccountEntity.class, "email", email);
		assertNotNull(aeX);

	}
}
