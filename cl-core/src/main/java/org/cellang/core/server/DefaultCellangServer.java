package org.cellang.core.server;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.cellang.commons.dispatch.DefaultDispatcher;
import org.cellang.commons.dispatch.Dispatcher;
import org.cellang.commons.lang.Handler;
import org.cellang.core.server.handler.AuthHandler;
import org.cellang.core.server.handler.ClientInitHandler;
import org.cellang.core.server.handler.ClientIsReadyHandler;
import org.cellang.core.server.handler.SignupHandler;
import org.cellang.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCellangServer implements CellangServer {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCellangServer.class);

	private Dispatcher<MessageContext> dispatcher;

	private ExecutorService executor;

	private boolean running;

	private ServerContext serverContext;

	public DefaultCellangServer() {

		this.serverContext = new ServerContext();
		this.dispatcher = new DefaultDispatcher<MessageContext>();
		this.dispatcher.addDefaultHandler(new Handler<MessageContext>() {

			@Override
			public void handle(MessageContext t) {
				LOG.warn("ignore msg:" + t.getRequestMessage().getPath());
			}

		});
		this.dispatcher.addHandler(Messages.MSG_CLIENT_IS_READY, new ClientIsReadyHandler());
		this.dispatcher.addHandler(Messages.REQ_CLIENT_INIT, new ClientInitHandler());
		this.dispatcher.addHandler(Messages.MSG_AUTH, new AuthHandler());
		this.dispatcher.addHandler(Messages.MSG_SIGNUP, new SignupHandler());
		this.executor = Executors.newCachedThreadPool();
	}

	@Override
	public void start() {
		this.running = true;

	}

	@Override
	public void shutdown() {
		this.running = false;
	}

	@Override
	public void service(final MessageContext mc) {
		try {
			this.doService(mc);
		} catch (Throwable t) {
			LOG.error("", t);			
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
		} finally {
			mc.setServerContext(null);//
		}

	}

}
