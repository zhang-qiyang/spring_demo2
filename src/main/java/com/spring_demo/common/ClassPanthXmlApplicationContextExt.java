package com.spring_demo.common;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 通过解析xml文件的方式进行初始化
 * @author zhangqiyang
 * @date 2019/3/24 - 12:40
 */
public class ClassPanthXmlApplicationContextExt {
    private String xmlFile;


    public ClassPanthXmlApplicationContextExt(String xmlFile){
        this.xmlFile=xmlFile;
    }
    public Object getBeanExt(String beanId) throws Exception {
        //1、解析xml文件
        List<Element> elements = getElements();
        //2、通过传入的beanid查找xml文件中与之对应的class地址
        String classByBeanId = findClassByBeanId(beanId, elements);
        //3、通过反射机制初始化对象
        Object o = newInstance(classByBeanId);
        return o;
    }



    /**
     * 获取xml文件的输入流
     * @param xmlFile xml文件名称
     * @return
     */
    public InputStream getLoad(String xmlFile){
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(xmlFile);
        return resourceAsStream;
    }

    /**
     * 获取子节点的集合
     * @return
     * @throws DocumentException
     */
    public List<Element> getElements() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(getLoad(xmlFile));
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        return elements;
    }

    /**
     * 通过beanid获取class地址
     * @param beanId
     * @param elements
     * @return
     * @throws Exception
     */
    public String findClassByBeanId(String beanId,List<Element> elements) throws Exception {
        for (Element element : elements) {
            String id = element.attributeValue("id");
            if(StringUtils.isEmpty(id)){
                throw new Exception("xml文件中未配置ID属性");
            }
            if(beanId.equals(id)){
                String aClass = element.attributeValue("class");
                if(StringUtils.isEmpty(aClass)){
                    throw new Exception("class属性未配置value");
                }
                return aClass;
            }
        }
        return null;
    }

    /**
     * 利用反射机制初始化对象
     * @param classPath
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object newInstance(String classPath) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName(classPath);
        Object o = aClass.newInstance();
        return o;
    }


}
