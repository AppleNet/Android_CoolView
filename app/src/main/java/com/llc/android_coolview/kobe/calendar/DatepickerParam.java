package com.llc.android_coolview.kobe.calendar;

import java.io.Serializable;
import java.util.Calendar;

public class DatepickerParam implements Serializable {

	private static final long serialVersionUID = -6241053776117989207L;

	public Calendar selectedDay = null;
	public Calendar startDate = null;
	public int dateRange = 0;
	public String title = "Departure Date";
}
