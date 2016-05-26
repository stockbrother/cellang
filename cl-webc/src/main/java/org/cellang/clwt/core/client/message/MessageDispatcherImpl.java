/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.clwt.core.client.message;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.lang.CollectionHandler;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

/**
 * @author wu
 * 
 */
public class MessageDispatcherImpl implements MessageDispatcherI {

	private static final WebLogger logger = WebLoggerFactory.getLogger(MessageDispatcherImpl.class);//

	protected List<HandlerEntry> handlers;

	protected CollectionHandler<MessageDataWrapper> defaultHandlers;

	protected CollectionHandler<MessageException> exceptionHandlers;

	protected String name;

	public MessageDispatcherImpl(String name) {
		this.name = name;
		this.exceptionHandlers = new CollectionHandler<MessageException>();
		this.handlers = new ArrayList<HandlerEntry>();
		this.defaultHandlers = new CollectionHandler<MessageDataWrapper>();

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(MessageDataWrapper msg) {
		try {
			this.handleInternal(msg);
		} catch (Throwable t) {

			MessageException me = new MessageException(t, msg);
			if (this.exceptionHandlers.size() == 0) {
				logger.error("exception got when dispatch message:" + msg, t);
			} else {
				try {
					this.exceptionHandlers.handle(me);

				} catch (Throwable e2) {
					logger.error("exception's exception", e2);
				} finally {

				}
			}
		}
	}

	protected void handleInternal(MessageDataWrapper t) {
		//logger.debug("dispatcher:" + ",handle msg:" + t);
		Path p = t.getTarget().getPath();

		List<HandlerEntry> hls = new ArrayList<HandlerEntry>(this.handlers);
		int matches = 0;
		for (HandlerEntry he : hls) {
			boolean match = he.tryHandle(true, p, t);
			if (match) {
				matches++;
			}
		}

		if (matches == 0) {

			this.defaultHandlers.handle(t);
			if (this.defaultHandlers.size() == 0) {
				logger.debug("path:" + p + " with msg:" + t + " has no handler match it in dispatcher:"
						+ this.name + ",all handlers:" + hls);
			}
		}

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public <W extends MessageDataWrapper> void addHandler(Path path, MessageHandlerI<W> mh) {
		this.addHandler(path, false, mh);
	}

	@Override
	public <W extends MessageDataWrapper> void addHandler(Path path, boolean strict, MessageHandlerI<W> mh) {

		HandlerEntry he = new HandlerEntry(path, strict, mh);

		this.handlers.add(he);

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public <W extends MessageDataWrapper> void addDefaultHandler(MessageHandlerI<W> mh) {
		this.defaultHandlers.addHandler(mh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.message.MessageDispatcherI#
	 * addExceptionHandler
	 * (com.fs.uicommons.api.gwt.client.message.MessageExceptionHandlerI)
	 */
	@Override
	public void addExceptionHandler(MessageExceptionHandlerI eh) {
		this.exceptionHandlers.addHandler(eh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * message.MessageDispatcherI#dispatch(com.
	 * fs.uicore.api.gwt.client.MsgWrapper)
	 */
	@Override
	public void dispatch(MessageDataWrapper mw) {
		this.handle(mw);
	}

	/*
	 *May 12, 2013
	 */
	@Override
	public void cleanAllHanlders() {
		this.exceptionHandlers.cleanAll();
	}

}
