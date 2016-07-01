package org.cellang.commons.commandline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.cellang.commons.commandline.StackConsoleReader.LineRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author wuzhen
 * 
 */
public abstract class AbstractComandLineApplication<T extends CommandLineApplication> implements CommandLineApplication {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractComandLineApplication.class);

	private StackConsoleReader reader;

	private ExecutorService executor;

	private Semaphore running = new Semaphore(0);

	private CommandLineParser parser = new BasicParser();

	private Map<String, CommandType> commandMap = new HashMap<String, CommandType>();

	private StackConsoleWriter writer;

	private boolean pushStdInput = true;

	private boolean pushStdOutput = true;

	private String prompt = "$";

	private int commandCounter;

	private boolean echo = true;

	private boolean printLine;

	private boolean isRunning;

	public AbstractComandLineApplication() {

		this.reader = new StackConsoleReader();
		this.writer = new StackConsoleWriter();
		this.executor = Executors.newSingleThreadExecutor();//
	}

	@Override
	public CommandLineApplication prompt(String p) {
		this.prompt = p;
		this.prompt();
		return this;
	}

	public void run() {
		try {
			this.runInternal();
		} catch (Throwable t) {
			// TODO exit
			LOG.error("todo exit", t);
		}
	}

	public void runInternal() {
		LOG.info("welcome to ...!");
		try {
			this.running.acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		LOG.info("... graph console!");
		while (this.isRunning) {

			this.writer.write(this.prompt);

			LineRead line = this.reader.readLine();
			if (this.echo) {
				this.writer.writeLine(line.getLine());
			}

			this.processLine(this.commandCounter, line);

			this.commandCounter++;
		}
		LOG.warn("exiting graph console");

	}

	public void prompt() {
		this.writer.write(this.prompt);
	}

	public void processLine(int idx, LineRead lr) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("processing line:" + lr.getLine() + ",idx:" + idx);
		}

		try {
			String line = lr.getLine();
			String[] args = line.split(" ");
			String cmd = args[0];
			cmd = cmd.trim();
			CommandType command = this.commandMap.get(cmd);
			CommandLine cl = null;
			String[] args2 = null;
			if (command == null) {
				command = this.getHelpCommand();
				args2 = new String[] {};
			} else {
				args2 = new String[args.length - 1];
				System.arraycopy(args, 1, args2, 0, args2.length);
				cl = this.parseCommandLine(command, args2, false);
			}
			if (cl == null) {// not found the command or format error.
				command = this.getHelpCommand();
				args2 = new String[] { cmd };
				cl = this.parseCommandLine(command, args2, true);
			}
			CommandAndLine cnl = new CommandAndLine(this, idx, command, cl);
			this.processLine(cnl);
		} finally {
			if (this.printLine) {
				this.writer.writeLine();
			}
		}

	}

	public abstract void processLine(CommandAndLine cl);
	
	public CommandLine parseCommandLine(CommandType command, String[] args, boolean force) {

		CommandLine rt = null;
		try {

			Options options = command.getOptions();
			rt = this.parser.parse(options, args);

		} catch (ParseException e) {
			LOG.warn("parse error for cmd:" + command.getName(), e);
			if (force) {
				throw new RuntimeException(e);
			}
		}
		return rt;
	}

	public CommandType getHelpCommand() {
		return this.commandMap.get("help");//
	}

	public void printHelp(Options options) {

	}

	public void start() {

		if (this.pushStdInput) {
			this.reader.push(new DefaultConsoleReader());
		}
		if (this.pushStdOutput) {
			this.writer.push(new DefaultConsoleWriter());
		}

		Future<String> fs = this.executor.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				AbstractComandLineApplication.this.run();
				return null;

			}
		});

	}

	@Override
	public CommandLineApplication pushReader(CommandLineReader cr) {
		return this.pushReader(cr, true);
	}

	@Override
	public CommandLineApplication pushReader(CommandLineReader cr, boolean popWhenClosed) {

		this.reader.push(cr, popWhenClosed);

		return this;
	}

	@Override
	public CommandLineApplication pushWriter(CommandLineWriter cw) {
		this.writer.push(cw);
		return this;

	}

	@Override
	public CommandLineWriter peekWriter() {
		return this.writer.peek();
	}

	@Override
	public CommandLineWriter popWriter() {

		return this.writer.pop();
	}

	@Override
	public List<CommandType> getCommandList() {

		List<String> cmdL = new ArrayList<String>(this.commandMap.keySet());

		String[] names = cmdL.toArray(new String[cmdL.size()]);

		// sorted
		Arrays.sort(names);
		List<CommandType> rt = new ArrayList<CommandType>();
		for (int i = 0; i < names.length; i++) {
			CommandType cmd = this.commandMap.get(names[i]);
			rt.add(cmd);

		}

		return rt;
	}

	@Override
	public CommandType getCommand(String name) {
		// TODO Auto-generated method stub
		return this.commandMap.get(name);
	}

	@Override
	public void printLine(boolean printLine) {
		this.printLine = printLine;
	}

}
