package com.lin.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import com.common.argis.RestService;
import com.common.util.CallbackResult;
import com.common.util.HttpRequester;
import com.common.util.HttpRespons;

@Path("/TestRests")
public class TestRests {
	
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
		String json=RestService.callRestQuery(url,"1=1","","*","false");
//		String json=RestService.callRequestPost(url,new HashMap());//
//		String json=RestService.callRequestGet(url,new HashMap());
		System.out.println(json);
	}
	
	@POST
    @Path("/testpost")  
//    @Produces(MediaType.TEXT_PLAIN) 
	@Produces({ "application/json" })
    public String testpost(@PathParam("name") String name) {
		//http://127.0.0.1:8085/test/rest/TestRests/test/lin?d=99
		String msg = name+"(post):\n" ;
        msg+="{"
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
        return msg;  
    }
	
	@GET
    @Path("/test/{name}")  
//    @Produces(MediaType.TEXT_PLAIN) 
	@Produces({ "application/json" })
    public String test(@PathParam("name") String name) {
		//http://127.0.0.1:8085/test/rest/TestRests/test/lin?d=99
        String msg = name+"(get):\n" ;
        msg+="{"
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
        return msg;  
    }
	
	@GET
    @Path("/getAjaxData")  
	@Produces({ "application/json" })
    public String getAjaxData(@Context HttpServletRequest request,
			 @Context HttpServletResponse response) {
		//http://127.0.0.1:8085/test/rest/TestRests/test/lin?d=99
		String pa1 = request.getParameter("p1");
		String pa2 = request.getParameter("p2");
        String msg ="{"
        		+"\"rop1\":\""+pa1+"\","
        		+"\"rop2\":\""+pa2+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
        return msg;  
    }
	
	@GET
    @Path("/getAjaxDataWithParam/{pp1}")  
	@Produces({ "application/json" })
    public String getAjaxDataWithParam(@PathParam("pp1") String pp1,@Context HttpServletRequest request,
			 @Context HttpServletResponse response) {
		//http://127.0.0.1:8085/test/rest/TestRests/test/lin?d=99
		String rpp1 = request.getParameter("pp1");
		String pa1 = request.getParameter("p1");
		String pa2 = request.getParameter("p2");
        String msg="{"
        		+"\"success\":\"true\","
        		+"\"pp1\":\""+pp1+"\","
        		+"\"rpp1\":\""+rpp1+"\","
        		+"\"rop1\":\""+pa1+"\","
        		+"\"rop2\":\""+pa2+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
        return msg;  
    }
	
	@GET
    @Path("/getAjaxDataWithParam2/{pp1}/{pp2}")  
	@Produces({ "application/json" })
    public String getAjaxDataWithParam2(@PathParam("pp1") String pp1,@PathParam("pp2") String pp2,
//    		@FormParam("p1") String p1,@FormParam("p2") String p2,
    		@Context HttpServletRequest request,
			 @Context HttpServletResponse response) {
		//http://127.0.0.1:8085/test/rest/TestRests/test/lin?d=99
		String ppp1 = request.getParameter("pp1");
		String ppp2 = request.getParameter("pp2");
		String pa1 = request.getParameter("p1");
		String pa2 = request.getParameter("p2");
        String msg="{"
        		+"\"success\":\"true\","
        		+"\"pp1\":\""+pp1+"\","
        		+"\"pp2\":\""+pp2+"\","
//        		+"\"fp1\":\""+p1+"\","
 //       		+"\"fp2\":\""+p2+"\","
        		+"\"rpp1\":\""+ppp1+"\","
        		+"\"rpp2\":\""+ppp2+"\","
        		+"\"rop1\":\""+pa1+"\","
        		+"\"rop2\":\""+pa2+"\","
        		+"\"param\":\""+pp1+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
        return msg;  
    }
	
	@POST
    @Path("/getAjaxPost")  
	@Produces({ "application/json" })
    public String getAjaxPost(@Context HttpServletRequest request,
			 @Context HttpServletResponse response) {
		//http://127.0.0.1:8085/test/rest/TestRests/test/lin?d=99
		String pa1 = request.getParameter("p1");
		String pa2 = request.getParameter("p2");
		String msg="{"
				+"\"rop1\":\""+pa1+"\","
        		+"\"rop2\":\""+pa2+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
        return msg;  
    }
	
	@POST
    @Path("/getAjaxPostWithParam/{pp1}")  
	@Produces({ "application/json" })
    public String getAjaxPostWithParam(@PathParam("pp1") String pp1,
    		@Context HttpServletRequest request,
			 @Context HttpServletResponse response) {
		//http://127.0.0.1:8085/test/rest/TestRests/test/lin?d=99
		String rpa1 = request.getParameter("pp1");
		String pa1 = request.getParameter("p1");
		String pa2 = request.getParameter("p2");
		String msg ="{"
				+"\"success\":\"true\","
        		+"\"pp1\":\""+pp1+"\","
        		+"\"rpp1\":\""+rpa1+"\","
        		+"\"rop1\":\""+pa1+"\","
        		+"\"rop2\":\""+pa2+"\","
//        		+"\"pp2\":\""+pp2+"\","
//        		+"\"fp1\":\""+p1+"\","
//        		+"\"fp2\":\""+p2+"\","
//        		+"\"rpp1\":\""+ppp1+"\","
//        		+"\"rpp1\":\""+ppp2+"\","
//        		+"\"rfp1\":\""+pa1+"\","
//        		+"\"rfp2\":\""+pa2+"\","
        		+"\"param\":\""+pp1+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
        return msg;  
    }
	
	@POST
    @Path("/getAjaxPostWithParam2/{pp1}/{pp2}")  
	@Produces({ "application/json" })
    public String getAjaxPostWithParam2(@PathParam("pp1") String pp1,@PathParam("pp2") String pp2,
    		@FormParam("p1") String p1,@FormParam("p2") String p2,
    		@Context HttpServletRequest request,
			 @Context HttpServletResponse response) {
		//http://127.0.0.1:8085/test/rest/TestRests/test/lin?d=99
		String ppp1 = request.getParameter("pp1");
		String ppp2 = request.getParameter("pp2");
		String pa1 = request.getParameter("p1");
		String pa2 = request.getParameter("p2");
		String op1 = request.getParameter("p3");
		String op2 = request.getParameter("p4");
        		
		String msg ="{"
				+"\"success\":\"true\","
        		+"\"pp1\":\""+pp1+"\","
        		+"\"pp2\":\""+pp2+"\","
        		+"\"fp1\":\""+p1+"\","
        		+"\"fp2\":\""+p2+"\","
        		+"\"rpp1\":\""+ppp1+"\","
        		+"\"rpp2\":\""+ppp2+"\","
        		+"\"rfp1\":\""+pa1+"\","
        		+"\"rfp2\":\""+pa2+"\","
        		+"\"rop3\":\""+op1+"\","
        		+"\"rop4\":\""+op2+"\","
        		+"\"param\":\""+request.getParameterMap().toString()+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
        return msg;  
    }
	
	@GET
	@Path("getAjaxJsonpData")
	@Produces({ "application/json" })
	public String getAjaxJsonpData(@Context HttpServletRequest request,
							 @Context HttpServletResponse response ) throws Exception {
		String pa1 = request.getParameter("p1");
		String pa2 = request.getParameter("p2");
		String msg ="{"
				+"\"rop1\":\""+pa1+"\","
        		+"\"rop2\":\""+pa2+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
		String json = request.getParameter("callbackparam")+"("+msg+")";
		return json;
	}
	
	@GET
	@Path("getAjaxJsonpDataWithParam/{pp1}")
	@Produces({ "application/json" })
	public String getAjaxJsonpDataWithParam(@PathParam("pp1") String pp1,@Context HttpServletRequest request,
							 @Context HttpServletResponse response ) throws Exception {
		String rpp1 = request.getParameter("pp1");
		String op1 = request.getParameter("p1");
		String op2 = request.getParameter("p2");
//		Map result = new HashMap();
//		result.put("minDate", "");
//		result.put("xdName","");
//		result.put("value","");
//		JSONObject object = JSONObjectCache.fromObject(result);
		String msg ="{"
				+"\"success\":\"true\","
        		+"\"pp1\":\""+pp1+"\","
        		+"\"rpp1\":\""+rpp1+"\","
        		+"\"rop1\":\""+op1+"\","
        		+"\"rop2\":\""+op2+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
		String json = request.getParameter("callbackparam")+"("+msg+")";
		return json;
	}
	
	@GET
	@Path("getAjaxJsonpDataWithParam2/{pp1}/{pp2}")
	@Produces({ "application/json" })
	public String getAjaxJsonpDataWithParam2(@PathParam("pp1") String pp1,@PathParam("pp2") String pp2,
  //  		@FormParam("p1") String p1,@FormParam("p2") String p2,
    		@Context HttpServletRequest request,
			@Context HttpServletResponse response ) throws Exception {
//		Map result = new HashMap();
//		result.put("minDate", "");
//		result.put("xdName","");
//		result.put("value","");
//		JSONObject object = JSONObjectCache.fromObject(result);
		String ppp1 = request.getParameter("pp1");
		String ppp2 = request.getParameter("pp2");
		String pa1 = request.getParameter("p1");
		String pa2 = request.getParameter("p2");
        String msg="{"
        		+"\"success\":\"true\","
        		+"\"pp1\":\""+pp1+"\","
        		+"\"pp2\":\""+pp2+"\","
  //      		+"\"fp1\":\""+p1+"\","
  //      		+"\"fp2\":\""+p2+"\","
        		+"\"rpp1\":\""+ppp1+"\","
        		+"\"rpp1\":\""+ppp2+"\","
        		+"\"rop1\":\""+pa1+"\","
        		+"\"rop2\":\""+pa2+"\","
        		+"\"param\":\""+pp1+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
		String json = request.getParameter("callbackparam")+"("+msg+")";
		return json;
	}
	
	@GET
	@Path("getCustomAjaxJsonpWithParam/{pp1}/{pp2}/{f}")
	@Produces({ "application/json" })
	public String getCustomAjaxJsonpWithParam(@PathParam("pp1") String pp1,@PathParam("pp2") String pp2,
			@PathParam("f") String f,
  //  		@FormParam("p1") String p1,@FormParam("p2") String p2,
    		@Context HttpServletRequest request,
			@Context HttpServletResponse response ) throws Exception {
//		Map result = new HashMap();
//		result.put("minDate", "");
//		result.put("xdName","");
//		result.put("value","");
//		JSONObject object = JSONObjectCache.fromObject(result);
		String ppp1 = request.getParameter("pp1");
		String ppp2 = request.getParameter("pp2");
		String pa1 = request.getParameter("p1");
		String pa2 = request.getParameter("p2");
		String rf1=request.getParameter("jsonp");
		String ff="";
		if(rf1!=null && !rf1.equalsIgnoreCase("null") && rf1.trim().length()>0){
			ff=rf1;
		}else{
			ff=f;
		}
        String msg="{"
        		+"\"success\":\"true\","
        		+"\"pp1\":\""+pp1+"\","
        		+"\"pp2\":\""+pp2+"\","
        		+"\"pf\":\""+f+"\","
        		+"\"rf1\":\""+rf1+"\","
        		+"\"rpp1\":\""+ppp1+"\","
        		+"\"rpp1\":\""+ppp2+"\","
        		+"\"rop1\":\""+pa1+"\","
        		+"\"rop2\":\""+pa2+"\","
        		+"\"param\":\""+pp1+"\","
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
		String json = ff+"("+msg+")";
		return json;
	}
	
	@POST
	@Path("getAjaxPostData2")
	@Produces({ "application/json" })
	public String getAjaxPostData2(@FormParam("p1") String p1,
			@FormParam("p2") String p2,
			@FormParam("p3") String p3) throws Exception {
		String json = "";
		String msg ="{"
        		+"	\"features\": [{"
        		+" \"geometry\": {"
        		+"	\"type\": \"polygon\","
        		+"	\"rings\": [[[454361.3190161438,"
        		+"	2719192.59562285],"
        		+"	[454848.05957048084,"
        		+"	2718744.030798265],"
        		+"	[454437.67047564767,"
        		+"	2718514.9764197534],"
        		+"	[454361.3190161438,"
        		+"	2719192.59562285]]],"
        		+"	\"_ring\": 0"
        		+"}"
        		+" }],"
        	+" \"geometryType\": \"esriGeometryPolygon\" "
        +" }";
		return msg;
	}
	
	@POST
	@Path("getJavaPostData/{pp1}/{pp2}")
	@Produces({ "application/json" })
	public String getJavaPostData(@PathParam("pp1") String pp1,@PathParam("pp2") String pp2,
			@FormParam("url") String url,
			@FormParam("p1") String p1,
			@FormParam("p2") String p2,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		CallbackResult callbackResult=new CallbackResult();
		String json="";
		String queryUrl="";
		if(url==null){
			url = request.getParameter("url");
		}
		if(p1==null){
			p1 = request.getParameter("p1");
		}
		if(p2==null){
			p2 = request.getParameter("p2");
		}
		if(url==null || url.trim().length()<5){
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nERROR:url is null");
			return "";
		}
		if(url.indexOf("/arcgis/rest/services/")>-1 && url.indexOf("MapServer")>-1){
			queryUrl=url+"/query";
		}else{
			queryUrl=url;
		}
		try {
			json=RestService.callRequestPost(queryUrl,new HashMap());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			callbackResult.setSuccess(false);
			callbackResult.setMessage(e.getMessage());
			e.printStackTrace();
		}
		callbackResult.setSuccess(true);
		callbackResult.setReturnValue(json);
		return callbackResult.toJson();
	}
	
	/**
	 * @param pp1
	 * @param pp2
	 * @param url:http://192.168.15.83:6080/arcgis/rest/services/ApvlMap/xm_sghy_zdxm/MapServer/0
	 * @param where:"1=1"
	 * @param geometry:""
	 * @param outFields:"*"
	 * @param returnGeometry:"false"
	 * @param p1
	 * @param p2
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Path("getJavaRestData/{pp1}/{pp2}")
	@Produces({ "application/json" })
	public String getJavaRestData(@PathParam("pp1") String pp1,@PathParam("pp2") String pp2,
			@FormParam("url") String url,
			@FormParam("where") String where,
			@FormParam("geometry") String geometry,
			@FormParam("outFields") String outFields,
			@FormParam("returnGeometry") String returnGeometry,
			@FormParam("p1") String p1,
			@FormParam("p2") String p2,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		CallbackResult callbackResult=new CallbackResult();
		String json="";
		String queryUrl="";
		if(url==null || url.trim().length()<5){
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nERROR:url is null");
			return "";
		}
		if(url.indexOf("/arcgis/rest/services/")>-1 && url.indexOf("MapServer")>-1){
			queryUrl=url+"/query";
		}else{
			queryUrl=url;
		}
		try {
			json=RestService.callRestQuery(queryUrl, where, geometry, outFields, returnGeometry);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			callbackResult.setSuccess(false);
			callbackResult.setMessage(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			callbackResult.setSuccess(false);
			callbackResult.setMessage(e.getMessage());
			e.printStackTrace();
		}
		callbackResult.setSuccess(true);
		callbackResult.setReturnValue(json);
		return callbackResult.toJson();
	}
	
	@GET
	@Path("getJavaGetData/{pp1}/{pp2}")
	@Produces({ "application/json" })
	public String getJavaGetData(@PathParam("pp1") String pp1,@PathParam("pp2") String pp2,
			@PathParam("url") String url,
			@PathParam("p1") String p1,
			@PathParam("p2") String p2,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		CallbackResult callbackResult=new CallbackResult();
		String json="";
		String queryUrl="";
		if(url==null){
			url = request.getParameter("url");
		}
		if(p1==null){
			p1 = request.getParameter("p1");
		}
		if(p2==null){
			p2 = request.getParameter("p2");
		}
		if(url==null || url.trim().length()<5){
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nERROR:url is null");
			return "";
		}
		if(url.indexOf("/arcgis/rest/services/")>-1 && url.indexOf("MapServer")>-1){
			queryUrl=url+"/query";
		}else{
			queryUrl=url;
		}
		try {
			json=RestService.callRequestGet(queryUrl,new HashMap());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			callbackResult.setSuccess(false);
			callbackResult.setMessage(e.getMessage());
			e.printStackTrace();
		}
		callbackResult.setSuccess(true);
		callbackResult.setReturnValue(json);
		return callbackResult.toJson();
	}

}
