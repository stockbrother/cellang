package org.cellang.webcommon.mvc;

import org.cellang.webcore.client.lang.WebObject;

public interface ControlManagerI extends WebObject {

	public ControlManagerI addControl(ControlI c);

	public <T extends ControlI> T getControl(Class<T> cls, String name, boolean force);

	public <T extends ControlI> T getControl(Class<T> cls, boolean force);

}
