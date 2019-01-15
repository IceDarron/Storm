package demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2008-4-12
 * <p>
 * Description: [日期公用类]
 * </p>
 */
public final class DateUtils {

	private DateUtils() {
	}

	public static final long MILLIS_PER_DAY = 1000 * 60 * 60 * 24;

	public static final String DATE_YYYYMM = "yyyyMM";

	public static final String DATE_YYYYMMDD = "yyyyMMdd";

	public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";

	public static final String DATE_YYYY_MM_DD_HH = "HH";

	public static final String DATE_HHMM = "HHmm";

	public static final String DATE_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_YYYYMMDDs_HHMMSS = "yyyyMMdd HH:mm:ss";

	public static final String DATE_YYYYMMDD_HHMMSS_MS = "yyyy-MM-dd HH:mm:ss ms";

	/**
	 * 获取当前系统的时间格式化结果
	 * 
	 * @author rongxn
	 * @since 20190104
	 * 
	 * @param dateFormat
	 *            时间格式，调用本工具类提供的常量格式
	 * @return 返回字符串类型的当前时间格式化结果
	 */
	public static String currentTime(String dateFormat) {
		// 获取当前系统的时间，指定日期格式化
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(new java.util.Date());
	}

	/**
	 * 获取指定时间的格式化结果
	 * 
	 * @author rongxn
	 * @since 20190104
	 * 
	 * @param date
	 *            指定时间
	 * @param dateFormat
	 *            时间格式，调用本工具类提供的常量格式
	 * @return 返回字符串类型的时间格式化结果
	 */
	public static String formatDateToString(long date, String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(date); // 指定日期格式化，转为字符串
	}

	/**
	 * 获取指定时间的格式化结果
	 * 
	 * @author rongxn
	 * @since 20190104
	 * 
	 * @param date
	 *            指定时间
	 * @param dateFormat
	 *            时间格式，调用本工具类提供的常量格式
	 * @return 返回字符串类型的时间格式化结果
	 */
	public static String formatDateToString(Date date, String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(date);
	}

	/**
	 * 按指定格式解析日期
	 * 
	 * @author rongxn
	 * @since 20190104
	 * 
	 * @param date
	 *            指定时间
	 * @param format
	 *            时间格式，调用本工具类提供的常量格式
	 * @return 返回日期对象
	 * @throws ParseException 
	 */
	public static Date parseDate(String date, String dateFormat) throws ParseException {
		return new SimpleDateFormat(dateFormat).parse(date);
	}

	/**
	 * 获取当前时间的下一个日期的零点
	 * 
	 * @author rongxn
	 * @since 20190104
	 * 
	 * @return 返回日期对象
	 * @throws ParseException 
	 */
	public static Date getNextTime() throws ParseException {
		return getAddDays(parseDate(currentTime(DATE_YYYYMMDD), DATE_YYYYMMDD), 1);
	}

	/**
	 * 获取指定时间的下一个日期的零点
	 * 
	 * @author rongxn
	 * @since 20190104
	 * 
	 * @param date
	 *            指定时间
	 * @return 返回日期对象
	 * @throws ParseException 
	 */
	public static Date getNextTime(String date) throws ParseException {
		return getAddDays(parseDate(date, DATE_YYYYMMDD), 1);
	}

