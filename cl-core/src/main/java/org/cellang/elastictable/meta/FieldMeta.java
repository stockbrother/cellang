/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 2, 2012
 */
package org.cellang.elastictable.meta;

import java.util.ArrayList;
import java.util.List;

import org.cellang.core.lang.ErrorInfos;
import org.cellang.elastictable.RowObject;

/**
 * @author wu
 * 
 */
public class FieldMeta {

	private NodeMeta nodeConfig;

	private String name;

	private FieldType type;

	private boolean manditory;// manditory when create.

	private AnalyzerType analyzer;

	private List<FieldValidatorI> validatorList = new ArrayList<FieldValidatorI>();

	/**
	 * @param name2
	 */
	public FieldMeta(NodeMeta nc, String name2) {
		this(nc, name2, true);//
	}

	public FieldMeta(NodeMeta nc, String name2, boolean mand) {
		this(nc, name2, mand, null);
	}

	public FieldMeta(NodeMeta nc, String name2, boolean mand, FieldType type) {
		this.nodeConfig = nc;
		this.name = name2;
		this.manditory = mand;
		this.type = type == null ? FieldType.STRING : type;
	}

	public void addValiditor(FieldValidatorI fv) {
		this.validatorList.add(fv);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the manditory
	 */
	public boolean isManditory() {
		return manditory;
	}

	public void validate(RowObject nw, ErrorInfos eis) {
		for (FieldValidatorI fv : this.validatorList) {
			fv.validate(this, nw, eis);
		}
	}

	/**
	 * @return the nodeConfig
	 */
	public NodeMeta getNodeConfig() {
		return nodeConfig;
	}

	/**
	 * @return the analyzer
	 */
	public AnalyzerType getAnalyzer() {
		return analyzer;
	}

	/**
	 * @param analyzer
	 *            the analyzer to set
	 */
	public void setAnalyzer(AnalyzerType analyzer) {
		this.analyzer = analyzer;
	}

	/**
	 * Jan 20, 2013
	 */
	public FieldType getType() {
		//
		return type;
	}

	public FieldMeta manditory(boolean m) {
		this.manditory = m;
		return this;
	}

	/**
	 * @param manditory
	 *            the manditory to set
	 */
	public void setManditory(boolean manditory) {
		this.manditory = manditory;
	}

	/**
	 * Jan 20, 2013
	 */
	public FieldMeta type(FieldType type) {
		this.type = type;
		return this;
	}
}
