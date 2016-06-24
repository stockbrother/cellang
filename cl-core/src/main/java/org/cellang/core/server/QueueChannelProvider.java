package org.cellang.core.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.lang.MessageI;

public class QueueChannelProvider implements ChannelProvider {
	public static class QueueChannel extends Channel {
		public BlockingQueue<MessageI> queue = new LinkedBlockingQueue<MessageI>();

		public QueueChannel(String id) {
			super(id);
		}

		@Override
		public void sendMessage(MessageI msg) {
			try {
				this.queue.put(msg);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} //
		}

	}

	private Map<String, Channel> map = new HashMap<String, Channel>();

	@Override
	public Channel getChannel(String id) {
		return this.map.get(id);
	}

	public QueueChannel createChannel() {
		String id = UUIDUtil.randomStringUUID();
		QueueChannel rt = new QueueChannel(id);
		this.map.put(id, rt);//
		return rt;

	}
}
