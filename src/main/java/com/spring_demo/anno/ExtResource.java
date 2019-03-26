package com.spring_demo.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangqiyang
 * @date 2019/3/24 - 13:57
 */
//自定义注解 从Spring容器获取bean
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtResource {
}
