/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package org.cellang.clwt.commons.client.frwk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cellang.clwt.commons.client.mvc.ModelSupport;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.lang.Path;

/**
 * @author wu
 * 
 */
public class FormModel extends ModelSupport {

	private List<FieldModel> fieldList;

	private List<Path> actionList;

	/**
	 * @param name
	 */
	public FormModel(String name) {
		super(name);
		this.fieldList = new ArrayList<FieldModel>();
		this.actionList = new ArrayList<Path>();
	}

	public List<FieldModel> getFieldModelList() {
		return this.fieldList;
	}

	public <T> T getFieldValue(String fname, T def) {
		FieldModel fm = this.getFieldModel(fname, false);

		T rt = (T) fm.getFieldValue();

		return rt == null ? def : rt;
	}

	public FieldModel getFieldModel(String name, boolean force) {

		List<FieldModel> fmL = this.fieldList;
		for (FieldModel fm : fmL) {
			if (fm.getName().equals(name)) {
				return fm;
			}
		}
		if (force) {
			throw new UiException("force:" + name + ",in:" + this);
		}
		return null;
	}

	public void addAction(Path... names) {
		this.actionList.addAll(Arrays.asList(names));

	}

	public List<Path> getActionList() {
		return this.actionList;
	}

	/**
	 * Feb 23, 2013
	 */
	public void addField(FieldModel rt) {
		this.fieldList.add(rt);
	}

}
