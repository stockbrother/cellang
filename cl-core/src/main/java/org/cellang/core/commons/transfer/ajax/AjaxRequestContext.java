/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.core.commons.transfer.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * @author wu
 * 
 */
public class AjaxRequestContext {

	@Deprecated
	// move to servlet.
	public int timeoutForSession;

	public int timeoutForFirstMessage;// 2 mins.

	public int timeoutForMoreMessage = 1;// should be short enough.

	public HttpServletRequest req;
	public HttpServletResponse res;

	public int totalMessages = 0;

	public AjaxComet as;

	/**
	 * @param req2
	 * @param res2
	 */
	public AjaxRequestContext(int timeoutForSession, int timeoutForFirstMessage, AjaxComet as,
			HttpServletRequest req, HttpServletResponse res2) {
		this.req = req;
		this.res = res2;
		this.as = as;
		this.timeoutForSession = timeoutForSession;
		this.timeoutForFirstMessage = timeoutForFirstMessage;
	}

	/**
	 * May 8, 2013
	 */
	public PrintWriter getWriter() {
		//
		try {
			return this.res.getWriter();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * May 13, 2013
	 */
	public void writeCloseSuccess() {
		//
		AjaxMsg msg = new AjaxMsg(AjaxMsg.CLOSE.getSubPath("success"));
		this.write(msg);
	}

	public void writeError(String code, String msg) {
		AjaxMsg am = new AjaxMsg(AjaxMsg.ERROR);
		am.setProperty(AjaxMsg.PK_ERROR_CODE, code);
		am.setProperty(AjaxMsg.PK_ERROR_MSG, msg);
		this.write(am);
	}

	/**
	 * May 8, 2013
	 */
	public void write(AjaxMsg msg) {
		Writer out = this.getWriter();

		try {
			if (this.totalMessages > 0) {
				out.write(",");
			}
			JSONObject json = new JSONObject();
			json.putAll(msg.getAsMap());

			json.writeJSONString(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		this.totalMessages++;

	}

	public void tryFetchMessage() {

		if (this.as == null) {
			// make the response.
			return;
		}
		while (true) {
			long timeout = 100;
			if (this.totalMessages == 0) {
				// wait a long time for the first message
				timeout = this.timeoutForFirstMessage;
			} else {
				// wait a short time for more messages.
				timeout = this.timeoutForMoreMessage;//
			}
			AjaxMsg msg = null;
			try {
				msg = this.as.getQueue().poll(timeout, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {

			}

			if (msg == null || msg.isInterruptMsg()) {// timeout to get the new
														// message, there is no
				// more message
				// any way, break and make the response.
				break;
			}
			// write this message to response
			this.write(msg);

		}

	}

	public void writeMessageStart() {
		Writer writer = this.getWriter();
		try {
			writer.write("[");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void writeMessageEnd() {
		Writer writer = this.getWriter();
		try {
			writer.write("]");
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
