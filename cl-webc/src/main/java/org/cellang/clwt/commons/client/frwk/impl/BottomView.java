/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package org.cellang.clwt.commons.client.frwk.impl;

import org.cellang.clwt.commons.client.frwk.BottomViewI;
import org.cellang.clwt.commons.client.frwk.HeaderItemEvent;
import org.cellang.clwt.commons.client.mvc.simple.SimpleView;
import org.cellang.clwt.commons.client.widget.AnchorWI;
import org.cellang.clwt.commons.client.widget.BarWidgetI;
import org.cellang.clwt.commons.client.widget.LabelI;
import org.cellang.clwt.commons.client.widget.ListI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.ClickEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Path;

/**
 * @author wu
 * 
 */
public class BottomView extends SimpleView implements BottomViewI {

	private ListI outer;

	private BarWidgetI bar;

	private LabelI copyRight;

	/**
	 * @param ctn
	 */
	public BottomView(Container c) {
		super(c, "bottom");
		this.outer = this.factory.create(ListI.class, this.getChildName("outer"), ListI.PK_IS_VERTICAL,
				Boolean.TRUE);
		this.outer.getElement().addClassName("bottom-list");
		this.outer.parent(this);
		{
			//
			this.bar = this.factory.create(BarWidgetI.class);
			this.bar.getElementWrapper().addClassName("bottom-bar");
			this.bar.parent(this.outer);//
		}
		{//
			this.copyRight = this.factory.create(LabelI.class);
			this.copyRight.getElementWrapper().addClassName("bottom-copyright");
			this.copyRight.parent(this.outer);
			this.copyRight.setText("/copyright", true);
		}
	}

	@Override
	public void addItem(final Path path) {
		//
		AnchorWI aw = this.factory.create(AnchorWI.class);
		// localized
		String txt = this.getClient(true).localized(path.toString());
		aw.setDisplayText(txt);
		aw.getElement().addClassName("bottom-item");

		this.bar.addItem(BarWidgetI.P_CENTER, aw);
		aw.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent t) {
				//
				BottomView.this.onClick(path);
			}
		});

	}

	protected void onClick(Path path) {
		new HeaderItemEvent(this, HeaderItemEvent.TYPE.getAsPath().concat(path)).dispatch();

	}

}
