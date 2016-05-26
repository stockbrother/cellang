package org.cellang.core.commons.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree<T> {
	public static class Node<T> {

		private String name;

		private Map<String, Node<T>> nodeMap = new HashMap<String, Node<T>>();

		public T setTarget(T target) {
			T rt = this.target;
			this.target = target;
			return rt;
		}

		private T target;

		public Node(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public List<Node<T>> getChildList() {
			return new ArrayList<Node<T>>(this.nodeMap.values());
		}

		public Node<T> getNode(List<String> nl) {
			Node<T> rt = this;

			for (int i = 0; i < nl.size(); i++) {

				String name = nl.get(i);
				rt = rt.getChild(name);
				if (rt == null) {
					break;
				}
			}
			return rt;
		}

		public Node<T> getChild(String name) {
			return this.nodeMap.get(name);
		}

		/**
		 * @param name2
		 */
		public Node<T> addChild(String name2) {
			Node<T> rt = new Node<T>(name2);
			this.nodeMap.put(name2, rt);
			return rt;
		}

		public T getTarget() {
			return target;
		}

	}

	private Node<T> root = new Node<T>(null);

	public Node<T> addNode(Path p) {

		Path pp = p.getParent();
		Node<T> pn = this.getNode(pp);
		if (pn == null) {
			pn = addNode(pp);
		}
		// parent got
		String name = p.getName();
		Node<T> old = pn.getChild(name);
		if (old != null) {
			throw new RuntimeException("node existed for path:" + p);
		}
		return pn.addChild(name);
	}

	public Node<T> getNode(Path p) {
		List<String> nl = p.getNameList();
		return root.getNode(nl);
	}

	public Node<T> getOrCreateNode(Path p) {
		Node<T> rt = this.getNode(p);
		if (rt != null) {
			return rt;
		}
		rt = this.addNode(p);
		return rt;
	}

	public List<Node<T>> getChildNodeList(Path p) {
		Node<T> node = this.getNode(p);
		if (node == null) {
			return new ArrayList<Node<T>>();
		}
		return node.getChildList();
	}

	public Node<T> addNode(Path p, T t) {
		// TODO Auto-generated method stub
		Node<T> rt = this.addNode(p);
		rt.setTarget(t);
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.struct.Tree#getTarget(com.fs.commons.api.struct.Path)
	 */
	public T getTarget(Path p) {
		Node<T> rt = this.getNode(p);
		return rt == null ? null : rt.getTarget();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.struct.Tree#getTargetListInPath(com.fs.commons.api
	 * .struct.Path)
	 */
	public List<T> getTargetListInPath(Path p) {
		List<T> rt = new ArrayList<T>();
		Path tp = p;
		while (true) {
			T t = this.getTarget(tp);
			rt.add(0, t);
			if (tp.isRoot()) {
				break;
			}
			tp = tp.getParent();//
		}
		return rt;
	}

}
