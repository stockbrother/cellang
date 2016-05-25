package org.cellang.webcommon.mvc.widget;

public interface AnchorWI extends BasicI {

	public void click();

	public void setDisplayText(String txt);
	
	public void setDisplayText(String txt, boolean loc);
	
	public void setTextAndTitle(String txt, String title);
	
	public void setImage(String src);

}
