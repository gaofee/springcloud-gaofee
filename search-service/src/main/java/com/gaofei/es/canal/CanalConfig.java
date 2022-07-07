package com.gaofei.es.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.gaofei.es.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.context.CanalContext;
import top.javatool.canal.client.handler.EntryHandler;

import java.util.Map;

/**
 * 实现该接口EntryHandler,泛型是监听表的实体类
 * 监听者user_test表的变化
 */
@Component
@CanalTable("book")
public class CanalConfig implements EntryHandler<Book> {
 
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
 
    //user_test有新增就会调用此方法，获取到新增的数据
    @Override
    public void insert(Book userTest) {
//        stringRedisTemplate.opsForValue().set("userTest", userTest.getName());
        System.out.println("新增了:"+userTest);
        System.out.println(CanalContext.getModel());
        EntryHandler.super.insert(userTest);
    }
 
    //修改
    @Override
    public void update(Book before, Book after) {
        System.out.println("更新前:"+before);
        System.out.println("更新后:"+after);
        EntryHandler.super.update(before, after);
    }
 
    //删除
    @Override
    public void delete(Book userTest) {
        System.out.println("删除了:"+userTest);
        EntryHandler.super.delete(userTest);
    }
}
