package org.cellang.collector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.cellang.core.entity.CorpInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

/**
 * <code>
		HTTP/1.1 200 OK
		Server=nginx
		Date=Tue, 28 Jun 2016 06:47:07 GMT
		Content-Type=text/csv ; charset=gbk
		Transfer-Encoding=chunked
		Content-Disposition=attachment; filename=zcfzb000001.csv
		P-Via=X0.1
		Expires=Tue, 28 Jun 2016 06:50:07 GMT
		Cache-Control=max-age=180
		P-Via=X20.204
		P-Cache=EXPIRED
		P-Via=X51.176
		via=product-midproxy13.sa.cnc.bj
		via=yz_proxy1
		Proxy-Connection=Keep-Alive
		Connection=Keep-Alive
		Age=0
</code> This code collect data from http site and save to the target folder.
 * 
 * @author wuzhen
 *
 */
public class NeteaseCollector {
	private static final Logger LOG = LoggerFactory.getLogger(NeteaseCollector.class);

	public static String TYPE_zcfzb = "zcfzb";
	public static String TYPE_lrb = "lrb";
	public static String TYPE_xjllb = "xjllb";
	private File dir163;

	private CloseableHttpClient httpclient;

	private HttpHost target;

	private HttpHost proxy;

	private RequestConfig config;

	private List<CorpInfoEntity> orgInfoList;

	private long lastAccessTimestamp;

	private long minInterval = 15 * 1000;

	private String firstFrom;

	private String[] types;

	public NeteaseCollector(File dir) {
		this.dir163 = dir;
	}

	public NeteaseCollector types(String... types) {

		this.types = types;

		return this;
	}

	public void loadCorpInfoEntity(List<CorpInfoEntity> rt, File indexFile) {

		try {
			CSVReader reader = new CSVReader(new FileReader(indexFile));

			String[] next = reader.readNext();

			// skip header
			while (true) {
				next = reader.readNext();
				if (next == null) {
					break;
				}
				String code = next[0].trim();
				String name = next[1].trim();
				CorpInfoEntity ce = new CorpInfoEntity();
				ce.setId(code);
				ce.setCode(code);
				ce.setName(name);
				rt.add(ce);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void start() throws Exception {
		if (this.types == null || types.length == 0) {
			throw new RuntimeException("no types specified.");
		}

		List<CorpInfoEntity> rt = new ArrayList<CorpInfoEntity>();
		File indexDir = EnvUtil.getDir1();
		File indexFile1 = new File(indexDir, "index1.corplist.csv");
		File indexFile2 = new File(indexDir, "index2.corplist.csv");

		this.loadCorpInfoEntity(rt, indexFile1);
		this.loadCorpInfoEntity(rt, indexFile2);

		this.orgInfoList = rt;
		httpclient = HttpClients.custom().build();
		try {
			// http://quotes.money.163.com/service/zcfzb_600305.html
			target = new HttpHost(EnvUtil.getHttpHost163(), 80, "http");
			proxy = new HttpHost(EnvUtil.getProxyHome(), EnvUtil.getProxyPort());
			config = RequestConfig.custom().setProxy(proxy).build();

			boolean running = false;
			for (CorpInfoEntity oi : this.orgInfoList) {
				if (!running) {
					if (this.firstFrom == null || oi.getCode().equals(this.firstFrom)) {
						running = true;
					} else {
						LOG.warn("wait:" + this.firstFrom + ",skip:" + oi.getCode());//
					}
				}

				if (running) {
					this.collectFor(oi);
				}
			}

		} finally {
			httpclient.close();
		}
	}

	public void collectFor(CorpInfoEntity oi) throws Exception {

		for (String type : types) {
			File typeDir = new File(this.dir163, type);

			String fname = oi.getCode().substring(0, 4);//
			File areaDir = new File(typeDir, fname);
			if (!areaDir.exists()) {
				areaDir.mkdirs();
			}
			File file = new File(areaDir, type + oi.getCode() + ".csv");
			if (file.exists()) {
				LOG.info("file exist:" + file);
			} else {
				waitAndDoCollectFor(oi, type, file);
			}
		}

	}

	private void waitAndDoCollectFor(CorpInfoEntity oi, String type, File outputFile) throws Exception {
		int retry = 3;
		while (retry > 0) {
			LOG.info("wait..." + this.minInterval + "(ms) before next http accesss.");
			while (true) {
				long pass = System.currentTimeMillis() - this.lastAccessTimestamp;
				if (pass < minInterval) {
					LOG.trace(".");
					Thread.sleep(1000);
					continue;
				}
				LOG.info("it's ready for next http access.");

				break;
			}
			boolean got = false;
			try {
				this.doCollectFor(oi, type, outputFile);
				got = true;
			} catch (SocketException e) {
				LOG.warn("error when collect:" + outputFile.getAbsolutePath(), e);
			}
			if (got) {
				retry = 0;
			} else {
				retry--;
			}
		}

	}

	private void doCollectFor(CorpInfoEntity oi, String type, File outputFile) throws Exception {

		HttpGet httpget = new HttpGet("/service/" + type + "_" + oi.getCode() + ".html?type=year");
		httpget.setConfig(config);

		LOG.info("Executing request " + httpget.getRequestLine() + " to " + target + " via " + proxy);

		CloseableHttpResponse response = httpclient.execute(target, httpget);
		this.lastAccessTimestamp = System.currentTimeMillis();
		try {

			LOG.info("----------------------------------------");
			if (LOG.isTraceEnabled()) {
				StringBuffer sb = new StringBuffer();

				sb.append("statusLine:").append(response.getStatusLine());

				for (Header header : response.getAllHeaders()) {
					sb.append("," + header.getName() + ":" + header.getValue());
				}
				LOG.trace(sb.toString());//

			}
			if (response.getStatusLine().getStatusCode() == 200) {
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
					LOG.error(
							"cannot rename from:" + workFile.getAbsolutePath() + ",to:" + outputFile.getAbsolutePath());
				}
			}

		} finally {
			response.close();
		}
	}
}
