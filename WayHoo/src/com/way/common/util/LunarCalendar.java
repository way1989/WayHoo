package com.way.common.util;

import android.content.Context;

import com.way.yahoo.R;


public class LunarCalendar {
    private static String[] lunarCalendarNumber = null;

    private static String[] lunarCalendarTen = null;

    private static String[] year_of_birth = null;

    private static String[] lunarTerm = null;

    private static String lunarLeapTag = null, lunarMonthTag = null,
            zhengyueTag = null;

    Context mContext;

    public int lunarYear = 0;

    public int lunarMonth = 0;

    public int lunarDay = 0;

    public int solarYear = 0;

    public int solarMonth = 0;

    public int solarDay = 0;

    public boolean isLeapMonth = false;

    public boolean isFastival = false;

    /* SPRD: bug257032 2013-12-26 special solar term dates @{ */
    private static String[] mSpecialSolarTermDates;
    /* SPRD: bug257032 2013-12-26 @} */

    /* SPRD: bug258885 2013-12-25 update lunar language resources info @{ */
    private static boolean mHasInitialedRes;
    // such as Mid-Autumn Day
    private static String[] mTraditionalFestivalStr;
    // such as Valentine's Day
    private static String[] mFestivalStr;
    // such as Jia, Yi, Bing, Ding
    private static String[] mYearStemStr;
    // such as Zi, Chou, Yin, Mao
    private static String[] mYearBranchStr;

    static {
        mHasInitialedRes = false;
    }

    public LunarCalendar(Context context) {
        mContext = context;
        if (!mHasInitialedRes) {
            reloadLanguageResources(context);
            mHasInitialedRes = true;
        }
    }

