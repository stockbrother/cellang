/**
 * Jul 1, 2012
 */
package org.cellang.clwt.core.client;

import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.lang.InstanceOf;
import org.cellang.clwt.core.client.lang.InstanceOf.CheckerSupport;
import org.cellang.clwt.core.client.lang.WebObject;
import org.cellang.clwt.core.client.transfer.LogicalChannel;
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

		c.setScheduler(new DefaultScheduler(c));

		//
		this.activeWidgetAndFactory(c);

		// RootI widget
		WebWidgetFactory wf = c.getWidgetFactory(true);

		//WebWidget root = wf.create(RootI.class);
		WebWidget root = new DefaultRootWidget(c,"root");
		// root
		// model
		//c.add(root);// TODO move to SPI.active();

		// client
		ClientObject client = new DefaultClientObject(c, root);
		c.setClient(client);
		//

	}

	protected void activeWidgetAndFactory(Container c) {
		WebWidgetFactory wf = new DefaultWebWidgetFactory(c);
		c.setWidgetFactory(wf);//
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

		InstanceOf.addChecker(new CheckerSupport(ClientObject.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ClientObject;

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
		InstanceOf.addChecker(new CheckerSupport(LogicalChannel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LogicalChannel;
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
