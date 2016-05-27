package org.cellang.commons.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.ObjectUtil;

public class Path {

	private static Map<String, Path> CACHE = new HashMap<String, Path>();

	private List<String> nameList;

	public static Path ROOT = Path.valueOf(new String[] {});

	public List<String> getNameList() {
		return nameList;
	}

	public String getName() {
		if (this.isRoot()) {
			return null;
		}
		return this.nameList.get(this.nameList.size() - 1);
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
		List<String> ps = new ArrayList<String>();
		for (int i = 0; i < this.nameList.size() - 1; i++) {
			String name = this.nameList.get(i);
			ps.add(name);

		}
		return new Path(ps);
	}

	public int size() {
		return this.nameList.size();
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
			if (!ObjectUtil.isNullSafeEquals(n1, n2)) {
				return false;
			}
		}
		return true;

	}

	public Path getSubPath(String name) {
		List<String> names = new ArrayList<String>();
		names.addAll(this.nameList);
		names.add(name);
		return Path.valueOf(names);
	}

	public static Path valueOf(String name, Path p) {
		List<String> nl = new ArrayList<String>(p.getNameList());
		nl.add(0, name);
		return new Path(nl);
	}

	public static Path valueOf(Path par, String name) {
		return par.getSubPath(name);
	}

	public static Path valueOf(List<String> names) {
		return new Path(names);
	}

	public static Path valueOf(String[] names) {
		return valueOf(Arrays.asList(names));

	}

	public static Path valueOf(String name) {
		Path rt = CACHE.get(name);
		if (rt != null) {
			return rt;
		}
		rt = valueOf(name, '/');
		CACHE.put(name, rt);
		return rt;

	}

	public static Path valueOf(String name, char sep) {
		String[] names = name.split("" + sep);
		return valueOf(names);
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

			if (!ObjectUtil.isNullSafeEquals(o1, o2)) {
				return false;
			}

		}
		return true;
	}

	public String toString(char sep) {
		return toString(sep, true);
	}

	public String toString(char sep, boolean startBySep) {
		String rt = "";

		for (int i = 0; i < this.nameList.size(); i++) {
			String name = this.nameList.get(i);
			if (i > 0 || startBySep) {
				rt += sep;
			}

			rt += name;
		}
		return rt;
	}

	public boolean contains(Path path) {
		Path p = this;
		while (p != null) {
			if (path.isSubPath(p, true)) {
				return true;
			}
			p = p.removeFirst();
		}
		return false;
	}

	public Path removeFirst() {
		if (this.isRoot()) {
			return null;
		}

		List<String> nL = new ArrayList<String>(this.nameList);
		nL.remove(0);
		return Path.valueOf(nL);
	}

	public Path removeLast() {
		if (this.isRoot()) {
			return null;
		}

		List<String> nL = new ArrayList<String>(this.nameList);
		nL.remove(nL.size() - 1);
		return Path.valueOf(nL);
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

	public String toString(boolean startBySep) {
		return toString('/', startBySep);
	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String toString() {
		//
		return toString('/', true);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public int hashCode() {
		//
		return this.nameList.hashCode();
	}

}
