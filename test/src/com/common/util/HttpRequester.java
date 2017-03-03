package com.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
   
/** 
 * HTTP请求对象 
 *  
 * @author hunter 
 */  
public class HttpRequester {  
	
	protected static Map<String, StringBuffer> cache = new HashMap<String, StringBuffer>();
    
    private static Pattern regex = Pattern.compile("[\u4e00-\u9fa5]");
    
	public static String encode(String str) {
		Matcher m = regex.matcher(str);
        StringBuffer b = new StringBuffer();
        try {
            while (m.find()) {
            	String key = m.group();
            	m.appendReplacement(b, URLEncoder.encode(key, "UTF-8"));
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }
	    m.appendTail(b);
	    return b.toString();
	}
    
    /** 
     * 发送GET请求 
     *  
     * @param urlString 
     *            URL地址 
     * @return 响应对象 
     * @throws IOException 
     */  
    public <T> T sendGet(String urlString) throws IOException {  
        return this.send(urlString, "GET", null, null);  
    }  
   
    /** 
     * 发送GET请求 
     *  
     * @param urlString 
     *            URL地址 
     * @param params 
     *            参数集合 
     * @return 响应对象 
     * @throws IOException 
     */  
    public <T> T sendGet(String urlString, Map<String, String> params)  
            throws IOException {  
        return this.send(urlString, "GET", params, null);  
    }  
   
    /** 
     * 发送GET请求 
     *  
     * @param urlString 
     *            URL地址 
     * @param params 
     *            参数集合 
     * @param propertys 
     *            请求属性 
     * @return 响应对象 
     * @throws IOException 
     */  
    public <T> T sendGet(String urlString, Map<String, String> params,  
            Map<String, String> propertys) throws IOException {  
        return this.send(urlString, "GET", params, propertys);  
    }  
   
    /** 
     * 发送POST请求 
     *  
     * @param urlString 
     *            URL地址 
     * @return 响应对象 
     * @throws IOException 
     */  
    public <T> T sendPost(String urlString) throws IOException {  
        return this.send(urlString, "POST", null, null);  
    }  
   
    /** 
     * 发送POST请求 
     *  
     * @param urlString 
     *            URL地址 
     * @param params 
     *            参数集合 
     * @return 响应对象 
     * @throws IOException 
     */  
    public <T> T sendPost(String urlString, Map<String, String> params)  
            throws IOException {  
        return this.send(urlString, "POST", params, null);  
    }  
   
    /** 
     * 发送POST请求 
     *  
     * @param urlString 
     *            URL地址 
     * @param params 
     *            参数集合 
     * @param propertys 
     *            请求属性 
     * @return 响应对象 
     * @throws IOException 
     */  
    public <T> T sendPost(String urlString, Map<String, String> params,  
            Map<String, String> propertys) throws IOException {  
        return this.send(urlString, "POST", params, propertys);
    }  

    /** 
     * 发送HTTP请求 
     *  
     * @param urlString 
     * @return 响映对象 
     * @throws IOException 
     */  
    private <T> T send(String urlString, String method,  
            Map<String, String> parameters, Map<String, String> propertys)  
            throws IOException {  
        HttpURLConnection urlConnection = null;
        StringBuffer param = new StringBuffer();
		try {
			if (method.equalsIgnoreCase("GET") && parameters != null) {
				int i = 0;
				for (String key : parameters.keySet()) {
					if (i == 0)
						param.append("?");
					else
						param.append("&");
					String value = parameters.get(key);
					if (!Common.isCheckNull(value)) {
						param.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(value, "UTF-8"));
					} else {
						param.append(URLEncoder.encode(key, "UTF-8")).append("=");
					}
					i++;
				}
				urlString += param;
			}
			if (method.equalsIgnoreCase("POST") && parameters != null) {
				for (String key : parameters.keySet()) {
					param.append("&");
					String value = parameters.get(key);
					if (!Common.isCheckNull(value)) {
						param.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(value, "UTF-8"));
					} else {
						param.append(URLEncoder.encode(key, "UTF-8")).append("=");
					}
				}
			}
			//将中文转码
			urlString = encode(urlString);
			URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			if (method.equalsIgnoreCase("GET")) {
				urlConnection.setRequestMethod("GET");// 提交模式
			} else {
				urlConnection.setRequestMethod("POST");// 提交模式
			}
			urlConnection.setRequestMethod(method);
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);
			// 设置获取连接超时时间
			//urlConnection.setConnectTimeout(10000);
			// 设置读取返回数据超时时间
			//urlConnection.setReadTimeout(10000);
			if (propertys != null) {
				for (String key : propertys.keySet()) {
					urlConnection.addRequestProperty(key, propertys.get(key));
				}
			}
			//服务缓存 start
			StringBuffer content = null;
	        String cacheKey = null;
	        boolean isCache = false;
			if (urlString.indexOf("/arcgis/rest/services/") != -1 && false) {
				isCache = true;
				cacheKey = urlString;
				if (param.length() > 0) {
					cacheKey += "?" + param.substring(1);
				}
				if (cache.containsKey(cacheKey)) {
					content = cache.get(cacheKey);
				}
			}
			//服务缓存 end
        	String ecod = "UTF-8";
        	if (content == null) {
    			if (method.equalsIgnoreCase("POST") && parameters != null) {
    				OutputStream opt = urlConnection.getOutputStream();
    				opt.write(encode(param.toString()).getBytes());
    				opt.flush();
    				opt.close();
    			}
                InputStream in = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, ecod));
                content = new StringBuffer();  
                String line = bufferedReader.readLine();  
                while (line != null) {
                    content.append(line).append("\r\n");  
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                if (isCache && cacheKey != null && content.indexOf("\"error\"")==-1) {
                	cache.put(cacheKey, content);
                }
        	}
        	HttpRespons httpResponser = getHttpRespons();
            httpResponser.urlString = urlString;  
            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();  
            httpResponser.file = urlConnection.getURL().getFile();  
            httpResponser.host = urlConnection.getURL().getHost();  
            httpResponser.path = urlConnection.getURL().getPath();  
            httpResponser.port = urlConnection.getURL().getPort();  
            httpResponser.protocol = urlConnection.getURL().getProtocol();  
            httpResponser.query = urlConnection.getURL().getQuery();  
            httpResponser.ref = urlConnection.getURL().getRef();
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();
            
            httpResponser.content = content;
            httpResponser.contentEncoding = ecod;
            httpResponser.code = urlConnection.getResponseCode();  
            httpResponser.message = urlConnection.getResponseMessage();  
            httpResponser.contentType = urlConnection.getContentType();  
            httpResponser.method = urlConnection.getRequestMethod();  
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();  
            httpResponser.readTimeout = urlConnection.getReadTimeout();  
            return (T)httpResponser;  
		} catch (IOException e) {  
			System.out.println("url: "+urlConnection.getURL());
            throw e; 
        } finally {
            if (urlConnection != null) {
            	urlConnection.disconnect();
            }
        }
    }
    
    public HttpRespons getHttpRespons() {
    	return new HttpRespons();
    }
}  