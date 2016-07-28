package org.cellang.console.format;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * 1-1 ReportItemEntity
 * 
 * @see ReportItemLocators
 * @author wuzhen
 *
 */
public class ReportItemLocator {
	private ReportItemLocator parent;
	private String key;
	private List<ReportItemLocator> childList = new ArrayList<>();
	private int order;

	public ReportItemLocator(ReportItemLocator parent, String key) {
		this.parent = parent;
		this.key = key;
	}

	public static ReportItemLocator newInstance(String key) {
		return new ReportItemLocator(null, key);
	}

	public ReportItemLocator newChild(String key) {
		ReportItemLocator rt = ReportItemLocator.newInstance(key);
		this.add(rt);
		return rt;
	}

	public ReportItemLocator add(ReportItemLocator child) {
		if (child.parent != null) {
			throw new RuntimeException("child has parent:" + child.parent);
		}
		child.parent = this;
		this.childList.add(child);
		return this;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ReportItemLocator getParent() {
		return parent;
	}

	public boolean isRoot() {
		return this.parent == null;
	}

	public void write(Writer writer) throws IOException {
		write(0, writer);
	}

	private void write(int depth, Writer writer) throws IOException {
		for (int i = 0; i < depth; i++) {
			writer.write(",");
		}
		writer.write(this.key);
		writer.write("\n");
		for (ReportItemLocator child : this.childList) {
			child.write(depth + 1, writer);
		}
	}

	public List<ReportItemLocator> getChildList() {
		return this.childList;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}
}