	public static Date getSpecificHours(Calendar calendar, int HOUR_OF_DAY) {
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int n = HOUR_OF_DAY - hour;
		calendar.add(Calendar.HOUR_OF_DAY, n);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * <p>
	 * Discription:[取得指定月份若干月后的月份]
	 * </p>
	 * 
	 * @param strDate
	 *            格式 yyyyMM
	 * @param monthCount
	 * @return
	 */
	public static String getAddMonth(String strDate, int monthCount) {
		java.util.Calendar c = Calendar.getInstance();
		int year = Integer.parseInt(strDate.substring(0, 4));
		int month = Integer.parseInt(strDate.substring(4, 6));
		c.set(year, month, 0);
		c.add(Calendar.MONTH, monthCount);
		return new SimpleDateFormat("yyyyMM").format(c.getTime());
	}

	/**
	 * <p>
	 * Discription:[取得某年的若干年后时间]
	 * </p>
	 * 
	 * @param date
	 * @param years
	 * @return
	 */
	public static Date getAddYears(Date date, int years) {
		return add(date, Calendar.YEAR, years);
	}

	/**
	 * <p>
	 * Discription:[取得某月的若干月后时间]
	 * </p>
	 * 
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getAddMonths(Date date, int months) {
		return add(date, Calendar.MONTH, months);
	}

	/**
	 * <p>
	 * Discription:[取得某天的若干天后时间]
	 * </p>
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getAddDays(Date date, int days) {
		return add(date, Calendar.DAY_OF_MONTH, days);
	}

	/**
	 * <p>
	 * Discription:[取得某小时的若干小时后时间]
	 * </p>
	 * 
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date getAddHours(Date date, int hours) {
		return add(date, Calendar.HOUR_OF_DAY, hours);
	}

	/**
	 * <p>
	 * Discription:[取得某分钟的若干分钟后时间]
	 * </p>
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date getAddMinutes(Date date, int minutes) {
		return add(date, Calendar.MINUTE, minutes);
	}

	/**
	 * <p>
	 * Discription:[取得某秒的若干秒后时间]
	 * </p>
	 * 
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date getAddSeconds(Date date, int seconds) {
		return add(date, Calendar.SECOND, seconds);
	}

	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/**
	 * Returns the number of days between the two specified dates (inclusive)
	 * 
	 * @param date1
	 *            a date
	 * @param date2
	 *            a date
	 * @return int indicating the number of days between the two specified dates
	 */
	public static int getDaysBetween(Date beginDate, Date endDate) {
		return new Long((endDate.getTime() - beginDate.getTime()) / MILLIS_PER_DAY).intValue();
	}

	/**
	 * <p>
	 * Discription:[随机生成日期，格式YYYYMMDD]
	 * </p>
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(DateUtils.DATE_YYYYMMDD);
			Date start = format.parse(beginDate);// 开始日期
			Date end = format.parse(endDate);// 结束日期
			if (start.getTime() >= end.getTime()) {
				throw new IllegalArgumentException("随机日期生成参数有误，请确认后再次请求。");
			}
			return random(start.getTime(), end.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * <p>
	 * Discription:[Random number]
	 * </p>
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private static long random(long begin, long end) {
		long random = begin + (long) (Math.random() * (end - begin));
		if (random == begin || random == end) {
			return random(begin, end);
		}
		return random;
	}

	/**
	 * <p>
	 * Discription:[将传入的时间串（yyyy-MM-dd HH:mm:ss）转为SQL Date]
	 * </p>
	 * 
	 * @param stTime
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date toSQLDate(String stTime) throws ParseException {
		return new java.sql.Date(DateUtils.parseDate(stTime, DateUtils.DATE_YYYYMMDD_HHMMSS).getTime());
	}

	/**
	 * <p>
	 * Discription:[将传入的时间串（yyyy-MM-dd HH:mm:ss）转为SQL TimeStamp]
	 * </p>
	 * 
	 * @param stTime
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Timestamp toSQLTime(String stTime) throws ParseException {
		return new java.sql.Timestamp(DateUtils.parseDate(stTime, DateUtils.DATE_YYYYMMDD_HHMMSS).getTime());
	}

	/**
	 * <p>
	 * Discription:[获取传入时间串的年月]
	 * </p>
	 * 
	 * @param strTime
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateYM(String strTime) {
		return strTime.replace("-", "").substring(0, 6);
	}

	/**
	 * <p>
	 * Discription:[将传入的时间串（yyyy-MM-dd HH:mm:ss）转为yyyyMMddHHmmss]
	 * </p>
	 * 
	 * @param stTime
	 *            yyyy-MM-dd HH:mm:ss
	 */
	public static String toTimeString(String stTime) {
		return stTime.replace("-", "").replace(":", "").replace(" ", "");
	}

	/**
	 * <p>
	 * Discription:[获取传入时间串的日期yyyyMMdd]
	 * </p>
	 * 
	 * @param strTime
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateDay(String strTime) {
		return strTime.replace("-", "").substring(0, 8);
	}

	/**
	 * 获取短信业务时间，如果当前时间在发送时段内，则取当前时间，否则取业务算法时间
	 * 
	 * @param hour
	 * @return
	 */
	public static String getTimeByHour(String hour) {
		SimpleDateFormat dateFormatHH = new SimpleDateFormat("HH");
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_YYYY_MM_DD);
		Date nowDate = new java.util.Date();
		String nowHour = dateFormatHH.format(nowDate);
		String cud = dateFormat.format(nowDate);
		try {
			if (nowHour.equals(hour)) {
				return currentTime(DATE_YYYYMMDD_HHMMSS);
			} else {
				cud = cud + " " + hour + ":00:00";
			}
		} catch (Exception e) {
			cud = cud + " " + hour + ":00:00";
		}
		return cud;
	}

	/**
	 * 获取传入时间和当前时间差了多少天（YYYYMMDD）
	 * 
	 * @param strDate
	 * @return
	 */
	public static int getDifferBystr(String strDate) {
		if (BasicUtils.isNULL(strDate)) {
			return -1;
		} else {
			return Integer.valueOf(currentTime(DATE_YYYYMMDD)) - Integer.valueOf(strDate);
		}
	}

	/**
	 * <p>
	 * Discription:[按指定格式解析日期]
	 * </p>
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String formatDateForSms(String date) {
		if (BasicUtils.isNULL(date)) {
			return "";
		}

		String result = "";

		try {
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			String day = date.substring(8, 10);
			String hour = date.substring(11, 13);
			String minutes = date.substring(14, 16);
			result = year + "年" + month + "月" + day + "日" + hour + "时" + minutes + "分";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * <p>
	 * Discription:[按指定格式解析日期]
	 * </p>
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String formatDateForSms2(String date) {
		if (BasicUtils.isNULL(date)) {
			return "";
		}

		String result = "";

		try {
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			String day = date.substring(8, 10);
			String hour = date.substring(11, 13);
			result = year + "年" + month + "月" + day + "日" + hour + "时";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * <p>
	 * Discription:[按指定格式解析日期]
	 * </p>
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String formatDateForSms3(String date) {
		if (BasicUtils.isNULL(date)) {
			return "";
		}

		String result = "";

		try {
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			String day = date.substring(8, 10);
			result = year + "年" + month + "月" + day + "日";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * <p>
	 * Discription:[按指定格式解析日期]
	 * </p>
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String formatDateForSms4(String date) {
		if (BasicUtils.isNULL(date)) {
			return "";
		}

		String result = "";

		try {
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			String day = date.substring(8, 10);
			result = year + month + day;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}