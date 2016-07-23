package org.cellang.core.util;

import org.cellang.collector.EnvUtil;

public class ReportDate {
	public int year;

	public ReportDate(int i) {
		this.year = i;
	}

	public String format() {

		return "Y" + year;
	}

	public ReportDate add(int idx) {
		return new ReportDate(year + idx);
	}

	public static ReportDate valueOf(int i) {

		return new ReportDate(i);
	}
	
	public long getTimeInMillis() {		
		return EnvUtil.newDateOfYearLastDay(this.year).getTime();
	}

	@Override
	public int hashCode() {
		return this.year;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !ReportDate.class.isInstance(obj)) {
			return false;
		}
		ReportDate rd = (ReportDate) obj;
		return this.year == rd.year;
	}

	@Override
	public String toString() {		
		return this.format();
	}
	

}
