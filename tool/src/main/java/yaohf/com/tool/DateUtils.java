package yaohf.com.tool;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtils {

	public static String dataTime() {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = format.format(date);
		return time;

	}

	public static int getMinute(String endTime) {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间

		String nowtime = d.format(new Date());// 按以上格式 将当前时间转换成字符串

		int result = 0;
		try {
			result = (int) ((d.parse(endTime).getTime() - d.parse(nowtime)
					.getTime())); // / (1000 * 60)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 *  剩余时间，指定时间－当前时间＝剩余时间
	 * @Description: TODO
	 * @param @param startTime
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static float getSurplusMinute(String startTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(startTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		Calendar current = Calendar.getInstance();
		float minute = ((cal.getTimeInMillis() - current.getTimeInMillis()) / 1000 / 60);
		return minute;
	}

	/**
	 * 
	 * 获取指定时间
	 * 
	 * @Description: TODO
	 * @param @param time
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getSettingTime(int time) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, time);
		String s = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal
				.getTime());
		return s;
	}

	/**
	 * 
	 * 获取指定时间
	 * 
	 * @Description: TODO
	 * @param @param time
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getSettingToDateTime(int time) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, time);
		String s = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal
				.getTime());
		return s;
	}

	public static float getDuration(String beginTime, String endTime) {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间

		// String nowtime = d.format(new Date());// 按以上格式 将当前时间转换成字符串

		float result = 0;
		try {
			result = (float) ((d.parse(endTime).getTime() - d.parse(beginTime)
					.getTime())); // / (1000 * 60)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据时间，获取时间秒
	 * 
	 * @Description: TODO
	 * @param @param time
	 * @param @return
	 * @return long
	 * @throws
	 */
	public static long getDuration(String time) {
		long seconds = 0;
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			seconds = d.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return seconds;
	}

	/**
	 * @Description: 获得本月1号的日期
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getCurrentMonthBeginDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return getDate(cal);
	}

	/**
	 * @Description: 获得日期
	 * @param @param cal
	 * @param @return
	 * @return String
	 * @throws
	 */
	private static String getDate(Calendar cal) {
		String s = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal
				.getTime());
		return s;
	}

	/**
	 * 
	 * @Description: 获得上个月1号的日期
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getLastMonthBeginDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return getDate(cal);
	}

	/**
	 * @Description: 获得上个月最后一天的日期
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getLastMonthEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 0);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return getDate(cal);
	}

	/**
	 * 
	 * @Description:获得上周一的日期
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getLastMondayDate() {
		Calendar cal = Calendar.getInstance();
		int dayPlus = getMondayPlus() + 7;
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - dayPlus);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return getDate(cal);
	}

	/**
	 * 
	 * @Description:获得上两周一的日期
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getLastMonday2Date() {
		Calendar cal = Calendar.getInstance();
		int dayPlus = getMondayPlus() + 14;
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - dayPlus);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return getDate(cal);
	}

	// 获得本周一的日期
	public static String getThisMondayDate() {
		Calendar cal = Calendar.getInstance();
		int mondayPlus = getMondayPlus();
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
				- mondayPlus);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return getDate(cal);
	}

	/**
	 * 
	 * @Description: 获得上周日的日期
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getLastSundayDate() {
		Calendar cal = Calendar.getInstance();
		int dayPlus = getMondayPlus() + 1;
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - dayPlus);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return getDate(cal);
	}

	/**
	 * 
	 * @Description: 获得上两周日的日期
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getLastSunday2Date() {
		Calendar cal = Calendar.getInstance();
		int dayPlus = getMondayPlus() + 8;
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - dayPlus);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return getDate(cal);
	}

	/**
	 * 
	 * @Description: 获得当前日期与本周日相差的天数
	 * @param @return
	 * @return int
	 * @throws
	 */
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return dayOfWeek - 1;
		}
	}

	/**
	 * 
	 * @Description: 今天 0点0分
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getToDayDateStart() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String s = sdf.format(new Date());
		return s;
	}

	/**
	 * 
	 * @Description: 现在时间
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getToDayDateEnd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf.format(new Date());
		return s;
	}

	/**
	 * 
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
//	public static String formatDuring(long mss) {
//		long hours = (mss % (60 * 60 * 24)) / (60 * 60);
//		long minutes = (mss % (60 * 60)) / (60);
//		long seconds = (mss % (60));
//		StringBuilder sb = new StringBuilder();
//		if (hours > 0) {
//			sb.append(hours);
//			sb.append("小时");
//		}
//		if (minutes > 0) {
//			sb.append(minutes);
//			sb.append("分");
//		}
//		if (seconds > 0) {
//			sb.append(seconds);
//			sb.append("秒");
//		}
//		return sb.toString();
//	}

	/**
	 * 当前日期结束时间
	 * 
	 * @return
	 */
	public static String getTodayEnd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String s = sdf.format(new Date());
		return s;
	}

	/**
	 * 
	 * @Description:获得两周的时间，从当天开始往前推14天
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getTwoWeekDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 14);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return getDate(cal);
	}
	/**
	 *
	 * @param
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public static String formatDuring(long ms) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ms);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return hour + "时" + minute + "分钟";
	}

	/**
	 * 倒计时为5分钟的时间
	 * @return
     */
	public static long getSeconds() {
		Date currentTime = new Date();
		double minutes = currentTime.getMinutes();
		int minutes2 = ((int) Math.floor(minutes / 5) + 1) * 5;
		int hashMinutes = minutes2 - (minutes2 % 2 == 0 ? 3 : 2);

		double Seconds = currentTime.getSeconds();

//		System.out.println(minutes + " " + minutes2);
//
//		System.out.println(Seconds);

		long time = currentTime.getTime();

		long time2 = time / 1000;
		time2 += (minutes2 - minutes) * 60 - Seconds;
		time2 *= 1000;
		long hashTime = time / 1000;
		hashTime += (hashMinutes - minutes) * 60 - Seconds;
		hashTime *= 1000;

		// Date d = new Date();
		// d.setMinutes(minutes2);
//		 System.out.println(d.toString());
//		System.out.println((new Date(time2)).toString());
//		System.out.println("hashTime: " + (new Date(hashTime)).toString());
//		System.out.println(time + " " + time2 + " hastTime: " + hashTime);

		long time3 = time2 - time;
		// long seconds = (int) Math.abs(time3);
		// System.out.println(seconds);
		return time3;
	}

	/**
	 * 当前时间向前的一个整分时间，个位在0或5的分钟时间
	 * @return
     */
	public static long getBottomSeconds() {
		Date currentTime = new Date();
		double minutes = currentTime.getMinutes();
		int minutes2 = ((int) Math.floor(minutes / 5) + 1) * 5;
		int hashMinutes = minutes2 - (minutes2 % 2 == 0 ? 3 : 2);

		double Seconds = currentTime.getSeconds();

//		System.out.println(minutes + " " + minutes2);
//
//		System.out.println(Seconds);

		long time = currentTime.getTime();

		long time2 = time / 1000;
		time2 += (minutes2 - minutes) * 60 - Seconds;
		time2 *= 1000;


		return time2 -300 * 1000;
	}

	/**
	 * 当前时间向前的一个整分时间，个位在0或5的分钟时间
	 * @return
     */
	public static long getTopSeconds() {
		Date currentTime = new Date();
		double minutes = currentTime.getMinutes();
		int minutes2 = ((int) Math.floor(minutes / 5) + 1) * 5;
		int hashMinutes = minutes2 - (minutes2 % 2 == 0 ? 3 : 2);

		double Seconds = currentTime.getSeconds();

//		System.out.println(minutes + " " + minutes2);
//
//		System.out.println(Seconds);

		long time = currentTime.getTime();

		long time2 = time / 1000;
		time2 += (minutes2 - minutes) * 60 - Seconds;
		time2 *= 1000;

		return time2;
	}

	/**
	 * 获取时间个位在0-4，5-9 之前的，3,7分钟的时间
	 * @return
     */
	public static long getTiming() {
		Date currentTime = new Date();
		double minutes = currentTime.getMinutes();
		int minutesClip = ((int) Math.floor(minutes / 5) + 1) * 5;
		int hashMinutes = minutesClip - (minutesClip % 2 == 0 ? 3 : 2);

		double Seconds = currentTime.getSeconds();

//		System.out.println(minutes + " " + minutes2);
//
//		System.out.println(Seconds);

		long time = currentTime.getTime();

		long hashTime = time / 1000;
		hashTime += (hashMinutes - minutes) * 60 - Seconds;
		hashTime *= 1000;
		return hashTime;
	}
}
