/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package org.cellang.webframework.client.transferpoint;

import org.cellang.webframework.client.CodecI;
import org.cellang.webframework.client.Console;
import org.cellang.webframework.client.Container;
import org.cellang.webframework.client.MessageCacheI;
import org.cellang.webframework.client.WebException;
import org.cellang.webframework.client.data.ErrorInfosData;
import org.cellang.webframework.client.data.MessageData;
import org.cellang.webframework.client.data.ObjectPropertiesData;
import org.cellang.webframework.client.data.PropertiesData;
import org.cellang.webframework.client.event.ClientClosingEvent;
import org.cellang.webframework.client.event.EndpointBondEvent;
import org.cellang.webframework.client.event.EndpointBusyEvent;
import org.cellang.webframework.client.event.EndpointCloseEvent;
import org.cellang.webframework.client.event.EndpointErrorEvent;
import org.cellang.webframework.client.event.EndpointFreeEvent;
import org.cellang.webframework.client.event.EndpointMessageEvent;
import org.cellang.webframework.client.event.EndpointOpenEvent;
import org.cellang.webframework.client.event.EndpointUnbondEvent;
import org.cellang.webframework.client.event.Event.EventHandlerI;
import org.cellang.webframework.client.event.StateChangeEvent;
import org.cellang.webframework.client.lang.AbstractWebObject;
import org.cellang.webframework.client.lang.Address;
import org.cellang.webframework.client.lang.Handler;
import org.cellang.webframework.client.lang.Path;
import org.cellang.webframework.client.logger.WebLoggerFactory;
import org.cellang.webframework.client.logger.WebLogger;
import org.cellang.webframework.client.message.MessageDataWrapper;
import org.cellang.webframework.client.message.MessageDispatcherI;
import org.cellang.webframework.client.message.MessageHandlerI;

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
public abstract class AbstractTransferPoint extends AbstractWebObject implements TransferPoint {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(AbstractTransferPoint.class);

	private CodecI messageCodec;

	private boolean serverIsReady;

	private String clientId;

	private String terminalId;

	private ObjectPropertiesData userInfo;

	private MessageCacheI messageCache;

	private EndpointFreeEvent lastFreeEvent;

	private EndpointBusyEvent lastBusyEvent;

	private Console console = Console.getInstance();

	protected Address uri;

	protected String protocol;

	/**
	 * @param md
	 */
	public AbstractTransferPoint(Container c, Address uri, MessageDispatcherI md, MessageCacheI mc) {
		super(c);
		this.uri = uri;
		this.protocol = uri.getProtocol();
		this.messageCache = mc;
		this.messageCache.addHandler(new EventHandlerI<StateChangeEvent>() {

			@Override
			public void handle(StateChangeEvent t) {
				AbstractTransferPoint.this.onMessageCacheUpdate(t);
			}
		});
		this.addHandler(
				EndpointMessageEvent.TYPE.getAsPath().concat(
						Path.valueOf("/control/status/serverIsReady", '/')),
				new MessageHandlerI<MessageDataWrapper>() {

					@Override
					public void handle(MessageDataWrapper t) {
						AbstractTransferPoint.this.onServerIsReady(t);
					}
				});
		MessageHandlerI<MessageDataWrapper> bindingMH = new MessageHandlerI<MessageDataWrapper>() {

			@Override
			public void handle(MessageDataWrapper t) {
				AbstractTransferPoint.this.onBindingSuccess(t);
			}
		};
		// TODO move to SPI active method.
		this.addHandler(Path.valueOf("/endpoint/message/terminal/auth/success"), bindingMH);
		this.addHandler(Path.valueOf("/endpoint/message/terminal/binding/success"), bindingMH);

		MessageHandlerI<MessageDataWrapper> unBindingMH = new MessageHandlerI<MessageDataWrapper>() {

			@Override
			public void handle(MessageDataWrapper t) {
				AbstractTransferPoint.this.onUnbindingSuccess(t);
			}
		};
		this.addHandler(Path.valueOf("/endpoint/message/terminal/unbinding/success"), unBindingMH);

	}

