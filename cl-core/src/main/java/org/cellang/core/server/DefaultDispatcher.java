package org.cellang.core.server;

import java.util.ArrayList;
import java.util.List;

import org.cellang.core.commons.lang.Handler;
import org.cellang.core.commons.lang.Path;
import org.cellang.core.commons.lang.Tree;
import org.cellang.core.commons.lang.Tree.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultDispatcher<T> implements Dispatcher<T> {

	private static class PathEntryHandler<T> {

		protected List<HandlerEntry<T>> handlers = new ArrayList<HandlerEntry<T>>();

		public void addHandler(Path p, boolean strict, Handler<T> h) {
			this.handlers.add(new HandlerEntry<T>(p, strict, h));
		}

		public int handle(Path p, T sc) {
			int rt = 0;
			for (HandlerEntry<T> h : handlers) {
				boolean hs = h.tryHandle(p, sc);

				if (hs) {
					rt++;
				}

			}
			return rt;
		}

	}

	private static class HandlerEntry<T> {

		private Path path;

		private boolean strict;

		private Handler<T> target;

		public HandlerEntry(Path p, boolean strict, Handler<T> h) {
			this.path = p;
			this.strict = strict;
			this.target = h;
		}

		public boolean tryHandle(Path p, T sc) {
			if (this.strict && p.equals(this.path) || !this.strict && path.isSubPath(p, true)) {
				this.target.handle(sc);
				return true;
			}
			return false;
		}

	}

	protected static final Logger LOG = LoggerFactory.getLogger(DefaultDispatcher.class);

	private Tree<PathEntryHandler<T>> tree;

	private String name;

	public DefaultDispatcher() {
		this.tree = new Tree<PathEntryHandler<T>>();
	}

	@Override
	public void dispatch(Path p, T ctx) {

		List<PathEntryHandler<T>> chL = this.tree.getTargetListInPath(p);
		int count = 0;
		for (PathEntryHandler<T> ch : chL) {
			if (ch == null) {
				continue;
			}
			count += ch.handle(p, ctx);
		}

		if (count == 0) {
			LOG.warn("dispatcher:" + this.name + " has no handler/s for ctx:" + ctx + " with path:" + p);
		}
	}

	@Override
	public void addHandler(Path p, Handler<T> h) {
		this.addHandler(p, false, h);
	}

	@Override
	public void addHandler(Path p, boolean strict, Handler<T> h) {
		Node<PathEntryHandler<T>> node = this.tree.getOrCreateNode(p);
		PathEntryHandler<T> cl = node.getTarget();
		if (cl == null) {
			cl = new PathEntryHandler<T>();
			node.setTarget(cl);
		}
		cl.addHandler(p, strict, h);
	}
}
