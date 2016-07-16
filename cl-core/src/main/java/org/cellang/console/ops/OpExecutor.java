package org.cellang.console.ops;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpExecutor {
	private static class OpFuture<T> {
		public OpFuture(ConsoleOp<T> op, Future<T> f) {
			this.future = f;
			this.op = op;
		}

		public Future<T> future;
		public ConsoleOp<T> op;

		public boolean isDone() {
			return future.isDone();
		}

		public T get() throws InterruptedException, ExecutionException {
			return future.get();
		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(OpExecutor.class);

	private ExecutorService executor = Executors.newCachedThreadPool();

	private ExecutorService monitorExecutor = Executors.newSingleThreadExecutor();

	private List<OpFuture<?>> futureList = new ArrayList<OpFuture<?>>();

	private Future<Void> monitorFuture;

	private boolean running = true;

	public OpExecutor() {

		this.monitorFuture = this.monitorExecutor.submit(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				OpExecutor.this.runMonitor();
				return null;
			}
		});

	}

	protected void runMonitor() {

		while (true) {

			synchronized (this.futureList) {
				List<OpFuture<?>> futureList2 = new ArrayList<>(this.futureList);
				this.futureList.clear();
				for (OpFuture<?> f : futureList2) {
					if (f.isDone()) {
						try {
							f.get();
							LOG.info("done of op:" + f.op);

						} catch (InterruptedException | ExecutionException e) {
							LOG.error("error of op:" + f.op, e);
						}
					} else {
						this.futureList.add(f);//
					}
				}
			}
			if (!this.running && this.futureList.isEmpty()) {
				break;
			}

		}
		LOG.info("done of monitor.");

	}

	public <T> Future<T> execute(ConsoleOp<T> op, OperationContext oc) {
		if (!this.running) {
			throw new RuntimeException("cannot execute operation,executor stoped.");
		}
		LOG.info("submit op async,op:" + op);
		Future<T> rt = this.executor.submit(new Callable<T>() {

			@Override
			public T call() throws Exception {
				return op.execute(oc);
			}
		});

		synchronized (this.futureList) {
			OpFuture<T> of = new OpFuture<>(op, rt);
			this.futureList.add(of);
		}

		return rt;
	}

}
