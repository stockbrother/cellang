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
import org.cellang.console.ConsolePanel.ConsoleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPanel extends JPanel {
	private static final Logger LOG = LoggerFactory.getLogger(MainPanel.class);

	private ConsolePanel console;

	private OperationContext oc;

	private ViewsPane views;

	private JSplitPane splitPane;
	private int port = 7888;
	private ExecutorService executor;
	JFrame frame;
	Future<Object> consoleFuture;

	public MainPanel(File dataDir) {
		super(new GridLayout(1, 0));
		oc = new OperationContext(dataDir);
		this.executor = Executors.newCachedThreadPool();
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

		frame = new JFrame("Tables");
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				MainPanel.this.console.quit();
			}
		});
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setOpaque(true); // content panes must be opaque
		frame.setContentPane(this);
		this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.splitPane.setResizeWeight(0.8);
		this.splitPane.setContinuousLayout(true);//

		this.add(splitPane);
		views = oc.getViewManager();
		this.splitPane.add(views);
		EntityConfigTableView table = new EntityConfigTableView(oc, oc.getEntityConfigFactory().getEntityConfigList());
		views.addView(table);
		//
		File consoleDataDir = new File(oc.getDataHome(), ".console");
		if (!consoleDataDir.exists()) {
			LOG.info("mkdirs:" + consoleDataDir.getAbsolutePath());//
			consoleDataDir.mkdirs();
		}
		console = new ConsolePanel(consoleDataDir, oc, port);
		this.splitPane.add(console);
		frame.pack();
		frame.setLocationRelativeTo(null);//
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
