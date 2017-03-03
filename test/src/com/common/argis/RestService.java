/**
 * 
 */
package com.common.argis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.common.util.ConstansURL;
import com.common.util.Common;
import com.common.util.HttpRequester;
import com.common.util.HttpRespons;
import com.common.util.JSONObjectCache;
import com.common.util.ReflectBeans;

/**
 * @author lianghuaxin
 *
 */
public class RestService {
	/**
	 * 全部默认参数查询
	 * @param url
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public static String callRestQuery(String url) throws Exception{
		ArcgisRestParam restParam = new ArcgisRestParam("json","1=1",null);
		return callRestQuery(url,restParam);
	}
	/**
	 * 只通过条件去过滤
	 * @param url
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public static String callRestQuery(String url,String where) throws Exception{
		ArcgisRestParam restParam = new ArcgisRestParam("json",where,null);
		return callRestQuery(url,restParam);
	}
	
	/**
	 * 通过条件、空间信息去过滤
	 * @param url
	 * @param where
	 * @param geometry
	 * @return
	 * @throws Exception
	 */
	public static String callRestQuery(String url,String where, String geometry) throws Exception{
		ArcgisRestParam restParam = new ArcgisRestParam("json",where,geometry);
		return callRestQuery(url,restParam);
	}
	
	/**
	 * 通过条件、空间信息去过滤，限定返回字段
	 * @param url
	 * @param where
	 * @param geometry
	 * @param outFields
	 * @return
	 * @throws Exception
	 */
	public static String callRestQuery(String url,String where, String geometry,String outFields) throws Exception{
		ArcgisRestParam restParam = new ArcgisRestParam("json",where,geometry,"true",outFields);
		return callRestQuery(url,restParam);
	}
	
	/**
	 * 通过条件、空间信息去过滤，限定返回字段、是否返回geometry
	 * @param url
	 * @param where
	 * @param geometry
	 * @param outFields
	 * @return
	 * @throws Exception
	 */
	public static String callRestQuery(String url,String where, String geometry,String outFields,String returnGeometry) throws Exception{
		ArcgisRestParam restParam = new ArcgisRestParam("json",where,geometry,returnGeometry,outFields);
		return callRestQuery(url,restParam);
	}
	
	/**
	 * rest查询
	 * @param url 服务图层地址
	 * @param restParam ArcGIS Rest查询服务所需的参数
	 * @return 1、处理得到结果，返回未经处理过的json数据
	 * 2、处理过程中出错，返回{success:false,message:XXXXXXX}
	 * @throws Exception
	 */
	public static String callRestQuery(String url,ArcgisRestParam restParam)throws IOException{
		String r = "";
		try{
			if(Common.isCheckNull(url)){
				throw new Exception("服务图层地址为空。");
			}
			if(Common.isCheckNull(restParam)) restParam = new ArcgisRestParam("json","false");
			Map param = ReflectBeans.beanToMap(restParam);
			String json = callRequestPost(url, param);
			return json;
		}catch (Exception e) {
			e.printStackTrace();
			r = String.format("{success:false,message:%s}", e.getMessage());
		}
		return r;
	}
	
	/**
	 * 只查询统计记录的数量
	 * @param url 服务图层地址
	 * @param restParam ArcGIS Rest查询服务所需的参数
	 * @return 1、处理得到结果，返回未经处理过的json数据
	 * 2、程序处理过程中出错，返回{success:false,message:XXXXXXX}
	 * @throws Exception
	 */
	public static String callRestCountOnly(String url,ArcgisRestParam restParam){
		String r = "";
		try{
			if(Common.isCheckNull(url)){
				throw new Exception("服务图层地址为空。");
			}
			if(Common.isCheckNull(restParam)) restParam = new ArcgisRestParam("json","false");
			if(Common.isCheckNull(restParam.getReturnCountOnly()) || restParam.getReturnCountOnly().equals("false")){
				restParam.setReturnCountOnly("true");
			}
			if(Common.isCheckNull(restParam.getWhere())){
				restParam.setWhere("1=1");
			}
			return callRestQuery(url,restParam);
		}catch (Exception e) {
			e.printStackTrace();
			r = String.format("{success:false,message:%s}", e.getMessage());
		}
		return r;
	}
	
