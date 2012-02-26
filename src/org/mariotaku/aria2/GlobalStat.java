package org.mariotaku.aria2;

import java.util.HashMap;

public class GlobalStat extends CommonItem {

	/**
	 * Overall download speed (byte/sec).
	 */
	public String downloadSpeed = "0";

	/**
	 * Overall upload speed(byte/sec).
	 */
	public String uploadSpeed = "0";

	/**
	 * The number of active downloads.
	 */
	public String numActive = "0";

	/**
	 * The number of waiting downloads.
	 */
	public String numWaiting = "0";

	/**
	 * The number of stopped downloads.
	 */
	public String numStopped = "0";

	public GlobalStat(HashMap<String, Object> data) {
		init(data);
	}
}