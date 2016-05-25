/**
 * Jul 17, 2012
 */
package org.cellang.webcore.client.data;

import java.util.ArrayList;
import java.util.List;

import org.cellang.webcore.client.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public class ErrorInfosData {
	private List<ErrorInfoData> errorInfoList = new ArrayList<ErrorInfoData>();

	public List<ErrorInfoData> getErrorInfoList() {
		return this.errorInfoList;
	}

	public boolean hasError() {
		return !this.errorInfoList.isEmpty();
	}

	public ErrorInfosData addAll(ErrorInfosData ei) {
		this.errorInfoList.addAll(ei.errorInfoList);
		return this;
	}

	public ErrorInfosData add(ErrorInfoData ei) {
		this.errorInfoList.add(ei);
		return this;
	}

	public boolean containsErrorCode(String code) {
		return !this.getErrorInfoListByCode(code).isEmpty();

	}

	public List<ErrorInfoData> getErrorInfoListByCode(String code) {
		List<ErrorInfoData> rt = new ArrayList<ErrorInfoData>();
		for (ErrorInfoData eid : this.errorInfoList) {
			if (eid.isCode(code)) {
				rt.add(eid);
			}
		}
		return rt;
	}

	/* */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ErrorInfosData)) {
			return false;
		}
		return ObjectUtil.nullSafeEquals(this.errorInfoList,
				((ErrorInfosData) obj).errorInfoList);
	}

	/* */
	@Override
	public String toString() {
		return this.errorInfoList.toString();

	}

}
