package org.cellang.commons.transfer.test.mock;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.cellang.commons.lang.Path;
import org.cellang.commons.transfer.ajax.AjaxMsg;
import org.cellang.commons.transfer.servlet.AjaxCometServlet;
import org.cellang.core.servlet.CellangServlet;
import org.cellang.core.util.ExceptionUtil;
import org.cellang.core.util.FileUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class ClientWithBrowserMock {
	ServletRunner servletRunner;
	ServletUnitClient servletClient;
	private String contextPath = "ajax";
	ExecutorService executor;
	public QueueAjaxMessageCallback queueCallback;

	public long timeoutForFirstMessage = 5 * 1000;

	public ClientWithBrowserMock(Class<? extends AjaxCometServlet> clazz) {
		File home = FileUtil.createTempDir("cl-test-home");
		
		this.queueCallback = new QueueAjaxMessageCallback();
		this.executor = Executors.newFixedThreadPool(2);
		servletRunner = new ServletRunner();
		Hashtable<String, String> paras = new Hashtable<String, String>();
		paras.put(AjaxCometServlet.PK_maxIdleTime, 60000 + "");
		paras.put(AjaxCometServlet.PK_timeoutForFirstMessage, this.timeoutForFirstMessage + "");
		paras.put(CellangServlet.PK_home, home.getAbsolutePath());//
		servletRunner.registerServlet(this.contextPath, clazz.getName(), paras);
		servletClient = servletRunner.newClient();
	}
	
	public void destroy(){
		this.servletRunner.shutDown();
		this.servletRunner = null;
		this.servletClient = null;
	}

	public String syncConnect() throws Exception {
		AjaxMsg amsg = new AjaxMsg(AjaxMsg.CONNECT);//
		this.asyncSend(amsg, null);
		AjaxMsg am = this.queueCallback.queue.poll(20, TimeUnit.SECONDS);
		Assert.assertNotNull("timeout to get response", am);
		System.out.println(am);//
		String sessionId = am.getProperty(AjaxMsg.PK_CONNECT_SESSION_ID);
		String pathS = am.getProperty(AjaxMsg.PK_PATH);
		Assert.assertEquals(AjaxMsg.CONNECT_SUCCESS, Path.valueOf(pathS));//
		Assert.assertNotNull(sessionId);//

		return sessionId;
	}

	public void syncClose(String sessionId) throws Exception {
		AjaxMsg amsg = new AjaxMsg(AjaxMsg.CLOSE);//
		this.asyncSend(amsg, sessionId);
		AjaxMsg am = this.queueCallback.queue.poll(2, TimeUnit.SECONDS);
		Assert.assertNotNull(am);
		System.out.println(am);//

		String pathS = am.getProperty(AjaxMsg.PK_PATH);
		Assert.assertEquals(AjaxMsg.CLOSE_SUCCESS, Path.valueOf(pathS));//
	}

	public void asyncSend(AjaxMsg amsg, String sessionId) {
		this.asyncSend(amsg, sessionId, this.queueCallback);//
	}

	public void asyncSend(AjaxMsg amsg, String sessionId, final AjaxMessageCallback callback) {
		JSONArray jsa = new JSONArray();
		JSONObject jso1 = new JSONObject();
		jso1.putAll(amsg.getAsMap());
		jsa.add(jso1);

		StringWriter sw = new StringWriter();
		try {
			jsa.writeJSONString(sw);
		} catch (IOException e) {
			throw ExceptionUtil.toRuntimeException(e);
		}
		String jsonS = sw.toString();
		System.out.println(jsonS);//

		ByteArrayInputStream bis = new ByteArrayInputStream(jsonS.getBytes());
		final PostMethodWebRequest request = new PostMethodWebRequest("http://any.host/" + this.contextPath, bis,
				"application/json; charset=utf-8");
		if (sessionId != null) {
			request.setHeaderField(AjaxCometServlet.HK_SESSION_ID, sessionId);
		}

		// request.setHeaderField("Content-Length", "" + len(text));
		Future<Boolean> future = this.executor.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				WebResponse rs = servletClient.getResponse(request);
				callback.onResponse(request, rs);
				return Boolean.TRUE;
			}

		});

	}

	public void asyncSendHeartBeat(String sessionId) {
		AjaxMsg amsg = new AjaxMsg(AjaxMsg.HEART_BEAT);
		this.asyncSend(amsg, sessionId);
	}

}
