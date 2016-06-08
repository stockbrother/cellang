package org.cellang.clwt.commons.client.impl;

import org.cellang.clwt.commons.client.CommonsPlugin;
import org.cellang.clwt.commons.client.WidgetCreaterSupport;
import org.cellang.clwt.commons.client.frwk.BodyViewI;
import org.cellang.clwt.commons.client.frwk.BottomViewI;
import org.cellang.clwt.commons.client.frwk.ConsoleViewI;
import org.cellang.clwt.commons.client.frwk.FrwkControlI;
import org.cellang.clwt.commons.client.frwk.FrwkViewI;
import org.cellang.clwt.commons.client.frwk.HeaderViewI;
import org.cellang.clwt.commons.client.frwk.impl.HeaderView;
import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.commons.client.mvc.ViewI;
import org.cellang.clwt.commons.client.mvc.widget.AnchorWI;
import org.cellang.clwt.commons.client.mvc.widget.BooleanEditorI;
import org.cellang.clwt.commons.client.mvc.widget.ButtonI;
import org.cellang.clwt.commons.client.mvc.widget.DateEditorI;
import org.cellang.clwt.commons.client.mvc.widget.DateWI;
import org.cellang.clwt.commons.client.mvc.widget.EditorI;
import org.cellang.clwt.commons.client.mvc.widget.EnumEditorI;
import org.cellang.clwt.commons.client.mvc.widget.ErrorInfosWidgetI;
import org.cellang.clwt.commons.client.mvc.widget.ImageI;
import org.cellang.clwt.commons.client.mvc.widget.IntegerEditorI;
import org.cellang.clwt.commons.client.mvc.widget.LabelI;
import org.cellang.clwt.commons.client.mvc.widget.ListI;
import org.cellang.clwt.commons.client.mvc.widget.PropertiesEditorI;
import org.cellang.clwt.commons.client.mvc.widget.PropertiesEditorI.PropertyModel;
import org.cellang.clwt.commons.client.mvc.widget.StackWI;
import org.cellang.clwt.commons.client.mvc.widget.StringEditorI;
import org.cellang.clwt.commons.client.mvc.widget.TableI;
import org.cellang.clwt.commons.client.mvc.widget.ViewReferenceI;
import org.cellang.clwt.commons.client.mvc.widget.WindowPanelWI;
import org.cellang.clwt.commons.client.widget.BarWidgetI;
import org.cellang.clwt.commons.client.widget.MenuItemWI;
import org.cellang.clwt.commons.client.widget.MenuWI;
import org.cellang.clwt.commons.client.widget.PanelWI;
import org.cellang.clwt.commons.client.widget.TabWI;
import org.cellang.clwt.commons.client.widget.TabberWI;
import org.cellang.clwt.commons.client.widget.impl.BooleanEditorImpl;
import org.cellang.clwt.commons.client.widget.impl.DateEditorImpl;
import org.cellang.clwt.commons.client.widget.impl.EnumEditorImpl;
import org.cellang.clwt.commons.client.widget.impl.IntegerEditorImpl;
import org.cellang.clwt.commons.client.widget.impl.PropertiesEditorImpl;
import org.cellang.clwt.commons.client.widget.impl.StringEditorImpl;
import org.cellang.clwt.commons.client.widget.impl.bar.BarWidgetImpl;
import org.cellang.clwt.commons.client.widget.impl.basic.AnchorWImpl;
import org.cellang.clwt.commons.client.widget.impl.basic.ButtonImpl;
import org.cellang.clwt.commons.client.widget.impl.basic.DateWImpl;
import org.cellang.clwt.commons.client.widget.impl.basic.ImageImpl;
import org.cellang.clwt.commons.client.widget.impl.basic.LabelImpl;
import org.cellang.clwt.commons.client.widget.impl.error.ErrorInfosWidgetImpl;
import org.cellang.clwt.commons.client.widget.impl.list.ListImpl;
import org.cellang.clwt.commons.client.widget.impl.menu.MenuWImpl;
import org.cellang.clwt.commons.client.widget.impl.panel.PanelWImpl;
import org.cellang.clwt.commons.client.widget.impl.stack.StackWImpl;
import org.cellang.clwt.commons.client.widget.impl.tab.TabberWImpl;
import org.cellang.clwt.commons.client.widget.impl.table.TableImpl;
import org.cellang.clwt.commons.client.widget.impl.wpanel.WindowPanelWImpl;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.lang.InstanceOf;
import org.cellang.clwt.core.client.lang.InstanceOf.CheckerSupport;
import org.cellang.clwt.core.client.widget.WebWidget;
import org.cellang.clwt.core.client.widget.WebWidgetFactory;
import org.cellang.webc.main.client.LoginControlI;
import org.cellang.webc.main.client.LoginViewI;

public class DefaultCommonsPlugin implements CommonsPlugin {

