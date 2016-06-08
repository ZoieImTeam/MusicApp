package com.xinbo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * @author www.zuidaima.com
 **/
public class PrettyTimeUtils {
	public static void main(String[] args) {
		String date = "2012-07-18 23:42:05";
		testLocal();
		String minutesAgo = getMinutesAgo(date);
		System.out.println(minutesAgo);
		testMinutesFromNow();
		testMomentsAgo();
	}

	public static void testLocal() {
		PrettyTime p = new PrettyTime(new Locale("ZH_CN"));
		System.out.println(p.format(new Date()));
	}

	public static void testMinutesFromNow() {
		PrettyTime t = new PrettyTime(new Date(0));
		System.out.println(t.format(new Date(1000 * 60 * 12)));
	}

	private static void testMomentsAgo() {
		PrettyTime t = new PrettyTime(new Date(6000));
		System.out.println(t.format(new Date(0)));
	}

	public static String getMinutesAgo(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
		Date date;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return dateStr;
		}
		Date now = new Date();
		PrettyTime t = new PrettyTime(now);
		String formatDate = t.format(date);
		return formatDate;
		// System.out.println(formatDate);
	}
}