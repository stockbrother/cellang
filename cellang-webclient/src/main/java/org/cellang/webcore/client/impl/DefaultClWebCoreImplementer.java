/**
 * Jul 1, 2012
 */
package org.cellang.webcore.client.impl;

import org.cellang.webcore.client.ClWebCoreImplementer;
import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.Scheduler;
import org.cellang.webcore.client.WebClient;
import org.cellang.webcore.client.event.EventBus;
import org.cellang.webcore.client.lang.Attacher;
import org.cellang.webcore.client.lang.InstanceOf;
import org.cellang.webcore.client.lang.WebObject;
import org.cellang.webcore.client.lang.InstanceOf.CheckerSupport;
import org.cellang.webcore.client.transferpoint.TransferPoint;
import org.cellang.webcore.client.widget.DefaultWebWidgetFactory;
import org.cellang.webcore.client.widget.WebWidget;
import org.cellang.webcore.client.widget.WebWidgetFactory;

/**
 * @author wu
 * 
 */
public class DefaultClWebCoreImplementer implements ClWebCoreImplementer {

	/* */
	@Override
	public void active(Container c) {

		this.activeInstanceOfChecker();

		c.add(new DefaultScheduler(c));

		//
		this.activeWidgetAndFactory(c);

		// RootI widget
		WebWidgetFactory wf = c.get(WebWidgetFactory.class, true);

		//WebWidget root = wf.create(RootI.class);
		WebWidget root = new DefaultRootWidget(c,"root");
		// root
		// model
		c.add(root);// TODO move to SPI.active();

		// client
		WebClient client = new UiClientImpl(c, root);
		c.add(client);
		//

	}

	protected void activeWidgetAndFactory(Container c) {
		WebWidgetFactory wf = new DefaultWebWidgetFactory(c);
		c.add(wf);//
//		wf.addCreater(new AbstractWebWidgetCreater<RootI>(RootI.class) {
//
//			@Override
//			public RootI create(Container c, String name, HasProperties<Object> pts) {
//				return new DefaultRootWidget(c, name);
//			}
//		});
	}

	protected void activeInstanceOfChecker() {

		InstanceOf.addChecker(new CheckerSupport(WebObject.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WebObject;

			}
		});

		InstanceOf.addChecker(new CheckerSupport(WebClient.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WebClient;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(WebWidget.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WebWidget;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(WebWidgetFactory.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WebWidgetFactory;

			}
		});

		InstanceOf.addChecker(new CheckerSupport(EventBus.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EventBus;

			}
		});

		InstanceOf.addChecker(new CheckerSupport(Attacher.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof Attacher;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(TransferPoint.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof TransferPoint;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(Scheduler.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof Scheduler;
			}
		});

	}

}
