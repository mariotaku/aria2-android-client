package org.mariotaku.aria2.android;

import java.util.HashMap;

public class SessionInfo extends CommonItem {

	public String sessionId = null;

	public SessionInfo() {
	}

	public SessionInfo(HashMap<String, Object> data) {
		init(data);
	}
}