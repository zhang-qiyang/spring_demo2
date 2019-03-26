package com.spring_demo.service;


import com.spring_demo.anno.ExtService;

/**
 * @author zhangqiyang
 * @date 2019/3/24 - 18:03
 */
@ExtService
public class OrderServiceImpl implements OrderService {


    @Override
    public void getHost() {
        System.out.println("依赖注入");
    }
}
