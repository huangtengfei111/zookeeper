package com.example.demo.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Description: java类作用描述
 * @Author: huangtf
 * @CreateDate: 2020/12/7 0:09
 */
public class Demo01 implements Watcher {

    static ZooKeeper zooKeeper;

    //计数器
    static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        //事件类型
        if (event.getType() == Event.EventType.None) {
            if (event.getState() == Event.KeeperState.SyncConnected) {
                System.out.println("连接成功");
                //countDownLatch.countDown();
            } else if (event.getState() == Event.KeeperState.Disconnected) {
                System.out.println("断开连接");
            } else if (event.getState() == Event.KeeperState.Expired) {
                System.out.println("连接超时");
                try {
                    zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Demo01());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event.getState() == Event.KeeperState.AuthFailed) {
                System.out.println("认证失败");
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Demo01());
        //阻塞线程，等待连接创建
        // countDownLatch.wait();
        Thread.sleep(5000);
        System.out.println(zooKeeper.getSessionId());
        Thread.sleep(50000);
        zooKeeper.close();
        System.out.println("结束");
    }


}
