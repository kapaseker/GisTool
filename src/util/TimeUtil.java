package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 返回日期
 * @author i-xiepenggang
 *
 */
public class TimeUtil {
	private static Date mDate = new Date();
	/**
	 * 
	 * @return 日期
	 */
	public static String getTime() {

		// mDate.get
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String reTime = format2.format(mDate);

		return reTime;

		// DateFormat.
	}
}
