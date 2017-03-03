package com.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@SuppressWarnings("unchecked")
public class JSONArrayCache {
	
	protected static Map cache = new HashMap();
	
	public static JSONArray fromObject(String json) {
		if (cache.containsKey(json)) {
			return (JSONArray)cache.get(json);
		} else {
			JSONArray ojson = JSONArray.fromObject(json);
			cache.put(json, ojson);
			return ojson;
		}
	}

	public static JSONArray fromObject(Collection array) {
		String json = array.toString();
		if (cache.containsKey(json)) {
			return (JSONArray)cache.get(json);
		} else {
			JSONArray ojson = JSONArray.fromObject(array);
			cache.put(json, ojson);
			return ojson;
		}
	}

	public static JSONArray fromObject(Map map) {
		String json = map.toString();
		if (cache.containsKey(json)) {
			return (JSONArray)cache.get(json);
		} else {
			JSONArray ojson = JSONArray.fromObject(map);
			cache.put(json, ojson);
			return ojson;
		}
	}

	public static JSONArray fromObject(Collection array, JsonConfig jsonConfig) {
		String json = array.toString();
		if (cache.containsKey(json)) {
			return (JSONArray)cache.get(json);
		} else {
			JSONArray ojson = JSONArray.fromObject(array, jsonConfig);
			cache.put(json, ojson);
			return ojson;
		}
	}

	public static JSONArray fromObject(Object obj) {
		if (cache.containsKey(obj)) {
			return (JSONArray)cache.get(obj);
		} else {
			JSONArray ojson = JSONArray.fromObject(obj);
			cache.put(obj, ojson);
			return ojson;
		}
	}
	
}
