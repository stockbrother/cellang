/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 21, 2013
 */
package org.cellang.clwt.commons.client.widget.impl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wu
 * 
 */
public class ListMap<K, V> {
	private List<K> keyList = new ArrayList<K>();
	private Map<K, V> map = new HashMap<K, V>();

	public void put(K k, V v) {
		this.map.put(k, v);
		keyList.remove(k);
		keyList.add(k);
	}

	public V remove(K k) {
		this.keyList.remove(k);
		return this.map.remove(k);

	}

	public List<K> getKeyList() {
		return this.keyList;
	}
	
	public K getNewest(){
		if(this.keyList.isEmpty()){
			return null;
		}
		return this.keyList.get(this.keyList.size()-1);
	}

}