	@Override
	public void active(Container c) {

		this.activeInstaneOf(c);
		WebWidgetFactory wf = c.get(WebWidgetFactory.class, true);
		wf.addCreater(new WidgetCreaterSupport<AnchorWI>(AnchorWI.class) {
			@Override
			public AnchorWI create(Container c, String name, HasProperties<Object> pts) {

				return new AnchorWImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ListI>(ListI.class) {
			@Override
			public ListI create(Container c, String name, HasProperties<Object> pts) {

				return new ListImpl(c, name, pts);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<PanelWI>(PanelWI.class) {
			@Override
			public PanelWI create(Container c, String name, HasProperties<Object> pts) {

				return new PanelWImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<StackWI>(StackWI.class) {
			@Override
			public StackWI create(Container c, String name, HasProperties<Object> pts) {

				return new StackWImpl(c, name, pts);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<TabberWI>(TabberWI.class) {
			@Override
			public TabberWI create(Container c, String name, HasProperties<Object> pts) {

				return new TabberWImpl(c, name, pts);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<TableI>(TableI.class) {
			@Override
			public TableI create(Container c, String name, HasProperties<Object> pts) {

				return new TableImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ImageI>(ImageI.class) {
			@Override
			public ImageI create(Container c, String name, HasProperties<Object> pts) {

				return new ImageImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ButtonI>(ButtonI.class) {
			@Override
			public ButtonI create(Container c, String name, HasProperties<Object> pts) {

				return new ButtonImpl(c, name);

			}
		});
		this.activeWidgetCreater(c, wf);

	}

	public void activeWidgetCreater(Container c, WebWidgetFactory wf) {
		wf.addCreater(new WidgetCreaterSupport<LabelI>(LabelI.class) {
			@Override
			public LabelI create(Container c, String name, HasProperties<Object> pts) {

				return new LabelImpl(c, name);

			}
		});
		//
		wf.addCreater(new WidgetCreaterSupport<MenuWI>(MenuWI.class) {
			@Override
			public MenuWI create(Container c, String name, HasProperties<Object> pts) {

				return new MenuWImpl(c, name);

			}
		});

		// Editors

		wf.addCreater(new WidgetCreaterSupport<StringEditorI>(StringEditorI.class) {
			@Override
			public StringEditorI create(Container c, String name, HasProperties<Object> pts) {

				return new StringEditorImpl(c, name, pts);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<BooleanEditorI>(BooleanEditorI.class) {
			@Override
			public BooleanEditorI create(Container c, String name, HasProperties<Object> pts) {

				return new BooleanEditorImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<IntegerEditorI>(IntegerEditorI.class) {
			@Override
			public IntegerEditorI create(Container c, String name, HasProperties<Object> pts) {

				return new IntegerEditorImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<DateEditorI>(DateEditorI.class) {
			@Override
			public DateEditorI create(Container c, String name, HasProperties<Object> pts) {

				return new DateEditorImpl(c, name, pts);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<ErrorInfosWidgetI>(ErrorInfosWidgetI.class) {
			@Override
			public ErrorInfosWidgetI create(Container c, String name, HasProperties<Object> pts) {

				return new ErrorInfosWidgetImpl(c, name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<BarWidgetI>(BarWidgetI.class) {
			@Override
			public BarWidgetI create(Container c, String name, HasProperties<Object> pts) {

				return new BarWidgetImpl(c, name);

			}
		});

	}

	public void activeCreater2(Container c, WebWidgetFactory wf) {
		
		wf.addCreater(new WidgetCreaterSupport<PropertiesEditorI>(PropertiesEditorI.class) {
			@Override
			public PropertiesEditorI create(Container c, String name, HasProperties<Object> pts) {

				return new PropertiesEditorImpl(c, name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<WindowPanelWI>(WindowPanelWI.class) {
			@Override
			public WindowPanelWI create(Container c, String name, HasProperties<Object> pts) {

				return new WindowPanelWImpl(c, name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<EnumEditorI>(EnumEditorI.class) {
			@Override
			public EnumEditorI create(Container c, String name, HasProperties<Object> pts) {

				return new EnumEditorImpl(c, name);

			}
		});


		wf.addCreater(new WidgetCreaterSupport<DateWI>(DateWI.class) {
			@Override
			public DateWI create(Container c, String name, HasProperties<Object> pts) {

				return new DateWImpl(c, name);

			}
		});

	}

	public void activeInstaneOf(Container c) {
		InstanceOf.addChecker(new CheckerSupport(AnchorWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof AnchorWI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(ButtonI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ButtonI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(Control.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof Control;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ControlManager.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ControlManager;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(ViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(TabberWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof TabberWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(TabWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof TabWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(StackWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof StackWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PanelWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PanelWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ImageI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ImageI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(ViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ViewI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(WebWidget.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WebWidget;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(EditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PropertiesEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PropertiesEditorI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(BooleanEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BooleanEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(IntegerEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof IntegerEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(DateEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof DateEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(MenuWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MenuWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(MenuItemWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MenuItemWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(StringEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof StringEditorI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(WindowPanelWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WindowPanelWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ConsoleViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ConsoleViewI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(PropertyModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PropertyModel;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(LoginControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginControlI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(HeaderView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderView;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(BarWidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BarWidgetI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ViewReferenceI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ViewReferenceI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(EnumEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EnumEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ErrorInfosWidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ErrorInfosWidgetI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(FrwkControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FrwkControlI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(DateWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof DateWI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(FrwkViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FrwkViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(BodyViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BodyViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(HeaderViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderViewI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(LoginViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginViewI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(BottomViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BottomViewI;

			}

		});

	}

}
