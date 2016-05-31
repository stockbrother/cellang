/**
 * Jul 1, 2012
 */
package org.cellang.clwt.core.client.impl;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.Scheduler;
import org.cellang.clwt.core.client.WebClient;
import org.cellang.clwt.core.client.WebCorePlugin;
import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.lang.Attacher;
import org.cellang.clwt.core.client.lang.InstanceOf;
import org.cellang.clwt.core.client.lang.WebObject;
import org.cellang.clwt.core.client.lang.InstanceOf.CheckerSupport;
import org.cellang.clwt.core.client.transfer.Endpoint;
import org.cellang.clwt.core.client.widget.DefaultWebWidgetFactory;
import org.cellang.clwt.core.client.widget.WebWidget;
import org.cellang.clwt.core.client.widget.WebWidgetFactory;

/**
 * @author wu
 * 
 */
public class DefaultWebCorePlugin implements WebCorePlugin {

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
		InstanceOf.addChecker(new CheckerSupport(Endpoint.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof Endpoint;
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
