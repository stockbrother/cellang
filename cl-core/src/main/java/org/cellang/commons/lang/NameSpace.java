package org.cellang.commons.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.cellang.commons.ObjectUtil;

public class NameSpace {

	private static Map<String, NameSpace> CACHE = new HashMap<String, NameSpace>();
	
	public static final char SEP = '.';

	private List<String> nameList;

	public static NameSpace ROOT = NameSpace.valueOf(new String[] {});

	public List<String> getNameList() {
		return nameList;
	}

	public String[] getNameArray() {
		return this.nameList.toArray(new String[this.nameList.size()]);
	}

	public String getName() {
		if (this.isRoot()) {
			return null;
		}
		return this.nameList.get(this.nameList.size() - 1);
	}

	private NameSpace(String[] names) {
		this(Arrays.asList(names));
	}

	private NameSpace(List<String> nl) {

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

	public NameSpace getParent() {
		if (this.isRoot()) {
			return null;
		}
		List<String> ps = new ArrayList<String>();
		for (int i = 0; i < this.nameList.size() - 1; i++) {
			String name = this.nameList.get(i);
			ps.add(name);

		}
		return new NameSpace(ps);
	}

	public int size() {
		return this.nameList.size();
	}

	public boolean isSubPath(NameSpace p) {
		return this.isSubPath(p, false);
	}

	public boolean isSubPath(NameSpace p, boolean include) {
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

	public NameSpace getSubPath(String name) {
		List<String> names = new ArrayList<String>();
		names.addAll(this.nameList);
		names.add(name);
		return NameSpace.valueOf(names);
	}

	public static NameSpace valueOf(String name, NameSpace p) {
		List<String> nl = new ArrayList<String>(p.getNameList());
		nl.add(0, name);
		return new NameSpace(nl);
	}

	public static NameSpace valueOf(NameSpace par, String name) {
		return par.getSubPath(name);
	}

	public static NameSpace valueOf(List<String> names) {
		return new NameSpace(names);
	}

	public static NameSpace valueOf(String[] names) {
		return valueOf(Arrays.asList(names));

	}

	public static NameSpace valueOf(String name) {
		NameSpace rt = CACHE.get(name);
		if (rt != null) {
			return rt;
		}
		String[] names = name.split("\\" + SEP);
		rt = valueOf(names);
		CACHE.put(name, rt);
		return rt;

	}

	public int length() {
		return this.nameList.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof NameSpace)) {
			return false;
		}
		NameSpace p2 = (NameSpace) obj;
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

	public boolean contains(NameSpace path) {
		NameSpace p = this;
		while (p != null) {
			if (path.isSubPath(p, true)) {
				return true;
			}
			p = p.removeFirst();
		}
		return false;
	}

	public NameSpace removeFirst() {
		if (this.isRoot()) {
			return null;
		}

		List<String> nL = new ArrayList<String>(this.nameList);
		nL.remove(0);
		return NameSpace.valueOf(nL);
	}

	public NameSpace removeLast() {
		if (this.isRoot()) {
			return null;
		}

		List<String> nL = new ArrayList<String>(this.nameList);
		nL.remove(nL.size() - 1);
		return NameSpace.valueOf(nL);
	}

	/**
	 * Jan 8, 2013
	 */
	public NameSpace concat(NameSpace p) {
		//
		List<String> names = new ArrayList<String>(this.nameList);
		names.addAll(p.nameList);
		return new NameSpace(names);
	}

	public String toString(boolean startBySep) {
		return toString(SEP, startBySep);
	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String toString() {
		//
		return toString(SEP, true);
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
