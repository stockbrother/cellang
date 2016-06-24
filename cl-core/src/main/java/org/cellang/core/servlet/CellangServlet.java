package org.cellang.core.servlet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.cellang.commons.json.Codec;
import org.cellang.commons.json.JsonCodecs;
import org.cellang.commons.transfer.Comet;
import org.cellang.commons.transfer.CometListener;
import org.cellang.commons.transfer.servlet.AjaxCometServlet;
import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.lang.MessageI;
import org.cellang.core.server.Channel;
import org.cellang.core.server.ChannelProvider;
import org.cellang.core.server.DefaultCellangServer;
import org.cellang.core.server.MessageServer;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellangServlet extends AjaxCometServlet implements CometListener,ChannelProvider {
	private static final Logger LOG = LoggerFactory.getLogger(CellangServlet.class);

	MessageServer server;

	JsonCodecs codecs;

	Codec messageCodec;

	private static final String CHANNEL0 = "CHANNEL0";
	
	public static final String PK_home = "home";
	
	private Map<String,Channel> channelMap  =new HashMap<String,Channel>();

	@Override
	public void init() throws ServletException {
		super.init();
		
		String home = getInitParameter(PK_home, true);
		File homeDir = new File(home);
		
		this.codecs = new JsonCodecs();
		this.messageCodec = this.codecs.getCodec(MessageI.class);
		
		this.server = new DefaultCellangServer(homeDir,this);
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
		this.channelMap.put(id, ct);//
		ws.setAttribute(CHANNEL0, id);// bind comet session with CHANNEL.
		// TODO make sure this clientIsReady is only from client?
		// MessageI reqMsg =
		// MessageSupport.newMessage(Messages.MSG_CLIENT_IS_READY);
		// this.doService(reqMsg, ws);//

	}


	@Override
	public void onMessage(Comet ws, String ms) {
		String channelId= (String) ws.getAttribute(CHANNEL0);
		JSONArray jso = (JSONArray) JSONValue.parse(ms);
		MessageI reqMsg = (MessageI) this.messageCodec.decode(jso);
		reqMsg.setHeader(MessageI.HK_CHANNEL, channelId);//
		this.server.process(reqMsg);		
	}

	@Override
	public void onException(Comet ws, Throwable t) {
		LOG.error("", t);
	}

	@Override
	public void onClose(Comet ws, int statusCode, String reason) {
		LOG.info("comet closed,stateCode:" + statusCode + ",reason:" + reason);//
	}

	@Override
	public Channel getChannel(String id) {
		return this.channelMap.get(id);//
	}

}
