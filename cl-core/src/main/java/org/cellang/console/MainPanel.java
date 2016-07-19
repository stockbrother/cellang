package org.cellang.console;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.cellang.collector.EnvUtil;
import org.cellang.console.clojure.ClojureConsolePane;
import org.cellang.console.clojure.ClojureConsolePane.ConsoleListener;
import org.cellang.console.clojure.ReplSession;
import org.cellang.console.control.ActionsControl;
import org.cellang.console.control.ActionsPane;
import org.cellang.console.control.ViewActionPane;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.ViewsPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Subclass the GUI part.
 * 
 * @author wuzhen
 *
 */
public class MainPanel extends JPanel {
	private static final Logger LOG = LoggerFactory.getLogger(MainPanel.class);

	private ClojureConsolePane console;

	private OperationContext oc;

	private ViewsPane views;

	private JSplitPane splitPaneTop;
	private JSplitPane splitPaneSub;
	private int port = 7888;
	private ExecutorService executor;
	JFrame frame;
	Future<Object> consoleFuture;
	private ViewActionPane actionManagerPane;

	public MainPanel(File dataDir) {
		super(new GridLayout(1, 0));
		this.executor = Executors.newCachedThreadPool();
		this.setOpaque(true); // content panes must be opaque
		frame = new JFrame("Tables");
		{

			frame.setContentPane(this);
			frame.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					MainPanel.this.console.quit();
				}
			});
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		}
		this.splitPaneTop = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		{

			this.splitPaneTop.setResizeWeight(0.8D);
			this.splitPaneTop.setContinuousLayout(true);//
			{
				this.splitPaneSub = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
				this.splitPaneSub.setResizeWeight(0.6D);//
				this.splitPaneTop.setContinuousLayout(true);//
				{
					views = new ViewsPane();
				}
				this.splitPaneSub.add(views);
				{
					actionManagerPane = new ViewActionPane();
				}
				this.splitPaneSub.add(actionManagerPane);
			}
			this.splitPaneTop.add(this.splitPaneSub);

			oc = new OperationContext(dataDir, views);

			//
			File consoleDataDir = new File(oc.getDataHome(), ".console");
			if (!consoleDataDir.exists()) {
				LOG.info("mkdirs:" + consoleDataDir.getAbsolutePath());//
				consoleDataDir.mkdirs();
			}

			this.console = new ClojureConsolePane(consoleDataDir, oc, port);

			this.splitPaneTop.add(console);
		}

		this.add(splitPaneTop);

		// add control code for integrate the views.
		ActionsControl ac = new ActionsControl(oc.getEntityConfigManager(),this.views, this.actionManagerPane);
	}

	/**
	 * Start the program.
	 */
	public Future<Object> start() {

		try {
			this.oc.start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// open default views for home page.
		oc.home();

		console.addListener(new ConsoleListener() {

			@Override
			public void sessionCreated(ReplSession session) {
				// after the session created, the repl server is ready and the
				// client is ready too, so show the UI.
				//
				frame.setVisible(true);

			}
		});
		Callable<Object> r = new Callable<Object>() {
			public Object call() {
				console.runLoop();
				return Boolean.TRUE;
			}
		};
		frame.pack();
		frame.setLocationRelativeTo(null);//
		consoleFuture = this.executor.submit(r);

		return this.executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				try {
					consoleFuture.get();
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
				oc.close();
				frame.dispose();
				LOG.info("exit,bye!");
				System.exit(0);
				return null;
			}
		});

	}

	/**
	 * The entry point of main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.

		AtomicReference<Future<Object>> future = new AtomicReference<Future<Object>>();
		Semaphore sem = new Semaphore(0);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainPanel mp = new MainPanel(EnvUtil.getDataDir());
				Future<Object> f = mp.start();
				future.set(f);
				sem.release();
			}
		});

		try {
			sem.acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		try {
			future.get().get();
		} catch (InterruptedException | ExecutionException e) {
			LOG.error("", e);
		}
		System.exit(0);//
	}

}
