package org.cellang.core.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.cellang.commons.lang.Path;
import org.cellang.commons.transfer.ajax.AjaxCometServlet;
import org.cellang.commons.transfer.ajax.AjaxMsg;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import junit.framework.TestCase;

public class AjaxCometServletTest extends TestCase {

	public AjaxCometServletTest() {
	}

	ServletRunner servletRunner;
	ServletUnitClient servletClient;
	private String contextPath = "ajax";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		servletRunner = new ServletRunner();
		Hashtable<String, String> paras = new Hashtable<String, String>();
		paras.put(AjaxCometServlet.PK_maxIdleTime, "60000");
		paras.put(AjaxCometServlet.PK_timeoutForFirstMessage, "10000");
		servletRunner.registerServlet(this.contextPath, TestAjaxCometServlet.class.getName(), paras);
		servletClient = servletRunner.newClient();
	}

	protected String connect() throws Exception {
		AjaxMsg amsg = new AjaxMsg(AjaxMsg.CONNECT);//
		List<AjaxMsg> amL = this.send(amsg, null);
		Assert.assertEquals(1, amL.size());
		AjaxMsg am = amL.get(0);
		System.out.println(am);//
		String sessionId = am.getProperty(AjaxMsg.PK_CONNECT_SESSION_ID);
		String pathS = am.getProperty(AjaxMsg.PK_PATH);
		Assert.assertEquals(AjaxMsg.CONNECT_SUCCESS, Path.valueOf(pathS));//
		Assert.assertNotNull(sessionId);//

		return sessionId;
	}

	protected void close(String sessionId) throws Exception {
		AjaxMsg amsg = new AjaxMsg(AjaxMsg.CLOSE);//
		List<AjaxMsg> amL = this.send(amsg, sessionId);
		Assert.assertEquals(1, amL.size());
		AjaxMsg am = amL.get(0);
		System.out.println(am);//

		String pathS = am.getProperty(AjaxMsg.PK_PATH);
		Assert.assertEquals(AjaxMsg.CLOSE_SUCCESS, Path.valueOf(pathS));//
	}

	protected List<AjaxMsg> send(AjaxMsg amsg, String sessionId) throws Exception {
		JSONArray jsa = new JSONArray();
		JSONObject jso1 = new JSONObject();
		jso1.putAll(amsg.getAsMap());
		jsa.add(jso1);

		StringWriter sw = new StringWriter();
		jsa.writeJSONString(sw);
		String jsonS = sw.toString();
		System.out.println(jsonS);//

		ByteArrayInputStream bis = new ByteArrayInputStream(jsonS.getBytes());
		PostMethodWebRequest request = new PostMethodWebRequest("http://any.host/" + this.contextPath, bis,
				"application/json; charset=utf-8");
		if (sessionId != null) {
			request.setHeaderField(AjaxCometServlet.HK_SESSION_ID, sessionId);
		}

		// request.setHeaderField("Content-Length", "" + len(text));
		WebResponse response = servletClient.getResponse(request);
		Assert.assertNotNull(response);
		Assert.assertEquals("content type", "application/json", response.getContentType());
		Assert.assertEquals("utf-8", response.getCharacterSet());

		InputStream ris = response.getInputStream();
		Reader rreader = new InputStreamReader(ris);

		List<AjaxMsg> rt = AjaxMsg.tryParseMsgArray(rreader);
		return rt;

	}

	public void testSimple() throws Exception {
		String sessionId = this.connect();
		try {
			AjaxMsg amsg = new AjaxMsg(AjaxMsg.MESSAGE);
			amsg.setProperty(AjaxMsg.PK_TEXTMESSAGE, "hello!");

			this.send(amsg, sessionId);
			TestAjaxCometServlet.TestEvent evt = TestAjaxCometServlet.events.poll(100, TimeUnit.MILLISECONDS);
			Assert.assertNotNull("message not received", evt);
			Assert.assertNotNull("no session id", evt.cometId);
			Assert.assertEquals("message not expected.", "hello!", evt.message);
		} finally {
			this.close(sessionId);
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

	}

}
