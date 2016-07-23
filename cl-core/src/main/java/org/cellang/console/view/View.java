package org.cellang.console.view;

import java.awt.Component;

import org.cellang.console.HasDelagates;

public interface View extends HasDelagates {

	public String getTitle();

	public Component getComponent();

	public String getId();	

}
