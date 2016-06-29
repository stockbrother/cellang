package org.cellang.collector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;

import org.cellang.core.util.FileUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVWriter;

public class SinaAllQuotesPreprocessor extends FilesPreprocessor {

	private static Logger LOG = LoggerFactory.getLogger(SinaAllQuotesPreprocessor.class);

	private static String[] HEADERS = new String[] { "symbol", "code", "name", "trade", "pricechange", "changepercent",
			"buy", "sell", "settlement", "open", "high", "low", "volume", "amount", "ticktime", "per", "pb", "mktcap",
			"nmc", "turnoverratio" };
	private static Charset charSet = Charset.forName("gb2312");

	public SinaAllQuotesPreprocessor(File sourceDir, File targetDir) {
		super(sourceDir, targetDir);
	}

	public static void main(String[] args) {
		File data = EnvUtil.getDataDir();
		File from = new File(data, "sina\\all-quotes");
		File to = new File("target\\sinapp\\all-quotes");
		new SinaAllQuotesPreprocessor(from, to).process();

	}

	@Override
	public void process() {
		try {
			this.doProcess();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} //
	}

	private void doProcess() throws IOException {
		if (!this.targetDir.exists()) {
			this.targetDir.mkdirs();
		}
		for (File f : this.sourceDir.listFiles()) {
			if (f.isDirectory()) {
				this.processFileGroup(f);
			}
		}
	}

	private void processFileGroup(File dir) throws IOException {
		String name = dir.getName();

		File output = new File(this.targetDir, name + ".all-quotes" + ".csv");
		if (output.exists()) {
			// ignore.
			LOG.warn("exists" + output.getAbsolutePath());
			return;//
		}

		File outputWork = new File(this.targetDir, name + ".all-quotes" + ".csv.work");

		CSVWriter cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(outputWork), Charset.forName("UTF-8")),
				',', CSVWriter.NO_QUOTE_CHARACTER);
		cw.writeNext(HEADERS);
		int total = 0;
		for (File f : dir.listFiles()) {
			String fname = f.getName();
			if (!fname.endsWith(".json")) {
				LOG.warn("ignore:" + f.getAbsolutePath());
				// ignore
				continue;
			}
			Reader r = FileUtil.newReader(f, charSet);
			//Object jso = JSONValue.parse(r);
			JSONArray jsa = new JSONArray();
			if (jso == null) {
				LOG.warn("null,ignore:" + f.getAbsolutePath());
				// ignore
			} else {
				LOG.warn("process:" + f.getAbsolutePath());
				JSONArray jsa = (JSONArray) jso;
				for (int i = 0; i < jsa.size(); i++) {
					JSONObject jo = (JSONObject) jsa.get(i);
					String[] line = new String[HEADERS.length];
					for (int j = 0; j < HEADERS.length; j++) {
						String key = HEADERS[j];
						line[j] = String.valueOf(jo.get(key));
					}
					cw.writeNext(line);
					total++;
				}
			}

		}
		cw.close();
		if (total == 0) {
			outputWork.delete();
		} else {
			outputWork.renameTo(output);
		}
	}

}