	/**
	 * Apr 4, 2013
	 */
	protected void onMessageCacheUpdate(StateChangeEvent t) {

		if (this.messageCache.size() == 0) {
			this.lastBusyEvent = null;
			if (this.lastFreeEvent == null) {
				this.lastFreeEvent = new EndpointFreeEvent(this);
				this.lastFreeEvent.dispatch();
			}
			// else ignore
		} else {

			this.lastFreeEvent = null;
			if (this.lastBusyEvent == null) {
				this.lastBusyEvent = new EndpointBusyEvent(this);
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
				AbstractTransferPoint.this.onClientClosing(t);
			}
		});
	}

	/**
	 * Apr 4, 2013
	 */
	protected void onClientClosing(ClientClosingEvent t) {
		this.close();
	}

	protected void onServerIsReady(MessageDataWrapper e) {
		MessageData md = e.getMessage();
		this.clientId = md.getString("clientId", true);
		this.terminalId = md.getString("terminalId", true);
		this.serverIsReady = true;
		new EndpointOpenEvent(this).dispatch();
	}

	@Override
	public void open() {
		if (this.messageCodec == null) {
			this.messageCodec = this.getClient(true).getCodecFactory().getCodec(MessageData.class);
		}

	}

	protected void assertIsReady() {

		if (!this.isReady()) {
			throw new WebException(getShortName() + ",server is not ready");
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

		if (this.userInfo != null) {
			req.setHeader("sessionId", this.getSessionId());//
		}

		req.setHeader("_resonse_address", "tid://" + this.terminalId);

		this.sendMessageDirect(req);

	}

	private void sendMessageDirect(final MessageData req) {
		//
		JSONValue js = (JSONValue) this.messageCodec.encode(req);
		String jsS = js.toString();
		this.messageCache.addMessage(req);// for later reference
		this.doSendMessage(jsS, new Handler<String>() {

			@Override
			public void handle(String t) {
				AbstractTransferPoint.this.onSendFailure(req);
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
		LOG.info(getShortName() + " is open, waiting server is ready.");
		MessageData req = new MessageData("/control/status/clientIsReady");
		this.sendMessageDirect(req);

	}

	protected void onClosed(String code, String reason) {
		this.serverIsReady = false;
		this.clientId = null;
		this.terminalId = null;//
		LOG.info(getShortName() + " is closed, code:" + code + ",reason:" + reason);
		new EndpointCloseEvent(this, code, reason).dispatch();

	}

	protected void onError(String msg) {
		LOG.error(getShortName() + ",error msg:" + msg, null);
		new EndpointErrorEvent(this, msg).dispatch();

	}

	protected void onMessage(String msg) {
		JSONValue jsonV = JSONParser.parseStrict(msg);
		MessageData md = (MessageData) this.messageCodec.decode(jsonV);
		String sid = md.getSourceId();
		if (sid != null) {
			MessageData req = this.messageCache.removeMessage(sid);
			if (req == null) {
				LOG.info(getShortName()
						+ ",request not found,may timeout or the source message is from other side,message:"
						+ md);
			} else {
				md.setPayload(MessageData.PK_SOURCE, req);
			}
		}
		Path p = md.getPath();
		Path tp = EndpointMessageEvent.TYPE.getAsPath();
		ErrorInfosData eis = md.getErrorInfos();
		if (eis.hasError()) {
			this.console.error(eis);
		}
		md.setHeader(MessageData.HK_PATH, tp.concat(p).toString());
		new EndpointMessageEvent(this, md).dispatch();
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

	@Override
	public void auth(PropertiesData<Object> pts) {
		MessageData req = new MessageData("/terminal/auth");
		req.setPayloads(pts);
		this.sendMessage(req);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public boolean isBond() {
		//
		return this.userInfo != null;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public String getSessionId() {
		//
		return this.userInfo.getString("sessionId", true);
	}

	/**
	 * Dec 23, 2012
	 */
	public void onBindingSuccess(MessageDataWrapper evt) {
		MessageData md = evt.getTarget();
		LOG.info(getShortName() + ",onBindingSuccess:" + md);
		this.userInfo = new ObjectPropertiesData();
		String sid = md.getString("sessionId", true);
		this.userInfo.setProperties(md.getPayloads());

		new EndpointBondEvent(this, this.getSessionId()).dispatch();
	}

	public void onUnbindingSuccess(MessageDataWrapper evt) {
		this.userInfo = null;
		new EndpointUnbondEvent(this).dispatch();
	}

	/*
	 * Jan 1, 2013
	 */
	@Override
	public void sendMessage(MessageDataWrapper req) {
		this.sendMessage(req.getTarget());//
	}

	private String getShortName() {
		return "endpoint(" + this.uri + ")";
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void logout() {
		//
		if (!this.isBond()) {
			throw new WebException(getShortName() + " not bound yet.");
		}

		MessageData req = new MessageData("/terminal/unbinding");
		req.setPayload("sessionId", this.getSessionId());

		this.sendMessage(req);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public ObjectPropertiesData getUserInfo() {
		//
		return this.userInfo;
	}

	@Override
	public Address getUri() {
		return uri;
	}

}
