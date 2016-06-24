package org.cellang.core.server;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.cellang.commons.dispatch.DefaultDispatcher;
import org.cellang.commons.dispatch.Dispatcher;
import org.cellang.commons.jdbc.ConnectionPoolWrapper;
import org.cellang.commons.lang.Handler;
import org.cellang.commons.lang.NameSpace;
import org.cellang.core.entity.EntityService;
import org.cellang.core.h2db.H2ConnectionPoolWrapper;
import org.cellang.core.lang.ErrorInfo;
import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.server.handler.ClientInitHandler;
import org.cellang.core.server.handler.ClientIsReadyHandler;
import org.cellang.core.server.handler.LoginHandler;
import org.cellang.core.server.handler.SignupSubmitHandler;
import org.cellang.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCellangServer implements MessageServer {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCellangServer.class);

	private Dispatcher<MessageContext> dispatcher;

	private ExecutorService executor;

	private boolean running;

	private ServerContext serverContext;

	private File home;

	private ChannelProvider channelProvider;

	public DefaultCellangServer(File home, ChannelProvider cp) {
		this.home = home;
		this.channelProvider = cp;

	}

	@Override
	public void start() {
		LOG.info("start... with home:" + this.home.getAbsolutePath());
		File dbHome = new File(home.getAbsolutePath() + File.separator + "db");
		String dbName = "h2db";
		EntityService es = EntityService.newInstance(dbHome, dbName);
		this.serverContext = new ServerContext(es);

		// TODO handler should be state-less and dispatcher should be removed.
		// each message should be self process-able when the context is ready.

		this.dispatcher = new DefaultDispatcher<MessageContext>();
		this.dispatcher.addDefaultHandler(new Handler<MessageContext>() {

			@Override
			public void handle(MessageContext t) {
				t.getResponseMessage().getErrorInfos().add(ErrorInfo.valueOf("handler/not-found",
						"request type:" + t.getRequestMessage().getPath().toString()));
				LOG.warn("ignore msg:" + t.getRequestMessage().getPath());
			}

		});
		this.dispatcher.addHandler(Messages.MSG_CLIENT_IS_READY, new ClientIsReadyHandler());
		this.dispatcher.addHandler(Messages.REQ_CLIENT_INIT, new ClientInitHandler());
		this.dispatcher.addHandler(Messages.SIGNUP_REQ, new SignupSubmitHandler());
		this.dispatcher.addHandler(Messages.LOGIN_REQ, new LoginHandler());

		this.executor = Executors.newCachedThreadPool();
		this.running = true;
		LOG.info("stared with home:" + this.home.getAbsolutePath());
	}

	@Override
	public void shutdown() {
		LOG.info("shutdown...");//
		this.running = false;
		LOG.info("shutdown done");//
	}

	@Override
	public void process(MessageI req) {
		String cid = req.getChannelId();
		Channel c = this.channelProvider.getChannel(cid);
		process(req, c);
	}

	@Override
	public void process(MessageI req, Channel channel) {
		NameSpace p = req.getPath();
		NameSpace p2 = NameSpace.valueOf(p.getParent(), "response");
		MessageI res = MessageSupport.newMessage(p2);

		try {
			res.setHeader(MessageI.HK_SOURCE_ID, req.getId());
			MessageContext mc = new MessageContext(req, res, channel);
			this.doService(mc);
		} catch (Throwable t) {
			res.getErrorInfos().add(new ErrorInfo(t));
			LOG.error("add error to response for request message:" + req, t);
		}
	}

	protected void doService(final MessageContext mc) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("service: request message:" + mc.getRequestMessage());//
		}
		mc.setServerContext(this.serverContext);
		try {

			Future<MessageContext> rtF = this.executor.submit(new Callable<MessageContext>() {

				@Override
				public MessageContext call() throws Exception {
					DefaultCellangServer.this.dispatcher.dispatch(mc.getRequestMessage().getPath(), mc);//
					return mc;
				}
			});
			try {
				rtF.get();
			} catch (InterruptedException e) {
				throw ExceptionUtil.toRuntimeException(e);
			} catch (ExecutionException e) {
				throw ExceptionUtil.toRuntimeException(e.getCause());//
			}
			MessageI res = mc.getResponseMessage();
			mc.getChannel().sendMessage(res);//
		} finally {
			mc.setServerContext(null);//
		}

	}

}
