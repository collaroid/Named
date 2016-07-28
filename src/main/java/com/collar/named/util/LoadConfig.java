package com.collar.named.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class LoadConfig {

	private static Map<String, String> propertiesMap = new HashMap<String, String>();

	static {
		reload();
	}

	public static void reload() {
		Properties properties = new Properties();


		try {
			InputStreamReader in = new InputStreamReader(new LoadConfig().getClass().getClassLoader()
					.getResourceAsStream("system.properties"), "UTF-8");
			properties.load(in);
			in.close();

			// 遍历一下获取到的HashTable，把内容放到Map里
			for (Entry<Object, Object> entry : properties.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();

				propertiesMap.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String get(String name) {
		return propertiesMap.get(name);
	}

	public static void main(String[] args) {
		System.out.println(get("girl_character"));
	}
}