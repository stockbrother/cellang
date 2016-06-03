/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cellang.core.lang.ErrorInfo;
import org.cellang.core.lang.ErrorInfos;
import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.cellang.elastictable.meta.FieldMeta;
import org.cellang.elastictable.meta.NodeMeta;
import org.cellang.elastictable.operations.NodeCreateOperationI;

/**
 * @author wu
 * 
 */
public class RowObject extends PropertiesWrapper<Object, HasProperties<Object>> {

	protected String nodeType;

	protected TableService dataService;

	protected NodeCreateOperationI createOperation;

	protected static Set<String> PK_SYSTEM = new HashSet<String>();
	static {
		PK_SYSTEM.add(TableRow.PK_ID);
		PK_SYSTEM.add(TableRow.PK_TIMESTAMP);
		PK_SYSTEM.add(TableRow.PK_TYPE);
		PK_SYSTEM.add(TableRow.PK_UNIQUE_ID);
	}

	public RowObject(String type) {
		this.nodeType = type;
	}

	public <T extends RowObject> T forOp(TableService ds) {
		this.dataService = ds;
		return (T) this;
	}

	public <T extends RowObject> T forCreate(TableService ds) {
		this.forOp(ds);
		this.createOperation = ds.prepareOperation(NodeCreateOperationI.class);
		this.createOperation.nodeType(this.getClass());// this.target will
														// connect to the
		// operation's paramters.
		this.createOperation.wrapper(this);

		return (T) this;

	}
	

	public void validate(NodeMeta nc, ErrorInfos rt) {
		List<String> kl = this.target.keyList();// actual data
		Set<String> kset = new HashSet<String>(nc.keySet());// metaInfo data
		for (String k : kl) {
			FieldMeta fc = nc.getField(k, false);
			if (fc == null) {// any data must be defined.
				rt.add(new ErrorInfo("no field:" + k + " is configured by for type:" + nc.getNodeType()));
				continue;
			}
		}
		for (String k : kset) {// metaInfo:
			FieldMeta fc = nc.getField(k, false);
			Object value = this.target.getProperty(k);//

			if (value == null && fc.isManditory()) {
				rt.add(new ErrorInfo("field:" + k + " is manditory for type:" + nc.getNodeType()));
			}

			fc.validate(this, rt);
		}

	}

	/**
	 * @param signupRequest
	 * @param pts
	 */
	public RowObject(String ntype, HasProperties<Object> pts) {
		this(ntype);
		this.attachTo(pts);
	}


	public <T extends PropertiesWrapper<Object, HasProperties<Object>>> T attachTo(HasProperties<Object> pts,
			TableService dataService) {

		this.dataService = dataService;
		return this.attachTo(pts);

	}

	public HasProperties<Object> getUserProperties() {
		HasProperties<Object> rt = new MapProperties<Object>();
		for (String key : this.target.keyList()) {
			if (PK_SYSTEM.contains(key)) {
				continue;
			}
			Object value = this.target.getProperty(key);
			rt.setProperty(key, value);
		}
		return rt;
	}

	@Override
	public <T extends PropertiesWrapper<Object, HasProperties<Object>>> T attachTo(HasProperties<Object> pts) {
		super.attachTo(pts);//

		return (T) this;
	}

	public String getUniqueId() {
		return (String) this.target.getProperty(TableRow.PK_UNIQUE_ID);
	}

	public String getId() {
		return (String) this.target.getProperty(TableRow.PK_ID);//
	}

	public void setId(String id) {
		this.target.setProperty(TableRow.PK_ID, id);
	}

	public String save() {
		return this.save(false);
	}

	public String save(boolean refreshAfterCreate) {

		ExecuteResult rst = this.createOperation.refreshAfterCreate(refreshAfterCreate).execute().getResult()
				.assertNoError();

		return (String) rst.get(true);
	}

	/**
	 * Oct 28, 2012
	 */
	public String getNodeType() {
		//
		return this.nodeType;
	}

	public Object getProperty(String key) {

		return this.target.getProperty(key);
	}

	public Object getProperty(String key, boolean force) {

		Object rt = this.target.getProperty(key);
		if (force && rt == null) {
			throw new RuntimeException("force :" + key);
		}
		return rt;
	}

	public <T> T getProperty(Class<T> cls, String key, T def) {
		Object rt = this.getProperty(key);

		if (rt == null) {
			return def;
		}
		return (T) rt;
	}

	public String getPropertyAsString(String key) {
		return (String) this.getProperty(key);
	}

	// NOTE type convert is needed, see the TableRow's impl class
	public Date getTimestamp() {
		return (Date) this.target.getProperty(TableRow.PK_TIMESTAMP);//
	}

	/**
	 * Nov 2, 2012
	 */

}