	/**
	 * 只查询统计记录的数量
	 * @param url 服务图层地址
	 * @param where 条件
	 * @param geometry 空间信息
	 * @return 1、处理得到相应条件记录的个数
	 * 2、-1,就是程序查询出错了
	 * @throws IOException
	 */
	public static int callRestCountOnly(String url,String where,String geometry)throws IOException{
		ArcgisRestParam restParam = new ArcgisRestParam("json",where,geometry);
		if(Common.isCheckNull(restParam.getWhere())){
			restParam.setWhere("1=1");
		}
		String json = callRestCountOnly(url,restParam);
		JSONObject r = JSONObject.fromObject(json);
		if(r.containsKey("count")){
			return r.getInt("count");
		}
		return -1;
	}
	
	/**
	 * 查询只返回各记录的id
	 * @param url 服务图层地址
	 * @param restParam ArcGIS Rest查询服务所需的参数
	 * @return 1、处理得到结果，返回未经处理过的json数据:{"objectIdFieldName":"OBJECTID","objectIds":[387,388]}
	 * 2、处理过程中出错，返回{success:false,message:XXXXXXX}
	 * @throws Exception
	 */
	public static String callRestReturnIdsOnly(String url,ArcgisRestParam restParam){
		String r = "";
		try{
			if(Common.isCheckNull(url)){
				throw new Exception("服务图层地址为空。");
			}
			if(Common.isCheckNull(restParam)) restParam = new ArcgisRestParam("json","false");
			if(Common.isCheckNull(restParam.getReturnIdsOnly()) || restParam.getReturnIdsOnly().equals("false")){
				restParam.setReturnCountOnly("false");
				restParam.setReturnIdsOnly("true");
			}
			return callRestQuery(url,restParam);
		}catch (Exception e) {
			e.printStackTrace();
			r = String.format("{success:false,message:%s}", e.getMessage());
		}
		return r;
	}
	
	/**
	 * 查询只返回各记录的id,处理失败则返回空数组，不关注处理失败的错误
	 * @param url 服务图层地址
	 * @param where 条件
	 * @param geometry 空间信息
	 * @return 1、id的数组
	 * 2、空数组
	 * @throws IOException
	 */
	public static String callRestReturnIdsOnly(String url,String where,String geometry)throws IOException{
		ArcgisRestParam restParam = new ArcgisRestParam("json",where,geometry);
		String json = callRestCountOnly(url,restParam);
		JSONObject r = JSONObject.fromObject(json);
		if(r.containsKey("objectIds")){
			return r.getJSONArray("objectIds").toString();
		}
		return "[]";
	}
	
