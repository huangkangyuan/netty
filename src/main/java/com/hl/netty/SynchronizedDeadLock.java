package com.hl.netty;

import java.nio.ByteBuffer;

public class SynchronizedDeadLock {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);
        //定义2个锁对象
        final Object o1 = new Object();
        final Object o2 = new Object();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (o1) {//获取o1的锁
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {//获取o2的锁

                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (o2) {//获取o2的锁
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {//获取o1的锁

                    }
                }
            }
        });
        //启动
        t1.start();
        t2.start();
    }
}
