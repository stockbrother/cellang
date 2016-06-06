/**
 * Jun 11, 2012
 */
package org.cellang.clwt.core.client.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.ContainerAwareWebObject;
import org.cellang.clwt.core.client.UiConstants;
import org.cellang.clwt.core.client.WebClient;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.codec.CodecFactory;
import org.cellang.clwt.core.client.codec.JsonCodecFactoryC;
import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.event.AfterClientStartEvent;
import org.cellang.clwt.core.client.event.ClientClosingEvent;
import org.cellang.clwt.core.client.event.ClientConnectLostEvent;
import org.cellang.clwt.core.client.event.ClientStartFailureEvent;
import org.cellang.clwt.core.client.event.EndpointCloseEvent;
import org.cellang.clwt.core.client.event.EndpointErrorEvent;
import org.cellang.clwt.core.client.event.EndpointOpenEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.gwtbridge.GwtClosingHandler;
import org.cellang.clwt.core.client.lang.AbstractHasProperties;
import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.State;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.message.MessageDispatcherI;
import org.cellang.clwt.core.client.message.MessageDispatcherImpl;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.clwt.core.client.transfer.EndpointImpl;
import org.cellang.clwt.core.client.transfer.Endpoint;
import org.cellang.clwt.core.client.transfer.TransferPointConfiguration;
import org.cellang.clwt.core.client.transfer.TransferPointConfiguration.ProtocolPort;
import org.cellang.clwt.core.client.widget.WebWidget;
import org.cellang.clwt.core.client.widget.WebWidgetFactory;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;

/**
 * @author wu TOTO rename to UiCoreI and impl.
 */
public class UiClientImpl extends ContainerAwareWebObject implements WebClient {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(UiClientImpl.class);

	private String clientId;

	private CodecFactory cf;

	private WebWidget root;

	private HasProperties<String> parameters;

	private HasProperties<String> localized;

	private List<Address> uriList;

	private Map<Integer, Endpoint> tryedEndpointMap = new HashMap<Integer, Endpoint>();

	public static final State UNKNOWN = State.valueOf("UNKNOWN");

	// starting
	public static final State STARTING = State.valueOf("STARTING");

	// failed to connect any endpoint protocol.
	public static final State FAILED = State.valueOf("FAILED");

	// ok state
	public static final State STARTED = State.valueOf("STARTED");

	// connection is closed,then set to lost state.
	public static final State LOST = State.valueOf("LOST");

	private static final String PK_TRYING_INDEX = "_trying_idx";

	/**
	 * Note:only set after client start.
	 */
	private Endpoint endpoint;

	public UiClientImpl(Container c, WebWidget root) {
		super(c);

		this.root = root;
		this.parameters = new AbstractHasProperties<String>();
		this.localized = new AbstractHasProperties<String>();

		Window.addWindowClosingHandler(new GwtClosingHandler() {

			@Override
			protected void handleInternal(ClosingEvent evt) {
				//
				new ClientClosingEvent(UiClientImpl.this).dispatch();
			}
		});
		this.setState(UNKNOWN);
	}

	private int getWindowLocationPort() {
		String portS = Window.Location.getPort();
		int port = Integer.parseInt(portS);
		return port;
	}

	private String getWindowLocationProtocol() {
		String pro = Window.Location.getProtocol();

		if (pro.endsWith(":")) {
			pro = pro.substring(0, pro.length() - 1);
		}
		return pro;
	}

	@Override
	public void doAttach() {

		// TODO move to SPI.active.
		this.cf = new JsonCodecFactoryC();

	}

