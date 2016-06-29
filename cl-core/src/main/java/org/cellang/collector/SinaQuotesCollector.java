package org.cellang.collector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.cellang.commons.httpclient.HttpClientFactory;
import org.cellang.commons.httpclient.HttpResponseCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SinaQuotesCollector {
	private static final Logger LOG = LoggerFactory.getLogger(SinaQuotesCollector.class);

	private HttpClientFactory clients;

	private String host = "vip.stock.finance.sina.com.cn";

	private int nextPage = 1;

	private int responses = 0;

	private int pageSize = 500;

	private boolean stop = false;

	private File output;

	public SinaQuotesCollector(File output) {
		this.clients = new HttpClientFactory();
		this.clients.setPause(10 * 1000);//
		this.output = output;
	}

	public static void main(String[] args) throws Exception {
		File output = new File("C:\\D\\data\\sina");
		
		new SinaQuotesCollector(output).start();
	}

	public void start() {
		if (!this.output.exists()) {
			this.output.mkdirs();
		}
		HttpResponseCallback hep = new HttpResponseCallback() {

			@Override
			public void onResponse(CloseableHttpResponse response) throws IOException {
				SinaQuotesCollector.this.onResponse(response);
			}
		};

		Iterator<String> uriIt = new Iterator<String>() {

			@Override
			public boolean hasNext() {
				return SinaQuotesCollector.this.hasNext();
			}

			@Override
			public String next() {
				return SinaQuotesCollector.this.next();
			}
		};

		this.clients.get(host, uriIt, hep);

	}

	protected void onResponse(CloseableHttpResponse response) throws IOException {
		this.responses++;
		StringBuffer sb = new StringBuffer();
		for (Header header : response.getAllHeaders()) {
			sb.append("(").append(header.getName() + "=" + header.getValue()).append("),");
		}
		LOG.info("onResponse,statusLine:" + response.getStatusLine() + sb.toString());

		File outputFile = new File(output, "Market_Center.getHQNodeData.p" + this.responses + ".json");

		if (response.getStatusLine().getStatusCode() == 200) {
			Header h = response.getEntity().getContentEncoding();
			File workFile = null;
			int i = 0;
			while (true) {
				workFile = new File(outputFile.getAbsolutePath() + ".work" + (i++));
				if (!workFile.exists()) {
					break;
				}
			}
			FileOutputStream os = new FileOutputStream(workFile);
			response.getEntity().writeTo(os);
			os.close();
			boolean succ = workFile.renameTo(outputFile);
			if (succ) {
				LOG.info("got:" + outputFile.getAbsolutePath());//
			} else {
				LOG.error("cannot rename from:" + workFile.getAbsolutePath() + ",to:" + outputFile.getAbsolutePath());
			}
			Reader fr = new InputStreamReader(new FileInputStream(outputFile));
			char[] line = new char[4];
			int len = fr.read(line);
			if ("null".equals(new String(line, 0, len))) {
				this.stop = true;
			}
		}

	}

	protected String next() {
		StringBuffer sb = new StringBuffer();
		sb.append("/quotes_service/api/json_v2.php/Market_Center.getHQNodeData")//
				.append("?num=").append(this.pageSize)//
				.append("&sort=symbol")//
				.append("&asc=0")//
				.append("&node=hs_a")//
				.append("&symbol=")//
				.append("&_s_r_a=page")//
				.append("&page=").append(this.nextPage)//
		;
		this.nextPage++;
		return sb.toString();
	}

	protected boolean hasNext() {
		return !this.stop;
	}
}
