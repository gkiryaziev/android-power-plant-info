package ua.kiryaziev.PowerPlantInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

	private Date d;
	private DateFormat df;

	public String parseDate(String date, String format) {
		df = new SimpleDateFormat(format);
		try {
			d = df.parse(date);
			return String.format("%td %tb %tY / %tT", d, d, d, d);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
