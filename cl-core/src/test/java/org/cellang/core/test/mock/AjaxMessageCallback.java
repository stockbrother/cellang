package org.cellang.core.test.mock;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.cellang.commons.transfer.ajax.AjaxMsg;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class AjaxMessageCallback {

	private static final Logger LOG = LoggerFactory.getLogger(AjaxMessageCallback.class);

	public void onResponse(WebRequest request, WebResponse response) {
		List<AjaxMsg> msgL = null;
		try {

			Assert.assertNotNull(response);
			Assert.assertEquals("content type", "application/json", response.getContentType());
			Assert.assertEquals("utf-8", response.getCharacterSet());

			InputStream ris = response.getInputStream();
			Reader rreader = new InputStreamReader(ris);
			msgL = AjaxMsg.tryParseMsgArray(rreader);

		} catch (Exception e) {
			this.onFailure(e.getMessage(), e);
			return;
		}
		try {

			this.onSuccess(msgL);
		} catch (Exception e) {
			LOG.error("error when message processing", e);
		}

	}

	protected void onFailure(String message, Throwable t) {
		LOG.error(message, t);//
	}

	protected void onSuccess(List<AjaxMsg> msgL) {

	}

}