	protected Endpoint newEndpoint(int tryIdx) {
		MessageDispatcherI md = new MessageDispatcherImpl("endpoint");
		Address uri = this.uriList.get(tryIdx);
		final Endpoint rt = new EndpointImpl(this.container, uri, md);
		rt.setProperty(PK_TRYING_INDEX, tryIdx);// the trying idx.

		rt.addHandler(EndpointOpenEvent.TYPE, new EventHandlerI<EndpointOpenEvent>() {

			@Override
			public void handle(EndpointOpenEvent t) {
				UiClientImpl.this.onEndpointOpen(t.getEndPoint());
			}
		});
		rt.addHandler(EndpointErrorEvent.TYPE, new EventHandlerI<EndpointErrorEvent>() {

			@Override
			public void handle(EndpointErrorEvent t) {
				UiClientImpl.this.onEndpointError(t.getEndPoint());
			}
		});
		rt.addHandler(EndpointCloseEvent.TYPE, new EventHandlerI<EndpointCloseEvent>() {

			@Override
			public void handle(EndpointCloseEvent t) {
				UiClientImpl.this.onEndpointClose(t);
			}
		});

		rt.addHandler(Path.valueOf("/endpoint/message/client/init/success"), new MessageHandlerI<MsgWrapper>() {

			@Override
			public void handle(MsgWrapper t) {
				UiClientImpl.this.onInitSuccess(rt, t);
			}
		});
		return rt;
	}

	@Override
	public void start() {
		if (!this.isState(UNKNOWN)) {
			throw new UiException("state should be:" + UNKNOWN + ",but state is:" + this.state);
		}

		this.setState(STARTING);

		this.uriList = new ArrayList<Address>();
		List<ProtocolPort> ppL = new ArrayList<ProtocolPort>(
				TransferPointConfiguration.getInstance().getConfiguredList());

		if (ppL.isEmpty()) {// not configured,then default ones

			if (false) {// try ws/wss first,
				String hpro = getWindowLocationProtocol();
				boolean https = hpro.equals("https");
				String wsp = https ? "wskts" : "wskt";
				String portS = Window.Location.getPort();
				int port = Integer.parseInt(portS);
				ppL.add(new ProtocolPort(wsp, port));
			}
			if (true) {// try ajax second
				String pro = getWindowLocationProtocol();
				int port = getWindowLocationPort();
				ppL.add(new ProtocolPort(pro, port));
			}

		}

		String host = Window.Location.getHostName();
		String ajaRes = "/transfer/ajax";
		String wsRes = "/transfer/wskt";

		for (ProtocolPort pp : ppL) {
			String pro = pp.protocol;
			String resource;
			int port = pp.port;

			if (pro.startsWith("http")) {
				resource = ajaRes;
			} else if (pro.startsWith("wskt")) {
				resource = wsRes;
			} else {
				throw new UiException("not supported pro:" + pro);
			}
			Address uri = new Address(pro, host, port, resource);
			this.uriList.add(uri);
		}

		this.tryConnect(0);
	}

	public void setState(State s) {
		State old = this.state;
		super.setState(s);
		LOG.info("state changed,old:" + old + ",new:" + this.state);
	}

	public boolean tryConnect(int uriIdx) {
		LOG.info("try connect:" + uriIdx);
		Endpoint ep = this.tryedEndpointMap.get(uriIdx);
		if (ep != null) {// tryed before.
			return true;//
		}

		if (uriIdx < this.uriList.size()) {
			// try the new endpoint.
			ep = this.newEndpoint(uriIdx);
			this.tryedEndpointMap.put(uriIdx, ep);
			ep.open();
			return true;
		}

		// failed
		this.setState(FAILED);// all protocols is failed, how to do? only
								// failed,notify user.
		new ClientStartFailureEvent(this).dispatch();
		// see the on error/onclose event processing methods
		return false;
	}

