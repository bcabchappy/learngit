package com.lin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExtServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);
		response.setContentType("text/html;charset=utf-8");
		String type = request.getParameter("type");
		String result = "";
		if (type.equalsIgnoreCase("ext0")) {
			String id=request.getParameter("id");
			// 接收Client端传来的参数，交根据条件返回
			if (id!=null &&id.equalsIgnoreCase("1")) {
				result="{data:[{id:1,name:'东城区'},{id:2,name:'西城区'},{id:2,name:'海淀区'}]}";
			} else {
				result="{data:[{id:1,name:'杨浦区'},{id:2,name:'虹口区'},{id:2,name:'闸北区'}]}";
			}
		} else if (type.equalsIgnoreCase("ext1")) {
			result="{data:[{id:1,name:'北京'},{id:2,name:'上海'}]}";

		} else if (type.equalsIgnoreCase("ext2")) {
			result = "[" +
					"{ text: '根下节点一[user图标]', leaf: true, iconCls: 'nodeicon' }," +
					"{ text: '根下节点二', leaf: true }," +
					"{ text: '根下节点三', leaf: false, children: [" +
					"{ text: '节点三子节点一', leaf: true }," +
					"{ text: '节点三子节点二', leaf: false, expanded: true, children: [" +
					"{ text: '节点三子节点二节点一', leaf: true }," +
					"{ text: '节点三子节点二节点二', leaf: true }" +
					"]" +
					"" +
					"]" +
					"}" +
					"]";

		} else if (type.equalsIgnoreCase("ext3")) {
			result="[{id:1,name:'类别一'},{id:2,name:'类别二'},{id:2,name:'类别三'}]";
			String test="hello//world";
			test=test.replace("////","//");
			System.out.println("test:"+test);
		} else if (type.equalsIgnoreCase("ext4")) {

		} else {

		}
		
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type=request.getParameter("type");
		response.setContentType("text/html;charset=utf-8");
		String result = "";
		if (type.equalsIgnoreCase("p1")) {
			String id=request.getParameter("id");
			// 接收Client端传来的参数，交根据条件返回
			System.out.println("id:"+id);
			result="{id:1,name:'张三',brithday:2001-01-01,height:178,sex:'0',discribe:'张三是一个.net软件工程师<br />现在正在学习ExtJs。'}";
		} else if (type.equalsIgnoreCase("p2")) {
			result="{data:[{id:1,name:'北京'},{id:2,name:'上海'}]}";

		}else if(type.equalsIgnoreCase("p3")){
			
		}else if(type.equalsIgnoreCase("p4")){
			
		}else if(type.equalsIgnoreCase("p5")){
			
		}else{
			
		}
		
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
	}
}
