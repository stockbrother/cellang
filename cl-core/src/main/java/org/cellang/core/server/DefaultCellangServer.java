package org.cellang.core.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DefaultCellangServer implements CellangServer {

	private Dispatcher<MessageContext> dispatcher;

	private BlockingQueue<MessageContext> queue;// cache.

	private ExecutorService executor;

	private Future<Long> task;

	private boolean running;

	public DefaultCellangServer() {
		this.dispatcher = new DefaultDispatcher<MessageContext>();
		this.queue = new LinkedBlockingQueue<MessageContext>();
		this.executor = Executors.newSingleThreadExecutor();
	}

	@Override
	public void start() {
		this.running = true;
		task = this.executor.submit(new Callable<Long>() {

			@Override
			public Long call() throws Exception {
				DefaultCellangServer.this.doCall();
				return null;
			}
		});

	}

	private void doCall() {
		while (this.running) {
			MessageContext mc = null;
			try {
				mc = this.queue.poll(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				continue;
			}
			this.dispatcher.dispatch(mc.getPath(), mc);//
		}

	}

	@Override
	public void shutdown() {
		this.running = false;
	}

	@Override
	public void service() {
	
	}

}
