/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elastictable.elasticsearch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.cellang.core.lang.HasProperties;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.PropertyConverter;
import org.cellang.elastictable.meta.FieldMeta;
import org.cellang.elastictable.meta.FieldType;
import org.cellang.elastictable.meta.NodeMeta;
import org.cellang.elastictable.support.NodeSupport;
import org.elasticsearch.search.SearchHit;

/**
 * @author wu
 * 
 */
public class SearchHitNode extends NodeSupport {
	public static Map<FieldType, PropertyConverter> pcMap = new HashMap<FieldType, PropertyConverter>();
	static {
		pcMap.put(FieldType.DATE, new PropertyConverter<String, Date>() {

			@Override
			public Date convertFromStore(String s) {
				//
				return ElasticTimeFormat.parse(s);

			}
		});
	}

	/**
	 * @param type
	 * @param uid
	 */
	public SearchHitNode(String nodeType, TableService ds, SearchHit sh) {
		super(nodeType, "todo");

		NodeMeta nc = ds.getConfigurations().getNodeConfig(nodeType, true);
		Map<String, Object> old = sh.sourceAsMap();
		SearchHitNode.convertPropertiesFromStore(old, nc, this);
	}
	
	public static void convertPropertiesFromStore(Map<String,Object> old, NodeMeta nm, HasProperties<Object> pts){

		for (Map.Entry<String, Object> me : old.entrySet()) {

			String key = me.getKey();
			Object value = me.getValue();
			value = SearchHitNode.convertFromStore(nm, key, value);

			pts.setProperty(key, value);
		}
		
	}

	public static Object convertFromStore(NodeMeta nm, String key, Object value) {
		FieldMeta fm = nm.getField(key, false);

		if (fm == null) {// not defined,TODO throw expcetion.
			return value;
		}
		return convertFromStore(fm, value);
	}

	public static Object convertFromStore(FieldMeta fm, Object value) {
		PropertyConverter pc = pcMap.get(fm.getType());
		Object rt = value;
		if (pc != null) {
			rt = pc.convertFromStore(value);
		}
		return rt;
	}

}
