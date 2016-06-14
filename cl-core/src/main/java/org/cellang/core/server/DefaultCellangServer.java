package org.cellang.core.server;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.cellang.commons.dispatch.DefaultDispatcher;
import org.cellang.commons.dispatch.Dispatcher;
import org.cellang.commons.lang.Handler;
import org.cellang.commons.lang.NameSpace;
import org.cellang.core.lang.ErrorInfo;
import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.rowobject.AccountRowObject;
import org.cellang.core.rowobject.CellRowObject;
import org.cellang.core.rowobject.ColumnRowObject;
import org.cellang.core.rowobject.RowRowObject;
import org.cellang.core.rowobject.SheetRowObject;
import org.cellang.core.server.handler.ClientInitHandler;
import org.cellang.core.server.handler.ClientIsReadyHandler;
import org.cellang.core.server.handler.LoginHandler;
import org.cellang.core.server.handler.SheetGetHandler;
import org.cellang.core.server.handler.SheetListHandler;
import org.cellang.core.server.handler.SheetSaveHandler;
import org.cellang.core.server.handler.SignupSubmitHandler;
import org.cellang.core.util.ExceptionUtil;
import org.cellang.elastictable.ElasticTableBuilder;
import org.cellang.elastictable.MetaInfo;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.meta.DataSchema;
import org.cellang.elastictable.test.EmbeddedESServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCellangServer implements MessageServer {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCellangServer.class);

	private Dispatcher<MessageContext> dispatcher;

	private ExecutorService executor;

	private boolean running;

	private ServerContext serverContext;

	private EmbeddedESServer esServer;

	private TableService tableService;

	private File home;

	public DefaultCellangServer(File home) {
		this.home = home;
	}

	@Override
	public void start() {
		LOG.info("start... with home:" + this.home.getAbsolutePath());
		this.esServer = new EmbeddedESServer(home.getAbsolutePath());

		DataSchema sa = DataSchema.newInstance();
		AccountRowObject.config(sa);
		CellRowObject.config(sa);//
		ColumnRowObject.config(sa);
		RowRowObject.config(sa);
		SheetRowObject.config(sa);//

		this.tableService = ElasticTableBuilder.newInstance()
				.metaInfo(MetaInfo.newInstance()//
						.owner("test")//
						.version("0.0.1-SNAPSHOT")//
						.password("none"))
				.schema(sa).client(this.esServer.getClient())//
				.build();//

		this.serverContext = new ServerContext();
		this.dispatcher = new DefaultDispatcher<MessageContext>();
		this.dispatcher.addDefaultHandler(new Handler<MessageContext>() {

			@Override
			public void handle(MessageContext t) {
				t.getResponseMessage().getErrorInfos().add(ErrorInfo.valueOf("handler/not-found",
						"request type:" + t.getRequestMessage().getPath().toString()));
				LOG.warn("ignore msg:" + t.getRequestMessage().getPath());
			}

		});
		this.dispatcher.addHandler(Messages.MSG_CLIENT_IS_READY, new ClientIsReadyHandler(this.tableService));
		this.dispatcher.addHandler(Messages.REQ_CLIENT_INIT, new ClientInitHandler(this.tableService));
		this.dispatcher.addHandler(Messages.AUTH_REQ, new LoginHandler(this.tableService));
		this.dispatcher.addHandler(Messages.SIGNUP_REQ, new SignupSubmitHandler(this.tableService));
		this.dispatcher.addHandler(Messages.LOGIN_REQ, new LoginHandler(this.tableService));
		this.dispatcher.addHandler(Messages.SHEET_SAVE_REQ, new SheetSaveHandler(this.tableService));
		this.dispatcher.addHandler(Messages.SHEET_LIST_REQ, new SheetListHandler(this.tableService));
		this.dispatcher.addHandler(Messages.SHEET_GET_REQ, new SheetGetHandler(this.tableService));
				
		this.executor = Executors.newCachedThreadPool();
		this.running = true;
		LOG.info("stared with home:" + this.home.getAbsolutePath());
	}

	@Override
	public void shutdown() {
		LOG.info("shutdown...");//
		this.running = false;
		this.esServer.shutdown();
		LOG.info("shutdown done");//
	}

	@Override
	public MessageI process(MessageI req) {
		return process(req, null);
	}

	@Override
	public MessageI process(MessageI req, Channel channel) {
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
		return res;
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
