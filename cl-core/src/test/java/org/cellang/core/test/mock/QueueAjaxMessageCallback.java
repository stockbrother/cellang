package org.cellang.core.test.mock;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.cellang.commons.transfer.ajax.AjaxMsg;
import org.cellang.core.util.ExceptionUtil;

public class QueueAjaxMessageCallback extends AjaxMessageCallback {

	public BlockingQueue<AjaxMsg> queue;

	public QueueAjaxMessageCallback() {
		this.queue = new LinkedBlockingQueue<AjaxMsg>();
	}

	@Override
	protected void onSuccess(List<AjaxMsg> msgL) {
		for (AjaxMsg ams : msgL) {
			try {
				this.queue.put(ams);
			} catch (InterruptedException e) {
				throw ExceptionUtil.toRuntimeException(e);//
			}
		}
	}

}
