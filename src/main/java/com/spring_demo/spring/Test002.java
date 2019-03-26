package com.spring_demo.spring;

import com.spring_demo.service.UserService;

/**
 * @author zhangqiyang
 * @date 2019/3/24 - 17:43
 */
public class Test002 {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContextzq app = new ClassPathXmlApplicationContextzq("com.spring_demo.service");
        UserService userService = (UserService) app.getBean("userServiceImpl");
        userService.add();


    }
}
