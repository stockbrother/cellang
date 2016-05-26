package org.cellang.clwt.commons.client.mvc;

import org.cellang.clwt.core.client.lang.WebObject;

public interface ControlManager extends WebObject {

	public ControlManager addControl(Control c);

	public <T extends Control> T getControl(Class<T> cls, String name, boolean force);

	public <T extends Control> T getControl(Class<T> cls, boolean force);

}