	/**
	 * Jan 1, 2013
	 */
	protected void onInitSuccess(Endpoint ep, MsgWrapper evt) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onInitSuccess,ep:" + ep + ",evt:" + evt);//
		}
		MessageData t = evt.getMessage();
		String sd = (String) t.getPayloads().getProperty("clientId", true);
		String sid = sd;
		if (sid == null) {
			throw new UiException("got a null sessionId");
		}
		this.clientId = sd;
		{
			ObjectPropertiesData opd = (ObjectPropertiesData) t.getPayload("parameters", true);//
			// parameters:

			for (String key : opd.keyList()) {
				String valueS = (String) opd.getProperty(key);

				this.parameters.setProperty(key, valueS);

			}
		}
		{
			// localized resource
			ObjectPropertiesData opd2 = (ObjectPropertiesData) t.getPayload("localized", true);//
			// parameters:

			for (String key : opd2.keyList()) {
				String valueS = (String) opd2.getProperty(key);

				this.localized.setProperty(key, valueS);

			}
		}
		//
		this.endpoint = ep;
		// event
		this.setState(STARTED);// started is here.
		new AfterClientStartEvent(this).dispatch();
	}

	public void onEndpointError(Endpoint ep) {

		if (this.isState(STARTED)) {//
			return;// ignore ,because it may a applevel error.
		}

		// ws error will delay the ajax request for that the limit is 2
		// connections.

		if (ep.isOpen()) {
			// this endpoint is open,but error before client/server is ready, so
			// we
			// cannot relay on this endpoint, we close it.
			ep.close();//
		}

		int uriIdx = (Integer) ep.getProperty(PK_TRYING_INDEX, true);
		// close event may not raise for some error?
		this.tryConnect(uriIdx + 1);
	}

	public void onEndpointClose(EndpointCloseEvent evt) {

		//
		if (this.isState(STARTED)) {// if started,not try other method for
									// connect
			this.setState(LOST);
			new ClientConnectLostEvent(this).dispatch();
			return;// ignore ,because it may a applevel error.
		}

		// ws error will delay the ajax request for that the limit is 2
		// connections.
		Endpoint ep = evt.getEndPoint();
		if (ep.isOpen()) {
			// this endpoint is open,but error before client/server is ready, so
			// we
			// cannot relay on this endpoint, we close it.
			ep.close();//
		}

		int uriIdx = (Integer) ep.getProperty(PK_TRYING_INDEX, true);
		// close event may not raise for some error?
		this.tryConnect(uriIdx + 1);
	}

	/**
	 * After serverIsReady, the the endpoint is open event raised up.<br>
	 * 
	 * Util now,opened endpoint does not gurantee it is the final one.<br>
	 * 
	 * After client start, is gurantee this.
	 * 
	 */
	public void onEndpointOpen(Endpoint ep) {

		MsgWrapper req = new MsgWrapper(UiConstants.P_CLIENT_INIT);
		String locale = this.getPreferedLocale();

		req.setPayload("preferedLocale", (locale));

		// int uriIdx = (Integer) ep.getProperty(PK_TRYING_INDEX, true);

		ep.sendMessage(req);

	}

	@Override
	public String getPreferedLocale() {
		return null;//
	}

	@Override
	public String localized(String key) {
		// i18n
		String rt = this.localized.getProperty(key);
		if (rt == null) {
			return key;
		}
		return rt;
	}

	/**
	 * Client got the sessionId from server,client stared on. Nov 14, 2012
	 */

	public String getParameter(String key, String def) {
		return this.parameters.getProperty(key, def);
	}

	public String getParameter(String key, boolean force) {
		return this.parameters.getProperty(key, force);
	}

	protected WebWidgetFactory getWidgetFactory() {
		return this.getContainer().get(WebWidgetFactory.class, true);

	}

	/**
	 * @return the root
	 */
	public WebWidget getRoot() {
		return root;
	}

	/* */
	@Override
	public void attach() {

		super.attach();
		this.root.attach();// TODO remove this call,add root as a child.

	}

	/* */
	@Override
	public void detach() {
		this.root.detach();//
		super.detach();

	}

	/*
	 * Nov 26, 2012
	 */
	@Override
	public String getClientId() {
		//
		return this.clientId;
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public CodecFactory getCodecFactory() {
		//
		return this.cf;
	}

	@Override
	public void setParameter(String key, String value) {
		this.parameters.setProperty(key, value);
	}

	/*
	 * Jan 1, 2013
	 */
	@Override
	public Endpoint getEndpoint(boolean force) {
		//
		if (this.endpoint == null && force) {
			throw new UiException("end point is null, client not started?");
		}
		return this.endpoint;
	}

	/*
	 * Apr 4, 2013
	 */
	@Override
	public int getParameterAsInt(String key, int def) {
		//
		String rt = this.getParameter(key, false);
		return rt == null ? def : Integer.parseInt(rt);
	}

	/*
	 * Apr 4, 2013
	 */
	@Override
	public boolean getParameterAsBoolean(String key, boolean def) {
		//
		String rt = this.getParameter(key, false);
		return rt == null ? def : Boolean.valueOf(rt);

	}

}
