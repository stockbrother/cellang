package org.cellang.server.test;

import java.io.File;

import org.cellang.core.entity.AccountEntity;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.EntitySessionFactoryImpl;
import org.cellang.core.entity.GetSingleEntityOp;
import org.cellang.core.util.FileUtil;

import junit.framework.TestCase;

public class EntityServiceTest extends TestCase {

	public void test() {
		File dbHome = FileUtil.createTempDir("cl-test-home");
		dbHome = new File(dbHome, "db");
		String dbName = "h2db";
		EntitySessionFactory esf = EntitySessionFactoryImpl.newInstance(dbHome, dbName, new EntityConfigFactory());
		GetSingleEntityOp<AccountEntity> getOp = new GetSingleEntityOp<>();

		EntitySession es = esf.openSession();

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

		EntitySessionFactory es2 = EntitySessionFactoryImpl.newInstance(dbHome, dbName, new EntityConfigFactory());

		AccountEntity aeX = getOp.set(AccountEntity.class, "email", email).execute(es2);
		assertNotNull(aeX);

	}
}
