package com.common.util;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import net.sf.json.JSONArray;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 * WebService统一调用类
 * 作者：韩勤
 * 统一传入参数对象Param数组
 * 统一返回字符串
 */
public class WebService{
	//endpoint
	private String endpoint;
	//命名空间
	private String namespace = "http://agcom.augurit.com";
	
	public WebService(String endpoint, String namespace) {
		this.endpoint = endpoint;
		this.namespace = namespace;
	}
	
	public static class WebParam {
		private String name;
		private String type;
		private Object value;
		
		public WebParam(String name, String value) {
			this.name = name;
			this.type = "String";
			this.value = value;
		}
		
		public WebParam(String name, String value, String type) {
			this.name = name;
			this.type = type;
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
	}
	
	/**
	 * 取得WebService调用的结果
	 * @param args
	 * @return
	 */
	public String getCallResult(String webMethod, WebParam... args) throws Exception {
		// 创建 Service实例
		Service service = new Service();
		// 通过Service实例创建Call的实例
		Call call;
		try {
			call = (Call) service.createCall();
			call.setUseSOAPAction(true);
			// 将Web Service的服务路径加入到call实例之中.
			call.setTargetEndpointAddress(new java.net.URL(endpoint));// 为Call设置服务的位置
			// 调用Web Service的方法
			call.setOperationName(new QName(namespace, webMethod));
			//设置方法的第一个参数，其中"strSSID"为参数名，后跟参数类型，是IN输入参数还是OUT输出参数
			Object[] params = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				WebParam mapping = args[i];
				String type = JSPUtil.checkNull(mapping.getType()).toLowerCase();
				QName type_XSD = null;
				if ("string".equals(type)) {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_STRING;
				} else if ("boolean".equals(type)) {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_BOOLEAN;
				} else if ("double".equals(type)) {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_DOUBLE;
				} else if ("float".equals(type)) {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_FLOAT;
				} else if ("int".equals(type)) {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_INT;
				} else if ("integer".equals(type)) {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_INTEGER;
				} else if ("long".equals(type)) {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_LONG;
				} else if ("short".equals(type)) {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_SHORT;
				} else if ("byte".equals(type)) {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_BYTE;
				} else {
					type_XSD = org.apache.axis.encoding.XMLType.XSD_STRING;
				}
				call.addParameter(new QName(namespace,mapping.getName()), type_XSD, javax.xml.rpc.ParameterMode.IN);
				params[i] = mapping.getValue();
			}
			//设置方法的返回值类型，统一为字符串
			call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_STRING);
			//设置SOAPAction；这里namespace与method之前一定要加“/”
			call.setSOAPActionURI(namespace + webMethod);
			// 调用Web Service,传入参数
			String str = (String) call.invoke(params);
			
			return str;
		} catch (Exception e) {
			throw e;
		}
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}