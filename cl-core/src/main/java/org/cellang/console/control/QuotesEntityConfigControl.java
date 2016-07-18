package org.cellang.console.control;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.collector.EnvUtil;
import org.cellang.collector.SinaAllQuotesPreprocessor;
import org.cellang.collector.SinaQuotesCollector;
import org.cellang.commons.jdbc.DeleteOperation;
import org.cellang.commons.util.BeanUtil;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.QuotesEntity;
import org.cellang.core.loader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuotesEntityConfigControl extends EntityConfigControl<QuotesEntity> implements HasActions {
	private static class Sorter implements Comparator<Method> {
		Map<String, String> sorted = new HashMap<>();

		public Sorter() {
			sorted.put("code", "000001");
			sorted.put("name", "000002");
			sorted.put("settlement", "000003");

		}

		@Override
		public int compare(Method o1, Method o2) {
			String name1 = BeanUtil.getPropertyNameFromGetMethod(o1);
			String name2 = BeanUtil.getPropertyNameFromGetMethod(o2);
			String i1 = sorted.get(name1);
			String i2 = sorted.get(name2);
			name1 = i1 == null ? name1 : i1;
			name2 = i2 == null ? name2 : i2;

			return name1.compareTo(name2);
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(QuotesEntityConfigControl.class);
	private List<Action> actions = new ArrayList<>();
	EntitySessionFactory entitySessions;

	public QuotesEntityConfigControl(EntitySessionFactory entitySessions) {
		super.comparator = new Sorter();
		this.entitySessions = entitySessions;
		actions.add(new Action() {

			@Override
			public String getName() {
				return "Update & Reload";
			}

			@Override
			public void perform() {
				QuotesEntityConfigControl.this.updateAndReload();
			}
		});
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (HasActions.class.equals(cls)) {
			return (T) this;
		}
		return null;
	}

	@Override
	public List<Action> getActions(List<Action> al) {
		al.addAll(this.actions);
		return al;
	}

	protected void updateAndReload() {
		File data = EnvUtil.getDataDir();
		File sinaData = new File(data, "sina");
		File outputParentDir = new File(sinaData, "all-quotes");

		File output = new SinaQuotesCollector().pauseInterval(2000).outputParentDir(outputParentDir).start();

		File to = new File(data, "sinapp" + File.separator + "all-quotes");

		new SinaAllQuotesPreprocessor(outputParentDir, to).process();
		File[] files = to.listFiles();
		File lastFile = null;
		for (File f : files) {
			if (f.isDirectory()) {
				continue;
			}
			if (lastFile == null || lastFile.lastModified() < f.lastModified()) {
				lastFile = f;
			}
		}
		LOG.info("load the last file:" + lastFile.getAbsolutePath());//

		this.entitySessions.execute(new DeleteOperation().table(QuotesEntity.tableName));
		new DataLoader(this.entitySessions).load(lastFile);

	}
}