	/**
	 * rest服务identify查询
	 * @param url
	 * @param geometry
	 * @return
	 * @throws Exception 
	 */
	public static String callRestIdentify(String url,String geometryType,String geometry,String Layers,String returnGeometry) throws Exception {
		if(Common.isCheckNull(url)){
			throw new Exception("服务图层地址为空。");
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", "json");
		param.put("geometryType", geometryType);//面状为esriGeometryPolygon
		if(!Common.isCheckNull(geometry)){
			param.put("geometry", geometry);
		}
		param.put("sr", ConstansURL.get("wkt"));//传入图层坐标系
		param.put("Layers", Layers);//查询图层ID，格式 n,n1,n2.....
		param.put("tolerance", "3");//容差
		param.put("mapExtent", "4626.11,-30913.98,118926.34,112490.46");//广州范围
		param.put("imageDisplay", "256,256,96");//图片参数
		param.put("returnGeometry", returnGeometry);
		param.put("outSR", ConstansURL.get("wkt"));//结果坐标系
		HttpRequester request = new HttpRequester();
		HttpRespons respons = request.sendPost(url, param);
		String json = respons.getContent();
		return json;
	}
	
	/**
	 * rest服务identify查询
	 * @param url
	 * @param geometry
	 * @return
	 * @throws Exception 
	 */
	public static String callSimplifyGpService(String geometries) throws Exception {
		String url = ConstansURL.get("Geometry.simplify");
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", "json");
		param.put("sr", "2435");
		param.put("Geometries", geometries);
		
		HttpRequester request = new HttpRequester();
		HttpRespons respons = request.sendPost(url, param);
		String json = respons.getContent();
		
		JSONObject jsonResult = JSONObjectCache.fromObject(json);
		
		if (jsonResult.containsKey("error")) return jsonResult.toString();
		
		JSONArray results =  jsonResult.getJSONArray("geometries");
		JSONObject value =  (JSONObject)results.getJSONObject(0);
		return value.toString();
	}
	
	/**
	 * 调用发布好的GP服务
	 * @param name 配置在ConstantURL.properties的GP名称
	 * @param param gp服务调用所需的参数
	 * @return 1、处理得到结果，返回未经处理过的json数据
	 * 2、处理过程中出错，返回{success:false,message:XXXXXXX}
	 * @throws IOException
	 */
	public static String callGpService(String name,Map<String,String> param) throws Exception{
		String url;
		try{
			url = ConstansURL.get(name);
		}catch (MissingResourceException e) {
			throw new Exception(name+"服务图层地址未配置。");
		}
		if(Common.isCheckNull(url)){
			throw new Exception("GP服务图层地址为空。");
		}
		if(Common.isCheckNull(param)) param = new HashMap<String,String>();
		if(!param.containsKey("f"))param.put("f", "json");
		HttpRequester request = new HttpRequester();
		HttpRespons respons = request.sendPost(url, param);
		String json = respons.getContent();
		return json;
	}
	/**
	 * 获取GP服务的buffer
	 * @param name 配置的GP服务名
	 * @param geometry gp服务调用的inputGeometry：{\"geometry\":{\"rings\":[[[478867,2719466],[478867,2719466],[478867,2719466],[478867,2719466]]]}}
	 * @param distances 缓冲距离 
	 * @return
	 */
	public static String callBuffer(String geometry,String distances)throws Exception{
		String name = "GpTool.buffer";
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", "json");
		//inputGeometry格式： "{\"features\":[{\"geometry\":{\"rings\":[[[478867,2719466],[478867,2719466],[478867,2719466],[478867,2719466]]]}}]}"
		System.out.println(String.format("{\"features\":[%s]}", geometry));
		param.put("inputGeometry",  String.format("{\"features\":[%s]}", geometry));
		//Distance格式：{"distance": 100,"units": "esriMeters"}
		String pgValue = String.format("{\"distance\": %s,\"units\" :\"esriMeters\"}", distances);	//评估参考值
		param.put("Distance", pgValue);
		String json = callGpService(name,param);
		JSONObject jsonResult = JSONObjectCache.fromObject(json);
		JSONObject value = jsonResult.getJSONArray("results").getJSONObject(0).getJSONObject("value");
		JSONArray features =  value.getJSONArray("features");
		if (features.size() == 0) {
			throw new Exception("无法获取缓冲地块：返回结果为空");
		}
		JSONObject feature = features.getJSONObject(0);
		return feature.getString("geometry");
	}
	
	/**
	 * Clip_intersect,冲突图斑检测所用的gp切割服务
	 * @param inputFeature
	 * @return
	 * @throws IOException
	 */
	public static String callClipIntersect (String inputFeature) throws Exception {
		String name = "GpTool.ClipIntersect";
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", "json");
		//inputFeature格式： "{\"features\":[{\"geometry\":{\"rings\":[[[478867,2719466],[478867,2719466],[478867,2719466],[478867,2719466]]]}}]}"
		param.put("TG_ClipFeatures", inputFeature);
		param.put("CG_ClipFeatures", inputFeature);
		String json = callGpService(name,param);
		return json;
	}
	/**
	 * KZX_Clip,控制线检测所用的gp切割服务
	 * @param inputFeature
	 * @return
	 * @throws IOException
	 */
	public static String callKzxClip(String inputFeature) throws Exception {
		String name = "GpTool.KZX_Clip";
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", "json");
		//inputFeature格式： "{\"features\":[{\"geometry\":{\"rings\":[[[478867,2719466],[478867,2719466],[478867,2719466],[478867,2719466]]]}}]}"
		param.put("Clip_Features", inputFeature);
		//服务调用请求
		String json = callGpService(name,param);
		return json;
	}
	
	/**
	 * rest服务CG_Clip查询
	 * @param inputFeature
	 * @return
	 * @throws Exception
	 */
	public static String callCGClip(String inputFeature) throws Exception {
		String name = "GpTool.CG_Clip";
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", "json");
		//inputFeature格式： "{\"features\":[{\"geometry\":{\"rings\":[[[478867,2719466],[478867,2719466],[478867,2719466],[478867,2719466]]]}}]}"
		param.put("clipFeatureCG", inputFeature);
		//服务调用请求
		String json = callGpService(name,param);
		return json;
	}
	/**
	 * Tg_Clip,土规统计所用的gp切割服务
	 * @param inputFeature
	 * @return
	 * @throws IOException
	 */
	public static String callTgClip(String inputFeature) throws Exception {
		String name = "GpTool.TG_Clip";
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", "json");
		//inputFeature格式： "{\"features\":[{\"geometry\":{\"rings\":[[[478867,2719466],[478867,2719466],[478867,2719466],[478867,2719466]]]}}]}"
		param.put("clipFeatureTG", inputFeature);
		//服务调用请求
		String json = callGpService(name,param);
		return json;
	}
	/**
	 * 
	 * @param clipFeatures
	 * @param inputFeatures
	 * @return
	 * @throws IOException
	 */
	public static JSONObject callIntersect(String clipFeatures, String inputFeatures) throws Exception {
		String name = "GpTool.Intersect";
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", "json");
		//inputFeature和clipFeatures的格式： "{\"features\":[{\"geometry\":{\"rings\":[[[478867,2719466],[478867,2719466],[478867,2719466],[478867,2719466]]]}}]}"
		param.put("intersectFeatures", clipFeatures);
		param.put("inputFeatures", inputFeatures);

		//服务调用请求
		String json = callGpService(name,param);
		//返回结果解析
		JSONObject jsonResult = JSONObjectCache.fromObject(json);
		if (jsonResult.containsKey("error")) return jsonResult;
		JSONArray results =  jsonResult.getJSONArray("results");
		JSONObject value =  results.getJSONObject(0).getJSONObject("value");
		value.discard("exceededTransferLimit");
		JSONArray features =  value.getJSONArray("features");
		for (int i = 0; i < features.size(); i++) {
			JSONObject feature = features.getJSONObject(i);
			feature.discard("attributes");
		}
		return value;
	}
	/**
	 * 调用GpTool.Erase
	 * @param clipFeatures
	 * @param inputFeatures
	 * @return JSONObject
	 * @throws Exception 
	 */
	public static JSONObject callEraseGpService(String clipFeatures, String inputFeatures) throws Exception {
		String url = ConstansURL.get("GpTool.Erase");
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", "json");
		param.put("clipFeatures", inputFeatures);
		param.put("inputFeatures", clipFeatures);
		//服务调用请求
		HttpRequester request = new HttpRequester();
		HttpRespons respons = request.sendPost(url, param);
		String json = respons.getContent();
		//返回结果解析
		JSONObject jsonResult = JSONObjectCache.fromObject(json);
		if (jsonResult.containsKey("error")) return jsonResult;
		JSONArray results =  jsonResult.getJSONArray("results");
		JSONObject value =  results.getJSONObject(0).getJSONObject("value");
		value.discard("exceededTransferLimit");
		JSONArray features =  value.getJSONArray("features");
		for (int i = 0; i < features.size(); i++) {
			JSONObject feature = features.getJSONObject(i);
			feature.discard("attributes");
		}
		return value;
	}
	/**
	 * 通过服务地址获取图层字段
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String getFieldsByurl(String url)throws Exception{
		if(Common.isCheckNull(url)){
			throw new Exception("获取图层字段出错了：服务图层地址为空。");
		}			
		HttpRequester request = new HttpRequester();
		HttpRespons respons = request.sendPost(url);
		String json = respons.getContent();
		JSONObject object = JSONObject.fromObject(json);
		if(!object.containsKey("fields")){
			throw new Exception("获取图层字段出错了：给予的图层服务没有查到相关字段。");
		}
		return object.getJSONArray("fields").toString();
	}
	
	public static String getRestMapServerUrl(String wmsUrl, String topicName) throws Exception {
		String[] urlsplit = wmsUrl.split("/WMSServer")[0].split("services");
		String url = urlsplit[0]+"rest/services"+urlsplit[1]+"?f=pjson";
		String layerTableId = null;
		HttpRequester request = new HttpRequester();
		HttpRespons respons = request.sendGet(url, null);
		String json = respons.getContent();
		JSONArray layers = JSONObjectCache.fromObject(json).getJSONArray("layers");
		for (int i = 0; i < layers.size(); i++) {
			JSONObject layer = layers.getJSONObject(i);
			Long id = layer.getLong("id");
			String name = layer.getString("name");
			if (topicName.equalsIgnoreCase(name)) {
				layerTableId = String.valueOf(id);
			}
		}
		if (layerTableId == null) {
			throw new Exception("layerTableId is null");
		}
		url = urlsplit[0]+"rest/services"+urlsplit[1]+"/"+layerTableId+"/query";
		return url;
	}
	/**
	 * 发起一个post请求
	 * @param url
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static String callRequestPost(String url,Map<String, String> param) throws IOException {
		HttpRequester request = new HttpRequester();
		HttpRespons respons = request.sendPost(url, param);
		return respons.getContent();
	}
	/**
	 * 发起一个get请求
	 * @param url
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static String callRequestGet(String url,Map<String, String> param) throws IOException {
		HttpRequester request = new HttpRequester();
		HttpRespons respons = request.sendGet(url, param);
		return respons.getContent();
	}

}
