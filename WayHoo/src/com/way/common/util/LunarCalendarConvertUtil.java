package com.way.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class LunarCalendarConvertUtil {
	private final static short[] lunarCalendarBaseInfo = new short[] { 0x4bd,
			0x4ae, 0xa57, 0x54d, 0xd26, 0xd95, 0x655, 0x56a, 0x9ad, 0x55d,
			0x4ae, 0xa5b, 0xa4d, 0xd25, 0xd25, 0xb54, 0xd6a, 0xada, 0x95b,
			0x497, 0x497, 0xa4b, 0xb4b, 0x6a5, 0x6d4, 0xab5, 0x2b6, 0x957,
			0x52f, 0x497, 0x656, 0xd4a, 0xea5, 0x6e9, 0x5ad, 0x2b6, 0x86e,
			0x92e, 0xc8d, 0xc95, 0xd4a, 0xd8a, 0xb55, 0x56a, 0xa5b, 0x25d,
			0x92d, 0xd2b, 0xa95, 0xb55, 0x6ca, 0xb55, 0x535, 0x4da, 0xa5d,
			0x457, 0x52d, 0xa9a, 0xe95, 0x6aa, 0xaea, 0xab5, 0x4b6, 0xaae,
			0xa57, 0x526, 0xf26, 0xd95, 0x5b5, 0x56a, 0x96d, 0x4dd, 0x4ad,
			0xa4d, 0xd4d, 0xd25, 0xd55, 0xb54, 0xb5a, 0x95a, 0x95b, 0x49b,
			0xa97, 0xa4b, 0xb27, 0x6a5, 0x6d4, 0xaf4, 0xab6, 0x957, 0x4af,
			0x497, 0x64b, 0x74a, 0xea5, 0x6b5, 0x55c, 0xab6, 0x96d, 0x92e,
			0xc96, 0xd95, 0xd4a, 0xda5, 0x755, 0x56a, 0xabb, 0x25d, 0x92d,
			0xcab, 0xa95, 0xb4a, 0xbaa, 0xad5, 0x55d, 0x4ba, 0xa5b, 0x517,
			0x52b, 0xa93, 0x795, 0x6aa, 0xad5, 0x5b5, 0x4b6, 0xa6e, 0xa4e,
			0xd26, 0xea6, 0xd53, 0x5aa, 0x76a, 0x96d, 0x4bd, 0x4ad, 0xa4d,
			0xd0b, 0xd25, 0xd52, 0xdd4, 0xb5a, 0x56d, 0x55b, 0x49b, 0xa57,
			0xa4b, 0xaa5, 0xb25, 0x6d2, 0xada };
	private final static byte[] lunarCalendarSpecialInfo = new byte[] { 0x08,
			0x00, 0x00, 0x05, 0x00, 0x00, 0x14, 0x00, 0x00, 0x02, 0x00, 0x06,
			0x00, 0x00, 0x15, 0x00, 0x00, 0x02, 0x00, 0x17, 0x00, 0x00, 0x05,
			0x00, 0x00, 0x14, 0x00, 0x00, 0x02, 0x00, 0x06, 0x00, 0x00, 0x05,
			0x00, 0x00, 0x13, 0x00, 0x17, 0x00, 0x00, 0x16, 0x00, 0x00, 0x14,
			0x00, 0x00, 0x02, 0x00, 0x07, 0x00, 0x00, 0x15, 0x00, 0x00, 0x13,
			0x00, 0x08, 0x00, 0x00, 0x06, 0x00, 0x00, 0x04, 0x00, 0x00, 0x03,
			0x00, 0x07, 0x00, 0x00, 0x05, 0x00, 0x00, 0x04, 0x00, 0x08, 0x00,
			0x00, 0x16, 0x00, 0x00, 0x04, 0x00, 0x0a, 0x00, 0x00, 0x06, 0x00,
			0x00, 0x05, 0x00, 0x00, 0x03, 0x00, 0x08, 0x00, 0x00, 0x05, 0x00,
			0x00, 0x04, 0x00, 0x00, 0x02, 0x00, 0x07, 0x00, 0x00, 0x05, 0x00,
			0x00, 0x04, 0x00, 0x09, 0x00, 0x00, 0x16, 0x00, 0x00, 0x04, 0x00,
			0x00, 0x02, 0x00, 0x06, 0x00, 0x00, 0x05, 0x00, 0x00, 0x03, 0x00,
			0x07, 0x00, 0x00, 0x16, 0x00, 0x00, 0x05, 0x00, 0x00, 0x02, 0x00,
			0x07, 0x00, 0x00, 0x15, 0x00, 0x00 };

	/* SPRD: bug254474 correct the algorithm of getting solar terms @{ */
	private final static long[] mSolarTermInfo = new long[] { 0, 21208, 42467,
			63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072,
			240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447,
			419210, 440795, 462224, 483532, 504758 };

	private final static Calendar mOffDateCalendar;
	private final static long mMilliSecondsForSolarTerm;
	/* @} */

	private final static int baseYear = 1900;

	private final static int outBoundYear = 2050;

	private static long baseDayTime = 0;

	private final static int bigMonthDays = 30;

	private final static int smallMonthDays = 29;

	static {
		/* SPRD: bug251911 lunar algorithm error @{ */
		// use Date.getTime to return milliseconds for we don't need timezone
		// info
		// Date(0, 0, 31) represent 1900-1-31, it is the first day of Gengzi
		// year
		// in lunar
		baseDayTime = new Date(0, 0, 31).getTime();
		/* SPRD: bug254474 correct the algorithm of getting solar terms @{ */
		mOffDateCalendar = Calendar.getInstance();
		mOffDateCalendar.set(1900, 0, 6, 2, 5, 0);
		mMilliSecondsForSolarTerm = mOffDateCalendar.getTime().getTime();
		/* @} */
	}

	/* SPRD: bug254474 correct the algorithm of getting solar terms @{ */
	public static int getSolarTermDayOfMonth(int year, int n) {
		mOffDateCalendar
				.setTime(new Date(
						(long) ((31556925974.7 * (year - 1900) + mSolarTermInfo[n] * 60000L) + mMilliSecondsForSolarTerm)));
		return mOffDateCalendar.get(Calendar.DAY_OF_MONTH);
	}

	/* @} */

	public static int getLunarMonthDays(int lunarYear, int lunarMonth) {
		if (isLunarBigMonth(lunarYear, lunarMonth))
			return bigMonthDays;
		else
			return smallMonthDays;
	}

	public static boolean isLunarBigMonth(int lunarYear, int lunarMonth) {
		short lunarYearBaseInfo = lunarCalendarBaseInfo[lunarYear - baseYear];
		if ((lunarYearBaseInfo & (0x01000 >>> lunarMonth)) != 0)
			return true;
		else
			return false;
	}

	final public static int getYearDays(int lunarYear) {
		int retSum = 0;
		for (int iLunarMonth = 1; iLunarMonth <= 12; iLunarMonth++) {
			retSum += getLunarMonthDays(lunarYear, iLunarMonth);
		}
		return (retSum + getLeapMonthDays(lunarYear));
	}

	final public static int getLeapMonth(int lunarYear) {
		return lunarCalendarSpecialInfo[lunarYear - baseYear] & 0xf;
	}

	final public static int getLeapMonthDays(int lunarYear) {
		if (getLeapMonth(lunarYear) == 0)
			return 0;
		else if ((lunarCalendarSpecialInfo[lunarYear - baseYear] & 0x10) != 0)
			return bigMonthDays;
		else
			return smallMonthDays;
	}

	public static void parseLunarCalendar(int year, int month, int day,
			LunarCalendar lunarCalendar) {
		if (lunarCalendar == null)
			return;

		int leapLunarMonth = 0;

		Date presentDate = null;

		boolean isLeapMonth = false;

		/* SPRD: bug251911 lunar algorithm error @{ */
		presentDate = new Date(year - 1900, month, day);

		// we use Math.ceil() here because offsetDayNum some time be truncate
		// this will cause we lost one day
		int offsetDayNum = (int) Math
				.ceil((presentDate.getTime() - baseDayTime) * 1.0 / 86400000L);
		/* @} */

		int lunarYear = 0;
		int lunarMonth = 0;
		int lunarDay = 0;

		for (lunarYear = baseYear; lunarYear < outBoundYear; lunarYear++) {
			int daysOfLunarYear = getYearDays(lunarYear);
			if (offsetDayNum < daysOfLunarYear)
				break;
			offsetDayNum -= daysOfLunarYear;
		}
		if (offsetDayNum < 0 || lunarYear == outBoundYear)
			return;

		leapLunarMonth = getLeapMonth(lunarYear);

		for (lunarMonth = 1; lunarMonth <= 12; lunarMonth++) {
			int daysOfLunarMonth = 0;
			if (isLeapMonth)
				daysOfLunarMonth = getLeapMonthDays(lunarYear);
			else
				daysOfLunarMonth = getLunarMonthDays(lunarYear, lunarMonth);

			if (offsetDayNum < daysOfLunarMonth)
				break;
			else {
				offsetDayNum -= daysOfLunarMonth;
				if (lunarMonth == leapLunarMonth) {
					if (!isLeapMonth) {
						lunarMonth--;
						isLeapMonth = true;
					} else {
						isLeapMonth = false;
					}
				}
			}
		}

		lunarDay = offsetDayNum + 1;

		lunarCalendar.lunarYear = lunarYear;
		lunarCalendar.lunarMonth = lunarMonth;
		lunarCalendar.lunarDay = lunarDay;
		lunarCalendar.isLeapMonth = isLeapMonth;

		lunarCalendar.solarYear = year;
		lunarCalendar.solarMonth = month;
		lunarCalendar.solarDay = day;
	}

	public static boolean isLunarSetting() {
		String language = getLanguageEnv();

		if (language != null
				&& (language.trim().equals("zh-CN") || language.trim().equals(
						"zh-TW")))
			return true;
		else
			return false;
	}

	private static String getLanguageEnv() {
		Locale l = Locale.getDefault();
		String language = l.getLanguage();
		String country = l.getCountry().toLowerCase();
		if ("zh".equals(language)) {
			if ("cn".equals(country)) {
				language = "zh-CN";
			} else if ("tw".equals(country)) {
				language = "zh-TW";
			}
		} else if ("pt".equals(language)) {
			if ("br".equals(country)) {
				language = "pt-BR";
			} else if ("pt".equals(country)) {
				language = "pt-PT";
			}
		}
		return language;
	}

}
