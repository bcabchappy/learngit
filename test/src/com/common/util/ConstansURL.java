package com.common.util;

import java.util.ResourceBundle;

public class ConstansURL {
	/**
	 * 读取classPath目录下{propertiesFileName}.properties 
	 * Hxin
	 */
	public static  ResourceBundle getBundle(String propertiesFileName){
		return ResourceBundle.getBundle(propertiesFileName);
	}
	
	/**
	 * 读取application.properties上配置的属性信息
	 * @throws Exception
	 * Hxin
	 */
	public static String getApplicationPropertyByKey(String key) throws Exception{
		try{
			String value = getBundle("application").getString(key);
			if (value == null) return "";
			value = value.replaceAll("/$", "");
			return value;
		}catch (Exception e) {
			throw new Exception("配置application.properties不存在 "+key+" 字段。");
		}
	}
	
	/**
	 * 读取application.properties上配置的属性信息
	 * @throws Exception
	 * Hxin
	 */
	public static String getApplicationPropertyByKey(String key,String replacement) throws Exception{
		try{
			String value = getBundle("application").getString(key);
			if (value == null) return "";
			value = value.replaceAll("/$", "");
			return value;
		}catch (Exception e) {
			System.out.println("配置application.properties不存在 "+key+" 字段。");
			//throw new Exception("配置application.properties不存在 "+key+" 字段。");
		}
		return replacement;
	}
	/**
	 * 读取application.properties上配置的属性信息。
	 * 查找不到，用替代者（replacement）进行赋值
	 * 不抛出错误
	 * Hxin
	 */
	public static String getInPropertyFile(String key,String replacement){
		try{
			String value = getBundle("application").getString(key);
			if (value == null) return "";
			value = value.replaceAll("/$", "");
			return value;
		}catch (Exception e) {
			System.out.println("配置ConstantURL.properties不存在 "+key+" 字段。");
			//throw new Exception("配置application.properties不存在 "+key+" 字段。");
		}
		return replacement;
	}
	
	/**
	 * 读取ConstantURL.properties上配置的信息
	 * Hxin
	 * @throws Exception 
	 */
	public static String get(String key) throws Exception{
		try{
			String value = getBundle("ConstantURL").getString(key);
			if (value == null) return "";
			value = value.replaceAll("/$", "");
			return value;
		}catch (Exception e) {
			throw new Exception("配置ConstantURL.properties不存在 "+key+" 字段。");
		}
	}
	
	/**
	 * 读取ConstantURL.properties上配置的信息
	 * 查找不到，用替代者（replacement）进行赋值
	 * 不抛出错误
	 * Hxin
	 */
	public static String get(String key,String replacement){
		try{
			String value = getBundle("ConstantURL").getString(key);
			if (value == null) return "";
			value = value.replaceAll("/$", "");
			return value;
		}catch (Exception e) {
			System.out.println("配置ConstantURL.properties不存在 "+key+" 字段。");
		}
		return replacement;
	}
	
	/**
	 * 读取ConstantURL.properties上配置的查询路径
	 * Hxin
	 * @throws Exception 
	 */
	public static String getQuery(String key) throws Exception {
		return String.format(get(key)+"%s", "/query");
	}
	
	/**
	 * 读取ConstantURL.properties上配置的查询路径
	 * 查找不到，用替代者（replacement）进行赋值
	 * 不抛出错误
	 * Hxin
	 */
	public static String getQuery(String key,String replacement){
		return String.format(get(key,replacement)+"%s", "/query");
	}
	
	/**
	 * 读取ConstantURL.properties上配置的插入空间信息的路径
	 * Hxin
	 * @throws Exception 
	 */
	public static String getAddFeatures(String key) throws Exception {
		return String.format(get(key)+"%s", "/addFeatures");
	}
	
	/**
	 * 读取ConstantURL.properties上配置的插入空间信息的路径
	 * 查找不到，用替代者（replacement）进行赋值
	 * 不抛出错误
	 * Hxin
	 */
	public static String getAddFeatures(String key,String replacement){
		return String.format(get(key,replacement)+"%s", "/addFeatures");
	}
	
	/**
	 * 读取ConstantURL.properties上配置的修改空间信息的路径
	 * @throws Exception 
	 */
	public static String getUpdateFeatures(String key) throws Exception {
		return String.format(get(key)+"%s", "/updateFeatures");
	}
	
	/**
	 * 读取ConstantURL.properties上配置的修改空间信息的路径
	 * 查找不到，用替代者（replacement）进行赋值
	 * 不抛出错误
	 * Hxin
	 */
	public static String getUpdateFeatures(String key,String replacement){
		return String.format(get(key,replacement)+"%s", "/updateFeatures");
	}
	
	/*
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
//		String polygons = "";
//		String url = ConstansURL.get("GpTool.Buffer");
//		ServiceUtils.getBufferGemotry(polygons, "10");
		String url = ConstansURL.getQuery("重点项目");
		System.out.println(url);
	}
}
