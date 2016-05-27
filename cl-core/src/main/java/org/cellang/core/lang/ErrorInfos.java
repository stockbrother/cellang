/**
 * Jul 17, 2012
 */
package org.cellang.core.lang;

import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.ObjectUtil;
import org.cellang.commons.lang.Path;

/**
 * @author wu
 * 
 */
public class ErrorInfos implements ValueI {
	private List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();

	public List<ErrorInfo> getErrorInfoList() {
		return this.errorInfoList;
	}

	public boolean hasError() {
		return !this.errorInfoList.isEmpty();
	}

	public ErrorInfos addAll(ErrorInfos eis) {
		for (ErrorInfo ei : eis.getErrorInfoList()) {
			this.add(ei);
		}
		return this;
	}

	public ErrorInfos addError(Path ecode){
		return this.add(new ErrorInfo(ecode));
	}
	@Deprecated
	public ErrorInfos addError(String code) {
		return this.addError(code, null);
	}

	public ErrorInfos addError(String code, String msg) {
		return this.add(new ErrorInfo(code, msg));
	}

	public ErrorInfos add(ErrorInfo ei) {
		this.errorInfoList.add(ei);
		return this;
	}

	/* */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ErrorInfos)) {
			return false;
		}
		return ObjectUtil.isNullSafeEquals(this.errorInfoList,
				((ErrorInfos) obj).errorInfoList);
	}

	/* */
	@Override
	public String toString() {
		return this.errorInfoList.toString();

	}

}
