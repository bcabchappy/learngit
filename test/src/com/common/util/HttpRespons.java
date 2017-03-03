package com.common.util;

   
/** 
 * 响应对象 
 * 
 */  
public class HttpRespons {  
   
    String urlString;  
   
    int defaultPort;  
   
    String file;  
   
    String host;  
   
    String path;  
   
    int port;  
   
    String protocol;  
   
    String query;  
   
    String ref;  
   
    String userInfo;  
   
    String contentEncoding;  
   
    StringBuffer content;  
   
    String contentType;  
   
    int code;  
   
    String message;  
   
    String method;  
   
    int connectTimeout;  
   
    int readTimeout;  
   
    public String getContent() {
        return content.toString();  
    }

    public StringBuffer getContentBuffer() {  
        return content;  
    }
    
    public String getContentType() {  
        return contentType;  
    }  
   
    public int getCode() {  
        return code;  
    }  
   
    public String getMessage() {  
        return message;  
    }  
   
    public String getContentEncoding() {  
        return contentEncoding;  
    }  
   
    public String getMethod() {  
        return method;  
    }  
   
    public int getConnectTimeout() {  
        return connectTimeout;  
    }  
   
    public int getReadTimeout() {  
        return readTimeout;  
    }  
   
    public String getUrlString() {  
        return urlString;  
    }  
   
    public int getDefaultPort() {  
        return defaultPort;  
    }  
   
    public String getFile() {  
        return file;  
    }  
   
    public String getHost() {  
        return host;  
    }  
   
    public String getPath() {  
        return path;  
    }  
   
    public int getPort() {  
        return port;  
    }  
   
    public String getProtocol() {  
        return protocol;  
    }  
   
    public String getQuery() {  
        return query;  
    }  
   
    public String getRef() {  
        return ref;  
    }  
   
    public String getUserInfo() {  
        return userInfo;  
    }  
   
}  