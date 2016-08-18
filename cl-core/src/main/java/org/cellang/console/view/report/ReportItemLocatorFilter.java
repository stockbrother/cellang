package org.cellang.console.view.report;

import java.util.HashSet;
import java.util.Set;

import org.cellang.console.format.ReportItemLocator;

public class ReportItemLocatorFilter {

	Set<String> collapsedKey = new HashSet<String>();

	public boolean accept(ReportItemLocator rr) {
		ReportItemLocator parent = rr.getParent();
		boolean rt = true;
		while (parent != null) {
			if (collapsedKey.contains(parent.getKey())) {
				rt = false;
			}

			parent = parent.getParent();
		}

		//System.out.println("closed set:" + collapsedKey + ",key:" + rr.getKey() + ",rt:" + rt);
		return rt;
	}

	public void changeCollapse(String key) {
		if (this.collapsedKey.contains(key)) {
			this.collapsedKey.remove(key);
		} else {
			this.collapsedKey.add(key);
		}
	}
}
