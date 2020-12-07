package com.example.demo.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @Description: java类作用描述
 * @Author: huangtf
 * @CreateDate: 2020/12/7 23:32
 */
public class DemoGetData {

    ZooKeeper zooKeeper;

    @Before
    public void before() throws IOException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Demo01(){
            @Override
            public void process(WatchedEvent event) {
                System.out.println("连接对象的参数");
                if (event.getState() == Event.KeeperState.SyncConnected) {

                }
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
            }
        });
    }

    @After
    public void after() throws InterruptedException {
        zooKeeper.close();
    }

    @Test
    public void getData() throws KeeperException, InterruptedException {
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.getPath());
                System.out.println(event.getType());
                //如果节点已经删除了，就不进行注册监听器
                if(event.getType() != Event.EventType.NodeDeleted){
                    try {
                        zooKeeper.getData("/watcher2", this, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        zooKeeper.getData("/watcher2", watcher, null);
    }
}
