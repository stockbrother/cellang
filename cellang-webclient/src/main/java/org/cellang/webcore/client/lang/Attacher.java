package org.cellang.webcore.client.lang;

public interface Attacher {

	public void owner(WebObject obj);

	public WebObject getOwner(boolean force);

	public void ownerAttached();

	public void ownerDettached();

}