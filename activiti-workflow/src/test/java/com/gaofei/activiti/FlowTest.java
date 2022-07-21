package com.gaofei.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FlowTest {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Test
    public void deploy(){
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("process/deviceReview.bpmn20.xml").deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }

    //启动流程
    @Test
    public void startProcess(){
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("deviceReviewId", "1");
        System.out.println(pi.getId());

    }
//查看流程实例
    @Test
    public void listProcess(){
        List<Task> list = taskService.createTaskQuery().list();
        list.forEach(task -> {
            System.out.println(task.getId());
            System.out.println(task.getName());
        });

    }
    //处理任务,到下个节点
    @Test
    public void doProcess(){
        List<Task> list = taskService.createTaskQuery().list();
        Map<String, Object> map = new HashMap<>();
        map.put("zerenrenApprove", false);
        map.put("zenrenrenApprove", false);
        list.forEach(task -> {
            if(task.getId().equals("62504")){
                //设置处理人
                task.setAssignee("lisi");
                taskService.complete(task.getId(),map);
            }
        });

    }

    //查看历史流程列表
    @Test
    public void listHiProcess(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().listPage(1, 6);
        System.out.println(list);
    }
}