    public static void reloadLanguageResources(Context ctx) {
        if (lunarCalendarNumber == null) {
            lunarCalendarNumber = new String[12];
        }
        lunarCalendarNumber[0] = getString(ctx, R.string.chineseNumber1);
        lunarCalendarNumber[1] = getString(ctx, R.string.chineseNumber2);
        lunarCalendarNumber[2] = getString(ctx, R.string.chineseNumber3);
        lunarCalendarNumber[3] = getString(ctx, R.string.chineseNumber4);
        lunarCalendarNumber[4] = getString(ctx, R.string.chineseNumber5);
        lunarCalendarNumber[5] = getString(ctx, R.string.chineseNumber6);
        lunarCalendarNumber[6] = getString(ctx, R.string.chineseNumber7);
        lunarCalendarNumber[7] = getString(ctx, R.string.chineseNumber8);
        lunarCalendarNumber[8] = getString(ctx, R.string.chineseNumber9);
        lunarCalendarNumber[9] = getString(ctx, R.string.chineseNumber10);
        lunarCalendarNumber[10] = getString(ctx, R.string.chineseNumber11);
        lunarCalendarNumber[11] = getString(ctx, R.string.chineseNumber12);

        if (lunarCalendarTen == null) {
            lunarCalendarTen = new String[5];
        }
        lunarCalendarTen[0] = getString(ctx, R.string.chineseTen0);
        lunarCalendarTen[1] = getString(ctx, R.string.chineseTen1);
        lunarCalendarTen[2] = getString(ctx, R.string.chineseTen2);
        lunarCalendarTen[3] = getString(ctx, R.string.chineseTen3);
        lunarCalendarTen[4] = getString(ctx, R.string.chineseTen4);

        if (year_of_birth == null) {
            year_of_birth = new String[12];
        }
        year_of_birth[0] = getString(ctx, R.string.animals0);
        year_of_birth[1] = getString(ctx, R.string.animals1);
        year_of_birth[2] = getString(ctx, R.string.animals2);
        year_of_birth[3] = getString(ctx, R.string.animals3);
        year_of_birth[4] = getString(ctx, R.string.animals4);
        year_of_birth[5] = getString(ctx, R.string.animals5);
        year_of_birth[6] = getString(ctx, R.string.animals6);
        year_of_birth[7] = getString(ctx, R.string.animals7);
        year_of_birth[8] = getString(ctx, R.string.animals8);
        year_of_birth[9] = getString(ctx, R.string.animals9);
        year_of_birth[10] = getString(ctx, R.string.animals10);
        year_of_birth[11] = getString(ctx, R.string.animals11);

        lunarLeapTag = getString(ctx, R.string.leap_month);
        lunarMonthTag = getString(ctx, R.string.month);
        zhengyueTag = getString(ctx, R.string.zheng);

        if (lunarTerm == null) {
            lunarTerm = new String[24];
        }
        lunarTerm[0] = getString(ctx, R.string.terms0);
        lunarTerm[1] = getString(ctx, R.string.terms1);
        lunarTerm[2] = getString(ctx, R.string.terms2);
        lunarTerm[3] = getString(ctx, R.string.terms3);
        lunarTerm[4] = getString(ctx, R.string.terms4);
        lunarTerm[5] = getString(ctx, R.string.terms5);
        lunarTerm[6] = getString(ctx, R.string.terms6);
        lunarTerm[7] = getString(ctx, R.string.terms7);
        lunarTerm[8] = getString(ctx, R.string.terms8);
        lunarTerm[9] = getString(ctx, R.string.terms9);
        lunarTerm[10] = getString(ctx, R.string.terms10);
        lunarTerm[11] = getString(ctx, R.string.terms11);
        lunarTerm[12] = getString(ctx, R.string.terms12);
        lunarTerm[13] = getString(ctx, R.string.terms13);
        lunarTerm[14] = getString(ctx, R.string.terms14);
        lunarTerm[15] = getString(ctx, R.string.terms15);
        lunarTerm[16] = getString(ctx, R.string.terms16);
        lunarTerm[17] = getString(ctx, R.string.terms17);
        lunarTerm[18] = getString(ctx, R.string.terms18);
        lunarTerm[19] = getString(ctx, R.string.terms19);
        lunarTerm[20] = getString(ctx, R.string.terms20);
        lunarTerm[21] = getString(ctx, R.string.terms21);
        lunarTerm[22] = getString(ctx, R.string.terms22);
        lunarTerm[23] = getString(ctx, R.string.terms23);

        if (mTraditionalFestivalStr == null) {
            mTraditionalFestivalStr = new String[9];
        }
        mTraditionalFestivalStr[0] = getString(ctx, R.string.chunjie);
        mTraditionalFestivalStr[1] = getString(ctx, R.string.yuanxiao);
        mTraditionalFestivalStr[2] = getString(ctx, R.string.duanwu);
        mTraditionalFestivalStr[3] = getString(ctx, R.string.qixi);
        mTraditionalFestivalStr[4] = getString(ctx, R.string.zhongqiu);
        mTraditionalFestivalStr[5] = getString(ctx, R.string.chongyang);
        mTraditionalFestivalStr[6] = getString(ctx, R.string.laba);
        mTraditionalFestivalStr[7] = getString(ctx, R.string.xiaonian);
        mTraditionalFestivalStr[8] = getString(ctx, R.string.chuxi);

        if (mFestivalStr == null) {
            mFestivalStr = new String[12];
        }
        mFestivalStr[0] = getString(ctx, R.string.new_Year_day);
        mFestivalStr[1] = getString(ctx, R.string.valentin_day);
        mFestivalStr[2] = getString(ctx, R.string.women_day);
        mFestivalStr[3] = getString(ctx, R.string.arbor_day);
        mFestivalStr[4] = getString(ctx, R.string.labol_day);
        mFestivalStr[5] = getString(ctx, R.string.youth_day);
        mFestivalStr[6] = getString(ctx, R.string.children_day);
        mFestivalStr[7] = getString(ctx, R.string.Communist_day);
        mFestivalStr[8] = getString(ctx, R.string.army_day);
        mFestivalStr[9] = getString(ctx, R.string.teacher_day);
        mFestivalStr[10] = getString(ctx, R.string.national_day);
        mFestivalStr[11] = getString(ctx, R.string.christmas_day);

        if (mYearStemStr == null) {
            mYearStemStr = new String[10];
        }
        mYearStemStr[0] = getString(ctx, R.string.jia);
        mYearStemStr[1] = getString(ctx, R.string.yi);
        mYearStemStr[2] = getString(ctx, R.string.bing);
        mYearStemStr[3] = getString(ctx, R.string.ding);
        mYearStemStr[4] = getString(ctx, R.string.wutian);
        mYearStemStr[5] = getString(ctx, R.string.ji);
        mYearStemStr[6] = getString(ctx, R.string.geng);
        mYearStemStr[7] = getString(ctx, R.string.xin);
        mYearStemStr[8] = getString(ctx, R.string.ren);
        mYearStemStr[9] = getString(ctx, R.string.gui);

        if (mYearBranchStr == null) {
            mYearBranchStr = new String[12];
        }
        mYearBranchStr[0] = getString(ctx, R.string.zi);
        mYearBranchStr[1] = getString(ctx, R.string.chou);
        mYearBranchStr[2] = getString(ctx, R.string.yin);
        mYearBranchStr[3] = getString(ctx, R.string.mao);
        mYearBranchStr[4] = getString(ctx, R.string.chen);
        mYearBranchStr[5] = getString(ctx, R.string.si);
        mYearBranchStr[6] = getString(ctx, R.string.wudi);
        mYearBranchStr[7] = getString(ctx, R.string.wei);
        mYearBranchStr[8] = getString(ctx, R.string.shen);
        mYearBranchStr[9] = getString(ctx, R.string.you);
        mYearBranchStr[10] = getString(ctx, R.string.xu);
        mYearBranchStr[11] = getString(ctx, R.string.hai);

        /* SPRD: bug257032 2013-12-26 special solar term dates @{ */
        if (mSpecialSolarTermDates == null) {
            mSpecialSolarTermDates = ctx.getResources()
                .getStringArray(R.array.special_solar_term_dates);
        }
        /* SPRD: bug257032 2013-12-26 */
    }

