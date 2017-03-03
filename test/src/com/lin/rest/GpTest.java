package com.lin.rest;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.Set;

import javax.ws.rs.Path;

import net.sf.json.JSONObject;

import com.common.util.Common;
import com.common.util.HttpRequester;
import com.common.util.HttpRespons;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

@Path("/GpTest")
public class GpTest {
	public static void main(String[] args) throws Exception {
//		Random rdm = new Random(System.currentTimeMillis());
//      int radm = Math.abs(rdm.nextInt())%32+1;
		String url = "http://127.0.0.1:8086/agsupport/rest/differAnalyseStastic/analyzeMain2";
		Map<String, String> param = new HashMap<String, String>();
		param.put("filePath", "E:\\同安洪塘南片区.dwg");
		HttpRequester request = new HttpRequester();
//		HttpRespons respons = request.sendPost(url, param);
//		String json = respons.getContent();
//		System.out.println(json);
		
		url="http://192.168.15.83:6080/arcgis/rest/services/ApvlMap/xm_sghy_zdxm/MapServer/0";
		url+="/query";
//		String json=RestService.callRestQuery(url,"1=1","","*","false");
//		String json=RestService.callRequestPost(url,new HashMap());//
//		String json=RestService.callRequestGet(url,new HashMap());
//		System.out.println(json);
		
		url="http://192.168.15.83:6080/arcgis/rest/services/test/impShp/GPServer/impShp/execute";
		Map<String,String> map=new HashMap();
		map.put("远程shp文件地址", "http://192.168.15.83:8083/dgsp/upload/shp/shp1.shp");
//		String json=callGpService(url,map);
//		System.out.println(json);
		String paramstr="{'displayFieldName':'a','geometryType':'b','spatialReference':'c','features':'d'}";
		JsonParser parser = new JsonParser();
		JsonElement el = parser.parse(paramstr);
		JsonObject jsonObj = null;
		if(el.isJsonObject()){
			jsonObj = el.getAsJsonObject();  
		}
		
		Gson gson=new Gson();
//		Map<String, Person> maps = gson.fromJson(json, new TypeToken<Map<String, Person>>() {}.getType());
		Map<String,String> maps = gson.fromJson(paramstr, new TypeToken<Map<String, String>>() {}.getType());
		Iterator<String> itkey = maps.keySet().iterator(); 
		while(itkey.hasNext()){
			String key=itkey.next();
			System.out.println(key+":"+maps.get(key));
		}
		JsonObject p=gson.fromJson(paramstr,JsonObject.class);
		Iterator<Map.Entry<String,JsonElement>> it = jsonObj.entrySet().iterator(); 
		param=new HashMap<String, String>();
//		while (it.hasNext()) {  
//			JsonElement ell = (JsonElement) it.next(); 
//			System.out.println(ell.+":"+it.next().getValue()+"\n");
//			String str = it.next().getKey(); 
//		  System.out.println(it.next().getKey()+":"+it.next().getValue()+"\n");
//		  param.put(str, it.next().getValue().getAsString());
//		} 
		
//		JSONObject p=JSONObject.fromObject(paramstr);
//		Iterator itt= p.entrySet().iterator();
//		toMap();
//		while (itt.hasNext()) {
//			Object str=itt.next(); 
//			String[] aa=getFiledName(str);
//			for (int i=0;i<aa.length;i++){
//				System.out.println(aa[i]);
//			}
//			method(str);
//		  System.out.println(it.next().getKey()+":"+it.next().getValue()+"\n");
//		  param.put(str, it.next().getValue().getAsString());
//		} 
		
		Map a=param;
	}
	
	public static String callGpService(String gpUrl,Map<String,String> param) throws Exception{
		String url;
		try{
			url = gpUrl;
		}catch (MissingResourceException e) {
			throw new Exception(gpUrl+"服务图层地址未配置。");
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
     * 将JSONObjec对象转换成Map-List集合 
     * @param json 
     * @return 
     */  
    public static Map<String, Object> toMap(JsonObject json){  
        Map<String, Object> map = new HashMap<String, Object>();  
        Set<Entry<String, JsonElement>> entrySet = json.entrySet();  
        for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ){  
            Entry<String, JsonElement> entry = iter.next();  
            String key = entry.getKey();  
            Object value = entry.getValue();  
            if(value instanceof JsonArray)  
                map.put((String) key, toList((JsonArray) value));  
            else if(value instanceof JsonObject)  
                map.put((String) key, toMap((JsonObject) value));  
            else  
                map.put((String) key, value);  
        }  
        return map;  
    } 
    
    /** 
     * 将JSONArray对象转换成List集合 
     * @param json 
     * @return 
     */  
    public static List<Object> toList(JsonArray json){  
        List<Object> list = new ArrayList<Object>();  
        for (int i=0; i<json.size(); i++){  
            Object value = json.get(i);  
            if(value instanceof JsonArray){  
                list.add(toList((JsonArray) value));  
            }  
            else if(value instanceof JsonObject){  
                list.add(toMap((JsonObject) value));  
            }  
            else{  
                list.add(value);  
            }  
        }  
        return list;  
    } 
	
	 /** 
	    * 获取属性名数组 
	    * */  
	   private static String[] getFiledName(Object o){  
	    Field[] fields=o.getClass().getDeclaredFields();  
	        String[] fieldNames=new String[fields.length];  
	    for(int i=0;i<fields.length;i++){  
	        System.out.println(fields[i].getType());  
	        fieldNames[i]=fields[i].getName();  
	    }  
	    return fieldNames;  
	   } 
	
	/** 
     * 通过对象得到所有的该对象所有定义的属性值 
     * @param obj 目标对象 
     */  
    public static void method(Object obj){  
       try{  
           Class clazz = obj.getClass();  
           Field[] fields = obj.getClass().getDeclaredFields();//获得属性  
           for (Field field : fields) {  
               PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);  
               Method getMethod = pd.getReadMethod();//获得get方法  
               Object o = getMethod.invoke(obj);//执行get方法返回一个Object  
               System.out.println(o);  
           }  
       }catch (Exception e) {  
           e.printStackTrace();  
       }   
     }  
      
    /** 
     * 通过对象和具体的字段名字获得字段的值 
     * @param obj 目的对象 
     * @param filed 字段名 
     * @return 通过字段名得到字段值 
     */  
    public static Object method(Object obj,String filed)   {  
       try {  
           Class clazz = obj.getClass();  
           PropertyDescriptor pd = new PropertyDescriptor(filed,clazz);  
           Method getMethod = pd.getReadMethod();//获得get方法  
           Object o = getMethod.invoke(obj);//执行get方法返回一个Object  
           return o;  
       }catch (Exception e) {  
           e.printStackTrace();  
           return null;  
       }   
     }

}
