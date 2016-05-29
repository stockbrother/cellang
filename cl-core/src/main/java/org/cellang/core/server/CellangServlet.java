package org.cellang.core.server;

import javax.servlet.ServletException;

import org.cellang.commons.json.Codec;
import org.cellang.commons.json.JsonCodecs;
import org.cellang.commons.transfer.Comet;
import org.cellang.commons.transfer.CometListener;
import org.cellang.commons.transfer.ajax.AjaxCometServlet;
import org.cellang.core.lang.MessageI;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class CellangServlet extends AjaxCometServlet implements CometListener {

	CellangServer server;

	JsonCodecs codecs;

	Codec messageCodec;

	@Override
	public void init() throws ServletException {
		super.init();
		this.codecs = new JsonCodecs();
		this.messageCodec = this.codecs.getCodec(MessageI.class);
		this.server = new DefaultCellangServer();
		this.manager.addListener(this);
	}

	@Override
	public void onConnect(Comet ws) {

	}

	@Override
	public void onMessage(Comet ws, String ms) {
		JSONObject jso = (JSONObject) JSONValue.parse(ms);
		MessageI reqMsg = (MessageI) this.messageCodec.decode(jso);

		MessageContext mc = new MessageContext(reqMsg);
		this.server.service(mc);
	}

	@Override
	public void onException(Comet ws, Throwable t) {

	}

	@Override
	public void onClose(Comet ws, int statusCode, String reason) {

	}

}
