package org.mariotaku.aria2.android.utils;

import java.text.DecimalFormat;

public class CommonUtils {

	public static String formatSpeedString(String src) {
		if (src == null) return "0 Bytes";
		int speed = 0;
		String result = "";
		try {
			speed = Integer.parseInt(src);
		} catch (NumberFormatException e) {
			return src;
		}
		if (speed < 1024) {
			result = speed + " Bytes";
		} else if (speed > 1024 && speed < 1024 * 1024) {
			result = new DecimalFormat("###.##").format(((double) speed) / 1024) + " KBytes";
		} else {
			result = new DecimalFormat("###.##").format(((double) speed) / 1024 / 1024) + " MBytes";
		}

		return result;
	}

}
