package org.mariotaku.aria2;

import java.util.HashMap;

public class Version {

	/**
	 * Version number of the program in string.
	 */
	public String version = null;

	/**
	 * List of enabled features. Each feature name is of type string.
	 */
	public Object[] enabledFeatures = new Object[] {};

	public Version() {

	}

	public Version(HashMap<String, Object> data) {
		version = (String) data.get("version");
		enabledFeatures = (Object[]) data.get("enabledFeatures");
	}
}