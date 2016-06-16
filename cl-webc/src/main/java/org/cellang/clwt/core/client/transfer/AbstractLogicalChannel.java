/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package org.cellang.clwt.core.client.transfer;

import org.cellang.clwt.core.client.Console;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.codec.Codec;
import org.cellang.clwt.core.client.data.ErrorInfosData;
import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.event.ClientClosingEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.LogicalChannelBusyEvent;
import org.cellang.clwt.core.client.event.LogicalChannelCloseEvent;
import org.cellang.clwt.core.client.event.LogicalChannelErrorEvent;
import org.cellang.clwt.core.client.event.LogicalChannelFreeEvent;
import org.cellang.clwt.core.client.event.LogicalChannelMessageEvent;
import org.cellang.clwt.core.client.event.LogicalChannelOpenEvent;
import org.cellang.clwt.core.client.event.StateChangeEvent;
import org.cellang.clwt.core.client.lang.AbstractWebObject;
import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.lang.Handler;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.webc.main.client.Messages;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
/**
 * @author wu
 * 
 */
public abstract class AbstractLogicalChannel extends AbstractWebObject implements LogicalChannel {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(AbstractLogicalChannel.class);

	private Codec messageCodec;

	private boolean serverIsReady;

	private String clientId;

	private String terminalId;

	private MessageCacheI messageCache;

	private LogicalChannelFreeEvent lastFreeEvent;

	private LogicalChannelBusyEvent lastBusyEvent;

	private Console console = Console.getInstance();

	protected Address uri;

	protected String protocol;

	/**
	 * @param md
	 */
	public AbstractLogicalChannel(Container c, Address uri, MessageCacheI mc) {
		super(c);
		this.uri = uri;
		this.protocol = uri.getProtocol();
		this.messageCache = mc;
		this.messageCache.addHandler(new EventHandlerI<StateChangeEvent>() {

			@Override
			public void handle(StateChangeEvent t) {
				AbstractLogicalChannel.this.onMessageCacheUpdate(t);
			}
		});
		// TODo dispatching messageData from server.
		this.addHandler(LogicalChannelMessageEvent.TYPE, new EventHandlerI<LogicalChannelMessageEvent>() {

			@Override
			public void handle(LogicalChannelMessageEvent t) {
				if (Messages.MSG_SERVER_IS_READY.equals(t.getChannelMessageData().getPath())) {
					AbstractLogicalChannel.this.onServerIsReady(t);
				}
			}
		});

	}

	/**
	 * Apr 4, 2013
	 */
	protected void onMessageCacheUpdate(StateChangeEvent t) {

		if (this.messageCache.size() == 0) {
			this.lastBusyEvent = null;
			if (this.lastFreeEvent == null) {
				this.lastFreeEvent = new LogicalChannelFreeEvent(this);
				this.lastFreeEvent.dispatch();
			}
			// else ignore
		} else {

			this.lastFreeEvent = null;
			if (this.lastBusyEvent == null) {
				this.lastBusyEvent = new LogicalChannelBusyEvent(this);
				this.lastBusyEvent.dispatch();
			}
		}
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

		this.messageCache.start();
		this.getClient(true).addHandler(ClientClosingEvent.TYPE, new EventHandlerI<ClientClosingEvent>() {

			@Override
			public void handle(ClientClosingEvent t) {
				AbstractLogicalChannel.this.onClientClosing(t);
			}
		});
	}

	/**
	 * Apr 4, 2013
	 */
	protected void onClientClosing(ClientClosingEvent t) {
		this.close();
	}

	/**
	 * After clientIsReady, client send a message to server. Server will
	 * establish terminal and then server send the message to client.
	 * 
	 * @param e
	 */
	protected void onServerIsReady(MsgWrapper e) {
		MessageData md = e.getMessage();
		this.clientId = md.getString("clientId", true);
		this.terminalId = md.getString("terminalId", true);
		this.serverIsReady = true;
		new LogicalChannelOpenEvent(this).dispatch();
	}

	@Override
	public void open() {
		if (this.messageCodec == null) {
			this.messageCodec = this.getClient(true).getCodecFactory().getCodec(MessageData.class);
		}

	}

	protected void assertIsReady() {

		if (!this.isReady()) {
			throw new UiException(getShortName() + ",server is not ready");
		}

	}

	protected boolean isReady() {
		return this.isNativeSocketOpen() && this.serverIsReady;
	}

	protected abstract boolean isNativeSocketOpen();

	/*
	 * Dec 20, 2012
	 */
	@Override
	public void sendMessage(MessageData req) {
		// applevel message sending.
		this.assertIsReady();

		this.sendMessageDirect(req);

	}

	// do not add any additional header, sent the message directly.
	private void sendMessageDirect(final MessageData req) {
		//
		JSONValue js = (JSONValue) this.messageCodec.encode(req);
		String jsS = js.toString();
		this.messageCache.addMessage(req);// for later reference
		this.doSendMessage(jsS, new Handler<String>() {

			@Override
			public void handle(String t) {
				AbstractLogicalChannel.this.onSendFailure(req);
			}
		});
	}

	/**
	 * @param req
	 */
	protected void onSendFailure(MessageData req) {
		this.messageCache.removeMessage(req.getId());//
	}

	protected abstract void doSendMessage(String msg, Handler<String> onfailure);

	/**
	 * Called after the underlying protocol(comet) is established.
	 */
	protected void onConnected() {
		// wait server is ready
		LOG.info(getShortName() + " is open, clientIsReady send to server and waiting server is ready.");
		MessageData req = new MessageData(Messages.MSG_CLIENT_IS_READY);
		this.sendMessageDirect(req);

	}

	protected void onClosed(String code, String reason) {
		this.serverIsReady = false;
		this.clientId = null;
		this.terminalId = null;//
		LOG.info(getShortName() + " is closed, code:" + code + ",reason:" + reason);
		new LogicalChannelCloseEvent(this, code, reason).dispatch();

	}

	protected void onError(String msg) {
		LOG.error(getShortName() + ",error msg:" + msg, null);
		new LogicalChannelErrorEvent(this, msg).dispatch();

	}

	// a message from server side.
	protected void onRawMessage(String msg) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onRawMessage:" + msg);//
		}
		JSONValue jsonV = JSONParser.parseStrict(msg);
		MessageData md = (MessageData) this.messageCodec.decode(jsonV);
		String sid = md.getSourceId();
		if (sid != null) {
			MessageData req = this.messageCache.removeMessage(sid);
			if (req == null) {
				LOG.info(getShortName()
						+ ",request not found,may timeout or the source message is from other side,message:" + md);
			} else {
				md.setPayload(MessageData.PK_SOURCE, req);
			}
		}
		Path p = md.getPath();
		Path tp = LogicalChannelMessageEvent.TYPE.getAsPath();
		ErrorInfosData eis = md.getErrorInfos();
		if (eis.hasError()) {
			this.console.error(eis);
		}

		new LogicalChannelMessageEvent(this, md).dispatch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.endpoint.EndPointI#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return this.serverIsReady;

	}

	/*
	 * Jan 1, 2013
	 */
	@Override
	public void sendMessage(MsgWrapper req) {
		this.sendMessage(req.getTarget());//
	}

	private String getShortName() {
		return "endpoint(" + this.uri + ")";
	}

	@Override
	public Address getUri() {
		return uri;
	}

}
