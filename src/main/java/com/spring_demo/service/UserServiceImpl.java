package com.spring_demo.service;

import com.spring_demo.anno.ExtResource;
import com.spring_demo.anno.ExtService;

/**
 * @author zhangqiyang
 * @date 2019/3/24 - 13:12
 */
@ExtService
public class UserServiceImpl implements UserService {
    @ExtResource
   private OrderService orderServiceImpl;

    @Override
    public void add() {
        //System.out.println("利用反射机制初始化");
        orderServiceImpl.getHost();

    }

}