    public static void clearLanguageResourcesRefs() {
        lunarCalendarNumber = null;
        lunarCalendarTen = null;
        year_of_birth = null;
        lunarTerm = null;
        mTraditionalFestivalStr = null;
        mFestivalStr = null;
        mYearStemStr = null;
        mYearBranchStr = null;

        mHasInitialedRes = false;

        // SPRD: bug257032 2013-12-26 special solar term dates
        mSpecialSolarTermDates = null;
    }

    private static String getString(Context ctx, int resId) {
        return ctx.getString(resId);
    }

    public String getTraditionalFestival() {
        return getTraditionalFestival(lunarYear, lunarMonth, lunarDay);
    }

    public String getTraditionalFestival(int lunarYear, int lunarMonth,
            int lunarDay) {
        /* SPRD: bug254439 delete duplicatin traditional festival string @{ */
        // if is leap month, return empty string
        if (isLeapMonth) {
            return "";
        }
        /* @} */
        String festivalStr = "";
        if (lunarMonth == 1 && lunarDay == 1)
            festivalStr = mTraditionalFestivalStr[0];
        if (lunarMonth == 1 && lunarDay == 15)
            festivalStr = mTraditionalFestivalStr[1];
        if (lunarMonth == 5 && lunarDay == 5)
            festivalStr = mTraditionalFestivalStr[2];
        if (lunarMonth == 7 && lunarDay == 7)
            festivalStr = mTraditionalFestivalStr[3];
        if (lunarMonth == 8 && lunarDay == 15)
            festivalStr = mTraditionalFestivalStr[4];
        if (lunarMonth == 9 && lunarDay == 9)
            festivalStr = mTraditionalFestivalStr[5];
        if (lunarMonth == 12 && lunarDay == 8)
            festivalStr = mTraditionalFestivalStr[6];
        if (lunarMonth == 12 && lunarDay == 23)
            festivalStr = mTraditionalFestivalStr[7];

        if (lunarMonth == 12) {
            if (lunarDay == LunarCalendarConvertUtil.getLunarMonthDays(
                    lunarYear, lunarMonth))
                festivalStr = mTraditionalFestivalStr[8];
        }
        return festivalStr;
    }

    public String getFestival() {
        return getFestival(solarMonth, solarDay);
    }

