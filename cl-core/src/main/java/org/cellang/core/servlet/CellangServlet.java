package org.cellang.core.servlet;

import java.io.File;

import javax.servlet.ServletException;

import org.cellang.commons.json.Codec;
import org.cellang.commons.json.JsonCodecs;
import org.cellang.commons.lang.Path;
import org.cellang.commons.transfer.Comet;
import org.cellang.commons.transfer.CometListener;
import org.cellang.commons.transfer.servlet.AjaxCometServlet;
import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.server.CellangServer;
import org.cellang.core.server.Channel;
import org.cellang.core.server.DefaultCellangServer;
import org.cellang.core.server.MessageContext;
import org.cellang.elastictable.elasticsearch.UUIDUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellangServlet extends AjaxCometServlet implements CometListener {
	private static final Logger LOG = LoggerFactory.getLogger(CellangServlet.class);

	CellangServer server;

	JsonCodecs codecs;

	Codec messageCodec;

	private static final String CHANNEL0 = "CHANNEL0";
	
	public static final String PK_home = "home";

	@Override
	public void init() throws ServletException {
		super.init();
		
		String home = getInitParameter(PK_home, true);
		File homeDir = new File(home);
		
		this.codecs = new JsonCodecs();
		this.messageCodec = this.codecs.getCodec(MessageI.class);
		
		this.server = new DefaultCellangServer(homeDir);
		this.server.start();
		this.manager.addListener(this);
	}

	@Override
	public void destroy() {
		this.server.shutdown();
		super.destroy();
	}

	/**
	 * Comet is the underlying transfer layer session.<br>
	 * Channel is the app layer session,we call it channel;<br>
	 * Logically,it is possible to build multiple channel on the same comet.<br>
	 * Actually, here, we just map channel 1-1 to comet.<br>
	 */
	@Override
	public void onConnect(Comet ws) {

		String id = UUIDUtil.randomStringUUID();
		CometChannel ct = new CometChannel(id, ws, this.messageCodec);

		ws.setAttribute(CHANNEL0, ct);// bind comet session with CHANNEL.
		// TODO make sure this clientIsReady is only from client?
		// MessageI reqMsg =
		// MessageSupport.newMessage(Messages.MSG_CLIENT_IS_READY);
		// this.doService(reqMsg, ws);//

	}

	protected void doService(MessageI req, Comet ct) {
		Channel channel = this.getChannel0(ct);
		
		MessageI res = this.server.process(req);
		
		channel.sendMessage(res);

	}

	private CometChannel getChannel0(Comet ct) {
		return (CometChannel) ct.getAttribute(CHANNEL0);
	}

	@Override
	public void onMessage(Comet ws, String ms) {
		JSONArray jso = (JSONArray) JSONValue.parse(ms);
		MessageI reqMsg = (MessageI) this.messageCodec.decode(jso);
		this.doService(reqMsg, ws);
	}

	@Override
	public void onException(Comet ws, Throwable t) {
		LOG.error("", t);
	}

	@Override
	public void onClose(Comet ws, int statusCode, String reason) {
		LOG.info("comet closed,stateCode:" + statusCode + ",reason:" + reason);//
	}

}
