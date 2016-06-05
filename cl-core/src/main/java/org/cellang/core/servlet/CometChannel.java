package org.cellang.core.servlet;

import org.cellang.commons.json.Codec;
import org.cellang.commons.transfer.Comet;
import org.cellang.core.lang.MessageI;
import org.cellang.core.server.Channel;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CometChannel extends Channel {
	private static final Logger LOG = LoggerFactory.getLogger(CometChannel.class);
	private Comet comet;
	private Codec messageCodec;

	public CometChannel(Comet ws, Codec messageCodec) {
		this.comet = ws;
		this.messageCodec = messageCodec;
	}

	@Override
	public void sendMessage(MessageI msg) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sendMessage:" + msg);
		}
		JSONArray jo = (JSONArray) this.messageCodec.encode(msg);
		String json = jo.toJSONString();
		comet.sendMessage(json);//
		if (LOG.isDebugEnabled()) {
			LOG.debug("end sendMessage");
		}
	}

}
