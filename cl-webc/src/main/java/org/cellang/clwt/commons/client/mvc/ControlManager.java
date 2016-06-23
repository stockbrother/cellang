package org.cellang.clwt.commons.client.mvc;

import org.cellang.clwt.core.client.lang.WebObject;

public interface ControlManager extends WebObject {

	public <T extends Control> ControlManager addControl(Class<T> cls, T c);

	public <T extends Control> T getControl(Class<T> cls, boolean force);

}
