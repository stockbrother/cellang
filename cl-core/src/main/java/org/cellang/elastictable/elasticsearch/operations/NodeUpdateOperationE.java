/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.elasticsearch.operations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.lang.ErrorInfo;
import org.cellang.core.lang.ErrorInfos;
import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.cellang.elastictable.TableRow;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.elasticsearch.ElasticClient;
import org.cellang.elastictable.meta.FieldMeta;
import org.cellang.elastictable.meta.NodeMeta;
import org.cellang.elastictable.operations.NodeUpdateOperationI;
import org.cellang.elastictable.result.BooleanResultI;
import org.cellang.elastictable.support.BooleanResult;
import org.cellang.elastictable.support.NodeOperationSupport;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService.ScriptType;

/**
 * @author wu
 * 
 */
public class NodeUpdateOperationE<W extends RowObject> extends
		NodeOperationSupport<NodeUpdateOperationI<W>, W, BooleanResultI> implements NodeUpdateOperationI<W> {

	private ElasticClient elastic;

	public static final String PK_PROPERTIES = "properties";// node
	
	private String scriptLang = null;//

	// propertiesKey

	/**
	 * @param ds
	 */
	public NodeUpdateOperationE(TableService ds) {
		super(new BooleanResult(ds));
		this.parameter(PK_PROPERTIES, new MapProperties<Object>());
		this.elastic = (ElasticClient) ds;
	}

	public String getUniqueId(boolean force) {
		return (String) this.getParameter(TableRow.PK_UNIQUE_ID, force);
	}

	protected HasProperties<Object> properties() {
		return (HasProperties<Object>) this.parameters.getProperty(PK_PROPERTIES, true);
	}

	@Override
	public NodeUpdateOperationI<W> uniqueId(String uid) {
		this.parameter(TableRow.PK_UNIQUE_ID, uid);
		return this.cast();
	}

	public void validate(HasProperties<Object> pts, NodeMeta nc, ErrorInfos rt) {
		List<String> kl = pts.keyList();// actual data
		for (String k : kl) {
			FieldMeta fc = nc.getField(k, false);
			if (fc == null) {// any data must be defined.
				rt.add(new ErrorInfo("no field:" + k + " is configured by for type:" + nc.getNodeType()));
				continue;
			}
		}

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(BooleanResultI rst) throws Exception {
		//
		Client client = elastic.getClient();
		HasProperties<Object> pts = this.getNodeProperties();
		this.validate(pts, this.nodeConfig, rst.getErrorInfo());
		if (rst.getErrorInfo().hasError()) {
			return;
		}
		// type
		String type = this.getNodeType(true).toString();//
		pts.setProperty(TableRow.PK_TYPE, type);// type field

		// uid
		String uid = this.getUniqueId(true);

		String idx = this.elastic.getIndex();
		// idx = "index1";
		UpdateRequestBuilder urb = client.prepareUpdate(idx, type, uid);
		String script = "";
		
		Map<String,Object> params = new HashMap<String,Object>();
		for (String key : pts.keyList()) {
			Object value = pts.getProperty(key);
			script += "ctx._source." + key + " = " + key;
			script += ";";
			params.put(key, value);
		}
		Script spt = new Script(script,ScriptType.INLINE,scriptLang,params); 
		urb.setScript(spt);

		UpdateResponse response = urb.execute().actionGet();
		// boolean exists = response.getGetResult().exists();
		boolean exists = response.getId() != null;
		rst.set(exists);

		if (this.isRefreshAfterCreate()) {
			this.dataService.refresh();
		}
	}

	public boolean isRefreshAfterCreate() {

		return this.parameters.getPropertyAsBoolean(NodeUpdateOperationI.PK_REFRESH_AFTER_UPDATE, false);

	}

	public HasProperties<Object> getNodeProperties() {
		//
		return (HasProperties<Object>) this.getParameter(PK_PROPERTIES);
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public NodeUpdateOperationI<W> refreshAfterUpdate(boolean refreshAfterCreate) {
		this.parameter(NodeUpdateOperationI.PK_REFRESH_AFTER_UPDATE, refreshAfterCreate);
		return this;
	}

	/*
	 * May 3, 2013
	 */
	@Override
	public NodeUpdateOperationI<W> property(String key, Object value) {
		//
		this.properties().setProperty(key, value);
		return this;
	}

	/*
	 * May 3, 2013
	 */
	@Override
	public String getUniqueId() {
		//
		return (String) this.getParameter(TableRow.PK_UNIQUE_ID);
	}

}
