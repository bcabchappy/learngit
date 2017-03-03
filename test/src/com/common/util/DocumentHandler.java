package com.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 应用Freemaker生成doc文件
 * @author 梁华新 
 * @Date   2015-6-16
 *
 */
public class DocumentHandler {
	
	//配置实例：只需要一个实例（单例模式）
	private Configuration configuration = null;
	private String webPath;
	/**
	 * 
	 * @param webPath 应用根目录
	 */
	public DocumentHandler(String webPath) {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		this.webPath = webPath;
	}
	/**
	 * 
	 * @param dataMap  装载数据
	 * @param templateFileName  ftl文件名，如fourline.ftl
	 * @param storagePath  生成文档存放路径 比如/upload/控制线检测/2015/07/201570722123.doc
	 */
	@SuppressWarnings("unchecked")
	public void createDoc(Map dataMap,String templateFileName,String storagePath) {
		/**
		 * 模板加载方式
		 * 1、setClassForTemplateLoading 
		 * 2、setServletContextForTemplateLoading  web上下文
		 * 3、setDirectoryForTemplateLoading
		 */
		try {
			configuration.setDirectoryForTemplateLoading(new File(webPath.concat("template")));
		} catch (IOException e2) {			
			e2.printStackTrace();
		}
		Template t = null;
		try {
			// fourline.ftl为要装载的模板
			t = configuration.getTemplate(templateFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出文档路径及名称
		File outFile = new File(webPath.concat(storagePath));
		//判断目标文件所在的目录是否存在
		if(!outFile.getParentFile().exists()){
			outFile.getParentFile().mkdirs();
		}
		if(!outFile.exists()){
			try {
				outFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Writer out = null;
		try {
			try {
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			t.process(dataMap, out);
			out.flush();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
