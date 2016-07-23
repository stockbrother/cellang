package org.cellang.console.view.helper;

import javax.swing.JSplitPane;

import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.View;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntitySessionFactory;

public class HelpersPane extends JSplitPane {
	
	public ViewHelperPane viewHelper;
	public EntityObjectHelperPane entityHelper;

	public HelpersPane() {
		super(JSplitPane.VERTICAL_SPLIT);
		this.viewHelper = new ViewHelperPane();
		this.entityHelper = new EntityObjectHelperPane();
		this.add(this.viewHelper);
		this.add(this.entityHelper);
	}

	public void viewSelected(View v) {
		this.viewHelper.setContextObject(v);//
		this.entityHelper.setContextObject(null);//
	}
	
	public void entitySelected(EntityObject eo){
		this.entityHelper.setContextObject(eo);
	}

	public void install(OperationContext oc) {
		this.viewHelper.install(oc);
	}

}
