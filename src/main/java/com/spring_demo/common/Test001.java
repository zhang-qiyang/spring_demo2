package com.spring_demo.common;

import com.spring_demo.service.UserService;

/**
 * @author zhangqiyang
 * @date 2019/3/24 - 13:13
 */
public class Test001 {
    public static void main(String[] args) throws Exception {
        ClassPanthXmlApplicationContextExt app = new ClassPanthXmlApplicationContextExt("spring.xml");
        UserService userService = (UserService) app.getBeanExt("userService");
        userService.add();
    }
}
