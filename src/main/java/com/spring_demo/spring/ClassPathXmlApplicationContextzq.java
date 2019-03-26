package com.spring_demo.spring;

import com.spring_demo.anno.ExtResource;
import com.spring_demo.anno.ExtService;
import com.spring_demo.utile.ClassUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过注解的方式进行初始化
 * @author zhangqiyang
 * @date 2019/3/24 - 17:09
 */
public class ClassPathXmlApplicationContextzq {
    private String packagePath;
    private ConcurrentHashMap<String,Object> beans;

    public ClassPathXmlApplicationContextzq(String packagePath) throws Exception {

        this.packagePath=packagePath;
        initBean();
        initEntry();
    }

    public void initBean() throws InstantiationException, IllegalAccessException {
        //初始化map集合
        beans =new ConcurrentHashMap<>();
        //1、扫描包，获取包下面的类
        List<Class<?>> classes = ClassUtil.getClasses(packagePath);
        //2、判断类上面是否有bean注解
        for (Class<?> classInfo : classes) {

            ExtService annotation = classInfo.getAnnotation(ExtService.class);
            if(annotation!=null){
                //获取当前类的名称
                String simpleName = classInfo.getSimpleName();
                String benaId = toLowerCaseFirstOne(simpleName);
                //利用反射机制，通过类的地址初始化对象
                Object o = newInstance(classInfo);
                beans.put(benaId,o);
            }
        }
    }

    /**
     * 获取bean对象
     * @param beanId
     * @return
     * @throws Exception
     */
    public Object getBean(String beanId) throws Exception {
        if(beanId.isEmpty()){
            throw new Exception("beanId不能为空");
        }
        Object classInfo = beans.get(beanId);
        if(classInfo==null){
            throw new Exception("class not found");
        }
        return classInfo;

    }

    /**
     * 首字母转小写
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))){
            return s;
        } else{
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0)))
                    .append(s.substring(1)).toString();
        }

    }

    /**
     * 利用反射创建对象
     * @param classInfo 类的路径
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object newInstance(Class<?> classInfo) throws IllegalAccessException, InstantiationException {
        return classInfo.newInstance();
    }

    /**
     * 给带有注解的属性赋值
     * @param object 当前类对象
     * @throws Exception
     */
    public void attriAssign(Object object) throws Exception {
        //1、利用反射机制 获取当前类下的所有属性
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        //if(declaredFields.length!=0){
            for (Field declaredField : declaredFields) {
                //查找带有注解的属性
                ExtResource annotation = declaredField.getAnnotation(ExtResource.class);
                if (annotation != null) {
                    //获取带有注解的属性名称
                    String name = declaredField.getName();
                    Object bean = getBean(name);
                    if (bean != null) {
                        declaredField.setAccessible(true);
                        declaredField.set(object, bean);
                    }
                }
            }
       // }
    }

    /**
     * 带有注解的属性进行初始化对象
     * @throws Exception
     */
    public void initEntry() throws Exception {
        for (Map.Entry<String,Object> m:beans.entrySet()){
            Object value = m.getValue();
            attriAssign(value);
        }
    }





}
