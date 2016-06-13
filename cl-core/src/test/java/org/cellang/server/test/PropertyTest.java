package org.cellang.server.test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.server.DefaultCellangServer;
import org.cellang.core.server.MessageServer;
import org.cellang.core.server.Messages;
import org.cellang.core.util.FileUtil;
import org.junit.Assert;

import junit.framework.TestCase;

public class PropertyTest extends TestCase {

	public void test() {

		File home = FileUtil.createTempDir("cl-test-home");

		MessageServer server = new DefaultCellangServer(home);
		server.start();

		try {
			String owner1 = "owner1";
			String key11 = "key11";
			String value11 = "value11";

			String key12 = "key12";
			String value12 = "value12";
			//TODO add other types

			String owner2 = "owner2";
			String key21 = "key21";
			String value21 = "value21";

			String key22 = "key22";
			String value22 = "value22";

			{

				MessageI msg = MessageSupport.newMessage(Messages.PROPERTY_SAVE_REQ);
				HasProperties<Object> p11 = new MapProperties<Object>();
				p11.setProperty("owner", owner1);
				p11.setProperty("key", key11);
				p11.setProperty("value", value11);

				HasProperties<Object> p12 = new MapProperties<Object>();
				p12.setProperty("owner", owner1);
				p12.setProperty("key", key12);
				p12.setProperty("value", value12);

				HasProperties<Object> p21 = new MapProperties<Object>();
				p21.setProperty("owner", owner2);
				p21.setProperty("key", key21);
				p21.setProperty("value", value21);

				HasProperties<Object> p22 = new MapProperties<Object>();
				p22.setProperty("owner", owner2);
				p22.setProperty("key", key22);
				p22.setProperty("value", value22);

				List<HasProperties<Object>> pl = new ArrayList<HasProperties<Object>>();
				pl.add(p11);
				pl.add(p12);
				pl.add(p21);
				pl.add(p22);
				msg.setPayload(pl);//

				MessageI rmsg = server.process(msg);

				Assert.assertNotNull(rmsg);
				rmsg.assertNoError();

			}
			{
				MessageI msg = MessageSupport.newMessage(Messages.PROPERTY_GET_REQ);
				msg.setPayload("owner", owner1);

				MessageI res = server.process(msg);
				Assert.assertNotNull(res);
				res.assertNoError();
				List<HasProperties<Object>> pl = (List<HasProperties<Object>>) res.getPayload();
				Assert.assertNotNull(pl);
				Assert.assertEquals(2, pl.size());//
				// convert to map

				Map<String, Object> map = new HashMap<String, Object>();
				String key1 = (String) pl.get(0).getProperty("key");
				Object value1 = pl.get(0).getProperty("value");
				map.put(key1, value1);
				String key2 = (String) pl.get(1).getProperty("key");
				Object value2 = pl.get(1).getProperty("value");
				map.put(key2, value2);
				Assert.assertEquals(value11, map.get(key11));
				Assert.assertEquals(value12, map.get(key12));

			}
			// login

		} finally {
			server.shutdown();
		}
	}
}