    private String getFestival(int lunarMonth, int lunarDay) {
        String festivalStr = "";
        if (lunarMonth == 0 && lunarDay == 1)
            festivalStr = mFestivalStr[0];
        if (lunarMonth == 1 && lunarDay == 14)
            festivalStr = mFestivalStr[1];
        if (lunarMonth == 2 && lunarDay == 8)
            festivalStr = mFestivalStr[2];
        if (lunarMonth == 2 && lunarDay == 12)
            festivalStr = mFestivalStr[3];
        if (lunarMonth == 4 && lunarDay == 1)
            festivalStr = mFestivalStr[4];
        if (lunarMonth == 4 && lunarDay == 4)
            festivalStr = mFestivalStr[5];
        if (lunarMonth == 5 && lunarDay == 1)
            festivalStr = mFestivalStr[6];
        /** add 20130702 spreadst of 181042 no communist day start */
        if (lunarMonth == 6 && lunarDay == 1)
            festivalStr = mFestivalStr[7];
        /** add 20130702 spreadst of 181042 no communist day end */
        if (lunarMonth == 7 && lunarDay == 1)
            festivalStr = mFestivalStr[8];
        if (lunarMonth == 8 && lunarDay == 10)
            festivalStr = mFestivalStr[9];
        if (lunarMonth == 9 && lunarDay == 1)
            festivalStr = mFestivalStr[10];
        if (lunarMonth == 11 && lunarDay == 25)
            festivalStr = mFestivalStr[11];
        return festivalStr;
    }

    /* SPRD: bug254474 correct the algorithm of getting solar terms @{ */
    /* SPRD: bug257032 2013-12-26 special solar term dates @{ */
    private String getSolarTerm(int year, int month, int date) {
        String termStr = "";
        SpecialSolarTermInfo info = getSpecialSolarTermInfo(year, month, date);
        if (info != null && info.mIndex != -1) {
            if (info.mIndex != 0) {
                termStr = info.mTermStr;
            } // else info.mIndex == 0, then this should return empty string
        } else {
            if (date == LunarCalendarConvertUtil.getSolarTermDayOfMonth(year,
                    month * 2)) {
                termStr = lunarTerm[month * 2];
            } else if (date == LunarCalendarConvertUtil.getSolarTermDayOfMonth(
                    year, month * 2 + 1)) {
                termStr = lunarTerm[month * 2 + 1];
            }
        }
        return termStr;
    }
    /* SPRD: bug257032 2013-12-26 @} */
    /* @} */

    private String getChinaMonthString() {
        return getChinaMonthString(lunarMonth, isLeapMonth);
    }

    private String getChinaMonthString(int lunarMonth, boolean isLeapMonth) {
        String chinaMonth = (isLeapMonth ? lunarLeapTag : "")
                + ((lunarMonth == 1) ? zhengyueTag
                        : lunarCalendarNumber[lunarMonth - 1]) + lunarMonthTag;
        return chinaMonth;
    }

    private String getChinaDayString(boolean notDisplayLunarMonthForFirstDay) {
        return getChinaDayString(lunarMonth, lunarDay, isLeapMonth,
                notDisplayLunarMonthForFirstDay);
    }

    public String getChinaDayString(int lunarMonth, int lunarDay,
            boolean isLeapMonth, boolean notDisplayLunarMonthForFirstDay) {
        if (lunarDay > 30)
            return "";
        if (lunarDay == 1 && notDisplayLunarMonthForFirstDay)
            return getChinaMonthString(lunarMonth, isLeapMonth);
        if (lunarDay == 10)
            return lunarCalendarTen[0] + lunarCalendarTen[1];
        if (lunarDay == 20)
            return lunarCalendarTen[4] + lunarCalendarTen[1];

        return lunarCalendarTen[lunarDay / 10]
                + lunarCalendarNumber[(lunarDay + 9) % 10];
    }

    private String getChinaYearString() {
        return getChinaYearString(lunarYear);
    }

    private String getChinaYearString(int lunarYear) {
        return String.valueOf(lunarYear);
    }

    private String getLunarYearString(int num) {
        return (mYearStemStr[num % 10] + mYearBranchStr[num % 12]);
    }
    /* SPRD: bug258885 2013-12-25 @} */

    public String getLunarYear(int year) {
        int num = year - 1900 + 36;
        return getLunarYearString(num);
    }

    public String animalsYear(int year) {
        return year_of_birth[(year - 4) % 12];
    }

