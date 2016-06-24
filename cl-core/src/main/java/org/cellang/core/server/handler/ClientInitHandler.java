package org.cellang.core.server.handler;

import java.util.UUID;

import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.server.AbstracHandler;
import org.cellang.core.server.Channel;
import org.cellang.core.server.ClientManager;
import org.cellang.core.server.MessageContext;
import org.cellang.core.server.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * After serverIsReady, a channel(between client and server) is established. <br>
 * Client use this channel for communication.And client ask to be
 * initialized,read data from server.
 * 
 * @author wu
 *
 */
public class ClientInitHandler extends AbstracHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(ClientInitHandler.class);

	public ClientInitHandler() {
		super();
	}


	@Override
	public void handle(MessageContext t) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("handle");
		}
		MessageI msg = MessageSupport.newMessage(Messages.RES_CLIENT_INIT_SUCCESS);
		String preferedLocale = (String) msg.getPayload("preferedLocale");
		Channel cn = t.getChannel();
		String clientId = UUID.randomUUID().toString();
		Object client = new Object();// store client info(session).
		ClientManager.ME.putClient(clientId, client);
		msg.setPayload("clientId", clientId);

		msg.setPayload("parameters", this.clientParameters());

		msg.setPayload("localized", this.localized());

		cn.sendMessage(msg);

		if (LOG.isDebugEnabled()) {
			LOG.debug("end handle");
		}
	}

	public HasProperties<Object> localized() {
		MapProperties<Object> rt = new MapProperties<Object>();
		return rt;
	}

	public HasProperties<Object> clientParameters() {
		MapProperties<Object> rt = new MapProperties<Object>();

		rt.setProperty("messageQueryLimit", "15");
		rt.setProperty("expQueryLimit", "15");
		rt.setProperty("contactUrl", "/contact");
		rt.setProperty("aboutUsUrl", "/about-us");
		// TODO make sure that it less than the comet session expire time.
		rt.setProperty("cometHeartBeatIntervalMs", "290000");
		// http://127.0.0.1:8888/home.html?logLevel=WARNING&fs.logLevel=INFO&gwt.codesvr=127.0.0.1:9997
		rt.setProperty("rootUrl", "http://127.0.0.1:8888/home.html?logLevel=WARNING&fs.logLevel=INFO");
		rt.setProperty("textInputLengthLimit", "1024");
		rt.setProperty("imagesUri", "$client.imagesUri");
		return rt;
	}

}
