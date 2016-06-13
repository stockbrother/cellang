package org.cellang.core.server.handler;

import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.server.AbstracHandler;
import org.cellang.core.server.Channel;
import org.cellang.core.server.ClientManager;
import org.cellang.core.server.MessageContext;
import org.cellang.core.server.Messages;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.elasticsearch.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1)Client send request to server for connection.<br>
 * 2)Server build an comet(underlying transfer protocol) and send the connection
 * id for further message transfer. <br>
 * 3)Client send clientIsReady to server.<br>
 * 
 * 4)Server assign an client id and terminal id for this client.Server send
 * serverIsReady to client. <br>
 * 
 * Question:<br>
 * the clientId looks like not useful: just binding client to channel,is ok?
 * 
 * @author wu
 *
 */
public class ClientIsReadyHandler extends AbstracHandler {
	public ClientIsReadyHandler(TableService ts) {
		super(ts);
	}

	private static final Logger LOG = LoggerFactory.getLogger(ClientIsReadyHandler.class);

	@Override
	public void handle(MessageContext t) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("handle");
		}
		MessageI msg = MessageSupport.newMessage(Messages.MSG_SERVER_IS_READY);
		Channel cn = t.getChannel();
		String clientId = UUIDUtil.randomStringUUID();
		Object client = new Object();// store client info(session).
		ClientManager.ME.putClient(clientId, client);
		msg.setPayload("clientId", clientId);

		msg.setPayload("terminalId", cn.getId());
		cn.sendMessage(msg);

		if (LOG.isDebugEnabled()) {
			LOG.debug("end handle");
		}
	}

}
