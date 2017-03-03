package com.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
public class JSONObjectCache {
	
	protected static Map cache = new HashMap();
	
	public static JSONObject fromObject(String json) {
		if (cache.containsKey(json)) {
			return (JSONObject)cache.get(json);
		} else {
			JSONObject ojson = JSONObject.fromObject(json);
			cache.put(json, ojson);
			return ojson;
		}
	}

	public static JSONObject fromObject(Collection array) {
		String json = array.toString();
		if (cache.containsKey(json)) {
			return (JSONObject)cache.get(json);
		} else {
			JSONObject ojson = JSONObject.fromObject(array);
			cache.put(json, ojson);
			return ojson;
		}
	}

	public static JSONObject fromObject(Map map) {
		String json = map.toString();
		if (cache.containsKey(json)) {
			return (JSONObject)cache.get(json);
		} else {
			JSONObject ojson = JSONObject.fromObject(map);
			cache.put(json, ojson);
			return ojson;
		}
	}
}
