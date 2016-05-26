/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package org.cellang.core.lang.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuzhen
 * 
 */
public class ClassWrapper {
	private Class clazz;

	public ClassWrapper(Class cls) {
		this.clazz = cls;
	}

	public List<Class> getSuperTypeList() {// TODO width first
		List<Class> rt = new ArrayList<Class>();
		Class[] its = this.clazz.getInterfaces();

		for (Class c : its) {
			rt.add(c);//
			ClassWrapper cw = new ClassWrapper(c);
			List<Class> superL = cw.getSuperTypeList();
			this.addIfNotContains(rt, superL);//
		}

		Class sup = this.clazz.getSuperclass();

		if (sup != null) {
			rt.add(sup);
			ClassWrapper cw = new ClassWrapper(sup);
			addIfNotContains(rt, cw.getSuperTypeList());
		}

		return rt;
	}

	private List<Class> addIfNotContains(List<Class> cL, List<Class> cL2) {
		for (Class cls : cL2) {
			this.addIfNotContains(cL, cls);
		}
		return cL;
	}

	private List<Class> addIfNotContains(List<Class> cL, Class cls) {
		if (!cL.contains(cls)) {
			cL.add(cls);
		}
		return cL;

	}
}
