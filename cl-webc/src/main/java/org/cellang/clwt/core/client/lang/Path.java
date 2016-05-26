package org.cellang.clwt.core.client.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cellang.clwt.core.client.util.ObjectUtil;

public class Path {

	public static final Path ROOT = new Path(new String[] {});

	private List<String> nameList;

	public String getName() {
		if (this.nameList.isEmpty()) {
			return null;
		}
		return this.nameList.get(this.nameList.size() - 1);
	}

	public List<String> getNameList() {
		return nameList;
	}

	private Path(String[] names) {
		this(Arrays.asList(names));
	}

	private Path(List<String> nl) {

		List<String> nl2 = new ArrayList<String>();
		for (String s : nl) {
			if (s.trim().length() == 0) {
				continue;
			}
			nl2.add(s);
		}
		this.nameList = nl2;

	}

	public boolean isRoot() {
		return this.nameList.isEmpty();
	}

	public Path getParent() {
		if (this.isRoot()) {
			return null;
		}
		List<String> ps = new ArrayList<String>(this.nameList.subList(0, this.nameList.size() - 1));

		return new Path(ps);
	}

	public int size() {
		return this.nameList.size();
	}

	public Path subPath(int idx) {

		List<String> nl = this.nameList.subList(idx, this.nameList.size());
		return new Path(nl);
	}

	public boolean isSubPath(Path p) {
		return this.isSubPath(p, false);
	}

	public boolean isSubPath(Path p, boolean include) {
		int s1 = this.size();
		int s2 = p.size();
		if (include && s1 > s2 || !include && s1 >= s2) {
			return false;
		}
		for (int i = 0; i < this.nameList.size(); i++) {
			String n1 = this.nameList.get(i);
			String n2 = p.nameList.get(i);
			if (!ObjectUtil.nullSafeEquals(n1, n2)) {
				return false;
			}
		}
		return true;

	}

	public Path getSubPath(String name) {
		List<String> names = new ArrayList<String>(this.nameList);
		names.add(name);
		return Path.valueOf(names);
	}
	
	public static Path valueOf(Path par, String name) {
		return par.getSubPath(name);
	}

	public static Path valueOf(List<String> names) {
		return new Path(names);
	}

	public static Path valueOf(String name, char sep) {
		String[] names = name.split("" + sep);
		return valueOf(names);
	}

	public static Path valueOf(String name) {
		return valueOf(name, '/');
	}

	public static Path valueOf(String[] names) {
		return new Path(names);
	}

	public int length() {
		return this.nameList.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Path)) {
			return false;
		}
		Path p2 = (Path) obj;
		if (p2.size() != this.size()) {
			return false;
		}
		for (int i = 0; i < this.nameList.size(); i++) {
			String o1 = this.nameList.get(i);
			String o2 = p2.nameList.get(i);

			if (!ObjectUtil.nullSafeEquals(o1, o2)) {
				return false;
			}

		}
		return true;
	}

	public String toString(char sep) {
		String rt = "";
		for (String n : this.nameList) {
			rt += sep;
			rt += n;
		}
		return rt;
	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String toString() {
		//
		return toString('/');
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public int hashCode() {
		//
		return this.nameList.hashCode();
	}

	/**
	 * Jan 8, 2013
	 */
	public static Path valueOf(String string, Path p) {
		//
		List<String> names = new ArrayList<String>(p.nameList);
		names.add(0, string);
		return new Path(names);
	}

	/**
	 * Jan 8, 2013
	 */
	public Path concat(Path p) {
		//
		List<String> names = new ArrayList<String>(this.nameList);
		names.addAll(p.nameList);
		return new Path(names);
	}

}