    public String[] getLunarCalendarInfo(boolean notDisplayLunarMonthForFirstDay) {
        if (lunarYear == 0 || lunarMonth == 0 || lunarDay == 0)
            return null;// new String[]{null,null,null,null,null};
        String lunarYearStr = getChinaYearString();
        String lunarMonthStr = getChinaMonthString();
        String lunarDayStr = getChinaDayString(notDisplayLunarMonthForFirstDay);

        String traditionFestivalStr = getTraditionalFestival();
        String festivalStr = getFestival();
        // SPRD: bug254474 correct the algorithm of getting solar terms
        String solarTermStr = getSolarTerm(solarYear, solarMonth, solarDay);

        return new String[] { lunarYearStr, lunarMonthStr, lunarDayStr,
                traditionFestivalStr, festivalStr, solarTermStr };
    }

    public String getLunarDayInfo() {
        if (lunarYear == 0 || lunarMonth == 0 || lunarDay == 0) {
            return "";
        }
        // if this day is traditional festival, show as it
        String traditionFestivalStr = getTraditionalFestival();
        String festivalStr = getFestival();
        // SPRD: bug254474 correct the algorithm of getting solar terms
        String solarTermStr = getSolarTerm(solarYear, solarMonth, solarDay);
        /*add 20130703 Spreadst of 176738 the color error start*/
        if (!traditionFestivalStr.trim().equals("")
                || !festivalStr.trim().equals("")
                || !solarTermStr.trim().equals("")) {
            isFastival = true;
        } else {
            isFastival = false;
        }
        /* add 20130703 Spreadst of 176738 the color error end */
        if (traditionFestivalStr != null && festivalStr != null
                && !traditionFestivalStr.trim().equals("")
                && !festivalStr.trim().equals("")) {
            return traditionFestivalStr + "/" + festivalStr;
        }

        if (traditionFestivalStr != null && solarTermStr != null
                && !traditionFestivalStr.trim().equals("")
                && !solarTermStr.trim().equals("")) {
            return traditionFestivalStr + "/" + solarTermStr;
        }

        if (festivalStr != null && solarTermStr != null
                && !festivalStr.trim().equals("")
                && !solarTermStr.trim().equals("")) {
            return festivalStr + "/" + solarTermStr;
        }

        if (traditionFestivalStr != null
                && !traditionFestivalStr.trim().equals("")) {
            return traditionFestivalStr;
        }

        // if this day is festival, show as it
        if (festivalStr != null && !festivalStr.trim().equals("")) {
            return festivalStr;
        }

        // if this day is solar term, show as it
        if (solarTermStr != null && !solarTermStr.trim().equals("")) {
            return solarTermStr;
        }

        // if this day is first day of lunar month, show lunar month number
        String lunarMonthStr = getChinaMonthString();
        if (lunarDay == 1) {
            return lunarMonthStr;
        }

        // otherwise, show lunar day number
        String lunarDayStr = getChinaDayString(false);
        return lunarDayStr;

    }

    /* SPRD: bug257032 2013-12-26 special solar term dates @{ */
    final static class SpecialSolarTermInfo {
        String mSpecialStr;
        String mTermStr;
        int mIndex;

        SpecialSolarTermInfo(String specialStr, String termStr,
                int index) {
            mSpecialStr = specialStr;
            mTermStr = termStr;
            mIndex = index;
        }
    }

    private static SpecialSolarTermInfo getSpecialSolarTermInfo(int year,
            int month, int day) {
        SpecialSolarTermInfo info = null;
        if (mSpecialSolarTermDates != null) {
            // out date format will be xxxxxxxx, eg. 20131221, length equals 8
            StringBuilder dateStrBuilder = new StringBuilder(8);
            dateStrBuilder.setLength(0);
            dateStrBuilder.append(year);
            // month is from 0-11
            if (month < 9) {
                dateStrBuilder.append(0);
            }
            dateStrBuilder.append(month + 1);
            // day is from 1-31
            if (day < 10) {
                dateStrBuilder.append(0);
            }
            dateStrBuilder.append(day);
            //Log.d("chen", "current date str: " + dateStrBuilder.toString());
            int index;
            String term = "";
            for (String dateStr : mSpecialSolarTermDates) {
                index = dateStr.indexOf(dateStrBuilder.toString());
                if (index != -1) {
                    term = lunarTerm[Integer.valueOf(dateStr
                        .substring(dateStr.lastIndexOf('|') + 1))];
                    info = new SpecialSolarTermInfo(dateStr, term,
                            index);
                    break;
                }
            }
        }
        return info;
    }
    /* SPRD: bug257032 2013-12-26 @} */

}
