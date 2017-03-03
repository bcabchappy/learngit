package com.common.util;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
	
	public static Map cache = new HashMap();
	
	public static void cleanServCache() {
		HttpRequester.cache.clear();
	}
	
	public static void cleanJSONCache() {
		JSONArrayCache.cache.clear();
		JSONObjectCache.cache.clear();
	}
	
	public static void cleanAll() {
		cleanServCache();
		cleanJSONCache();
		cache.clear();
	}
	
	public static void main(String[] args) {

	}
}
