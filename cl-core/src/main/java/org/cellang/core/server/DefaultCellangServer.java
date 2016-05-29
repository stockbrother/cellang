package org.cellang.core.server;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.cellang.commons.dispatch.DefaultDispatcher;
import org.cellang.commons.dispatch.Dispatcher;
import org.cellang.commons.lang.Handler;
import org.cellang.commons.lang.Path;
import org.cellang.core.lang.CellSource;
import org.cellang.core.lang.jdbc.JdbcCellSourceConfiguration;
import org.cellang.core.lang.util.ExceptionUtil;
import org.cellang.core.server.handler.AuthHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCellangServer implements CellangServer {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCellangServer.class);

	private Dispatcher<MessageContext> dispatcher;

	private ExecutorService executor;

	private boolean running;

	private ServerContext serverContext;

	public DefaultCellangServer() {

		JdbcCellSourceConfiguration cfg = new JdbcCellSourceConfiguration();
		cfg.setUrl("jdbc:h2:./target/test/h2db");

		CellSource as = cfg.create();

		this.serverContext = new ServerContext();
		serverContext.setCellSource(as);
		this.dispatcher = new DefaultDispatcher<MessageContext>();
		this.dispatcher.addDefaultHandler(new Handler<MessageContext>() {

			@Override
			public void handle(MessageContext t) {
				LOG.warn("ignore msg:" + t.getRequestMessage().getPath());
			}

		});
		this.dispatcher.addHandler(Path.valueOf("msg/auth"), new AuthHandler());

		this.executor = Executors.newCachedThreadPool();
	}

	@Override
	public void start() {
		this.serverContext.getCellSource().open();
		this.running = true;

	}

	private void doService(MessageContext mc) {
		this.dispatcher.dispatch(mc.getRequestMessage().getPath(), mc);//
	}

	@Override
	public void shutdown() {
		this.running = false;
		this.serverContext.getCellSource().close();
	}

	@Override
	public void service(final MessageContext mc) {
		mc.setServerContext(this.serverContext);
		try {

			Future<MessageContext> rtF = this.executor.submit(new Callable<MessageContext>() {

				@Override
				public MessageContext call() throws Exception {
					DefaultCellangServer.this.doService(mc);
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
