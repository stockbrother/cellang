package org.cellang.corpsviewer.corpdata;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.lang.Visitor;

/**
 * 1-1 ReportItemEntity
 * 
 * @see ItemDefines
 * @author wuzhen
 *
 */
public class ItemDefine {
	private ItemDefine parent;
	private String key;
	private List<ItemDefine> childList = new ArrayList<>();
	private int order;

	public ItemDefine(ItemDefine parent, String key) {
		this.parent = parent;
		this.key = key;
	}

	public static ItemDefine newInstance(String key) {
		return new ItemDefine(null, key);
	}

	public ItemDefine newChild(String key) {
		ItemDefine rt = ItemDefine.newInstance(key);
		this.add(rt);
		return rt;
	}

	public ItemDefine add(ItemDefine child) {
		if (child.parent != null) {
			throw new RuntimeException("child has parent:" + child.parent);
		}
		child.parent = this;
		this.childList.add(child);
		return this;
	}

	public ItemDefine find(String key) {
		if (key.equals(this.key)) {
			return this;
		}
		for (ItemDefine cd : this.childList) {
			ItemDefine rt = cd.find(key);
			if (rt != null) {
				return rt;
			}			
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ItemDefine getParent() {
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
		for (ItemDefine child : this.childList) {
			child.write(depth + 1, writer);
		}
	}

	public List<ItemDefine> getChildList() {
		return this.childList;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public void forEach(Visitor<ItemDefine> vis, boolean includeThis) {
		if (includeThis) {
			vis.visit(this);
		}
		for (ItemDefine cI : this.childList) {
			cI.forEach(vis, true);//
		}
	}

	public void addAllToList(List<ItemDefine> list, boolean addThis) {

		if (addThis) {
			list.add(this);
		}
		for (ItemDefine cI : this.childList) {
			cI.addAllToList(list, true);//
		}
	}

}
