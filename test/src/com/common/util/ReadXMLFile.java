package com.common.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.io.SAXReader;
import org.dom4j.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ReadXMLFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path="src/resources/HeaderControllerWidget.xml";
		ReadXMLFile readXMLfile=new ReadXMLFile();
		JSONObject obj=readXMLfile.readXMLFile(path);
		System.out.println(JSONObject.fromObject(obj));
	}
	
	private JSONObject readXMLFile(String path) {
		JSONObject result = new JSONObject();
		long lasting = System.currentTimeMillis();
		try {
			File f = new File(path);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
//			listNodes(root);
			List<Element> configurationSub=root.elements();
			for(int i=0;i<configurationSub.size();i++){
				Element node=configurationSub.get(i);
				if(node.getName().equalsIgnoreCase("lsk")){
					result.put("lsk", node.getTextTrim());
				}else if(node.getName().equalsIgnoreCase("actions")){
					List<Element> actions=node.elements();
					JSONObject actionObj;
					JSONArray actionArr=new JSONArray();
					for(int j=0;j<actions.size();j++){
						Element action=actions.get(j);
						actionObj=new JSONObject();
						//属性解析
						List<Attribute> attrlist = action.attributes();  
					    for(Attribute attribute : attrlist){  
					    	actionObj.put(attribute.getName(), attribute.getValue());
					    } 
						List<Element> layers=action.elements("layer");
						JSONArray layerArr=new JSONArray();
						JSONObject layerObj=new JSONObject();
						for(int k=0;k<layers.size();k++){
							Element layer=layers.get(k);
							attrlist=layer.attributes();
							for(Attribute attribute : attrlist){  
								layerObj.put(attribute.getName(), attribute.getValue());
						    }
							layerObj.put("url", layer.getTextTrim());
							layerArr.add(layerObj);
						}
						actionObj.put("layers", layerArr);
						actionArr.add(actionObj);
					}
					result.put("actions", actionArr);
				}
			}
			System.out.println("运行时间：" + (System.currentTimeMillis() - lasting)
					+ "毫秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 *  递归
	 * @param node
	 */
	public void listNodes(Element node){  
        System.out.println("当前节点的名称：" + node.getName());  
        //首先获取当前节点的所有属性节点  
        List<Attribute> list = node.attributes();  
        //遍历属性节点  
        for(Attribute attribute : list){  
            System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  
        }  
        //如果当前节点内容不为空，则输出  
        if(!(node.getTextTrim().equals(""))){
             System.out.println( node.getName() + "：" + node.getText());
        }
        //同时迭代当前节点下面的所有子节点  
        //使用递归  
        Iterator<Element> iterator = node.elementIterator();
        while(iterator.hasNext()){
            Element e = iterator.next();  
            listNodes(e);  
        }
    } 

}
