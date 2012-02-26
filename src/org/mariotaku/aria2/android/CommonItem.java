package org.mariotaku.aria2.android;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CommonItem {

	public Object getRaw(String name) {

		name = name.replaceAll("_", "-");
		if ("continue-download".equals(name)) name = "continue";

		Object value = null;

		try {
			Field field = getClass().getField(name);
			value = field.get(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return value;
	}

	public void init(HashMap<String, Object> data) {
		Set<String> keys = data.keySet();

		for (String key : keys) {
			setRaw(key, data.get(key));
		}
	}

	public void init(String[] values) {
		Field[] fields = getClass().getFields();
		try {
			for (String value : values) {
				String[] option = value.split(":", 2);
				if (option.length != 2) continue;
				for (Field field : fields) {
					if (field.getModifiers() == Modifier.PUBLIC) {
						if (field.getName().equals(option[0])) {
							if (field.getType() == boolean.class) {
								field.setBoolean(this, Boolean.parseBoolean(option[1]));
							} else if (field.getType() == int.class) {
								field.setInt(this, Integer.parseInt(option[1]));
							} else if (field.getType() == long.class) {
								field.setLong(this, Long.parseLong(option[1]));
							} else {
								field.set(this, (Object) option[1]);
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setRaw(String name, Object value) {

		name = name.replaceAll("-", "_");
		if ("continue".equals(name)) name = "continue_download";

		try {
			Field field = getClass().getField(name);
			field.set(this, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

	}

	public String[] toStringArray() {
		Field[] fields = getClass().getFields();
		ArrayList<String> list = new ArrayList<String>();

		try {
			for (Field field : fields) {
				if (field.getModifiers() == Modifier.PUBLIC) {
					Object value = field.get(this);
					if (field.getType() == String.class) {
						if (value == null || "".equals(value)) continue;
					}
					list.add(field.getName() + ":" + value);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return list.toArray(new String[list.size()]);
	}
}
