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
 * @CreateDate: 2020/12/7 22:50
 */
public class DemoExists {

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

    @Test
    public void watcherExists() throws KeeperException, InterruptedException {
        //arg1 节点路径
        //arg2  使用连接对象中的watcher对象进行监听
        zooKeeper.exists("/watcher1", true);
        Thread.sleep(500000);
        System.out.println("结束");
    }

    @Test
    public void watcherExists2() throws KeeperException, InterruptedException {
        //arg1 节点路径
        //arg2  使用自定义的watcher对象进行监听  但watcher是一次性的
        zooKeeper.exists("/watcher1", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
            }
        });
        Thread.sleep(500000);
        System.out.println("结束");
    }

    @Test
    public void watcherExists3() throws KeeperException, InterruptedException {
        //arg1 节点路径
        //arg2  使用自定义的watcher对象进行监听  使watcher可以多次使用
        Watcher watcher = new Watcher(){  //匿名内部类
            @Override
            public void process(WatchedEvent event) {
                System.out.println("path=" + event.getPath());
                System.out.println("eventType=" + event.getType());
                try {
                    //watcher可以多次使用
                    zooKeeper.exists("/watcher1", this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        zooKeeper.exists("/watcher1", watcher);
        Thread.sleep(500000);
        System.out.println("结束");
    }

    @After
    public void after() throws InterruptedException {
        zooKeeper.close();
    }
}
