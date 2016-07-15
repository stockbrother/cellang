package org.cellang.console.view;

import java.util.Calendar;

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

	// TODO use locale
	public long getTimeInMillis() {
		Calendar c = Calendar.getInstance();
		c.set(year, 12, 31);//
		return c.getTimeInMillis();
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
