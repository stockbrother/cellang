package org.cellang.console.view;

import java.util.Comparator;

public class DateChartView extends ChartView<Long> {

	Comparator<Long> DESC = new Comparator<Long>() {
		private long unit = 1000 * 3600 * 24;

		@Override
		public int compare(Long o1, Long o2) {
			long rt = o2.longValue() - o1.longValue();

			// if unit = 1, the int casted from long will overflow and the
			// result not
			// correct.
			return (int) (rt / unit);
		}
	};

	public DateChartView(String title, AbstractChartDataProvider<Long> cd) {
		super(title, cd);
		model.setXValueSorter(DESC);
	}

}
