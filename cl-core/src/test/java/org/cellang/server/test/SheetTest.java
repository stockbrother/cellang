package org.cellang.server.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.rowobject.SheetRowObject;
import org.cellang.core.server.DefaultCellangServer;
import org.cellang.core.server.MessageServer;
import org.cellang.core.server.Messages;
import org.cellang.core.util.FileUtil;
import org.cellang.elastictable.TableRow;
import org.junit.Assert;

import junit.framework.TestCase;

public class SheetTest extends TestCase {

	public void test() {

		File home = FileUtil.createTempDir("cl-test-home");

		MessageServer server = new DefaultCellangServer(home);
		server.start();

		try {

			String sheetName = "sheet1";
			String sheetOwner = "owner1";
			String[] row0 = new String[] { "00", "01", "02" };
			String[] row1 = new String[] { "10", "11", "12" };
			String[] row2 = new String[] { "20", "21", "22" };
			String[] row3 = new String[] { "30", "31", "32" };

			{

				MessageI msg = MessageSupport.newMessage(Messages.SHEET_SAVE_REQ);

				List<List<String>> cll = new ArrayList<List<String>>();
				cll.add(Arrays.asList(row0));
				cll.add(Arrays.asList(row1));
				cll.add(Arrays.asList(row2));
				cll.add(Arrays.asList(row3));


				HasProperties<Object> sheet = new MapProperties<Object>();
				sheet.setProperty("owner", sheetOwner);//
				sheet.setProperty("name", sheetName);//
				sheet.setProperty("cellTable", cll);
				msg.setPayload(sheet);
				MessageI rmsg = server.process(msg);

				Assert.assertNotNull(rmsg);
				rmsg.assertNoError();
			}
			String sheetId = null;
			{
				MessageI msg = MessageSupport.newMessage(Messages.SHEET_LIST_REQ);
				msg.setPayload("owner", sheetOwner);
				MessageI res = server.process(msg);
				res.assertNoError();
				List<HasProperties<Object>> sl = (List<HasProperties<Object>>) res.getPayload();
				Assert.assertNotNull(sl);//
				Assert.assertEquals(1, sl.size());
				Assert.assertEquals(sheetName, sl.get(0).getProperty(SheetRowObject.NAME));
				sheetId = (String) sl.get(0).getProperty(TableRow.PK_ID);
				Assert.assertNotNull(sheetId);//
			}
			{
				MessageI msg = MessageSupport.newMessage(Messages.SHEET_GET_REQ);
				msg.setPayload("sheetId", sheetId);

				MessageI res = server.process(msg);
				Assert.assertNotNull(res);
				res.assertNoError();

				List<HasProperties<Object>> pl = (List<HasProperties<Object>>) res.getPayload();
				Assert.assertNotNull(pl);
				Assert.assertEquals(1, pl.size());
				Assert.assertEquals(sheetOwner, pl.get(0).getProperty("owner"));
				Assert.assertEquals(sheetName, pl.get(0).getProperty("name"));
				List<List<String>> cll = (List<List<String>>)pl.get(0).getProperty("cellTable");
				Assert.assertNotNull(cll);
				Assert.assertEquals(4, cll.size());
				Assert.assertArrayEquals(row0, cll.get(0).toArray(new String[0]));
				Assert.assertArrayEquals(row1, cll.get(1).toArray(new String[0]));
				Assert.assertArrayEquals(row2, cll.get(2).toArray(new String[0]));
				Assert.assertArrayEquals(row3, cll.get(3).toArray(new String[0]));
				
			}
			// login

		} finally {
			server.shutdown();
		}
	}
}
