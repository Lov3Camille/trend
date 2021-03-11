package org.lov3camille.trend;

import static org.junit.Assert.assertTrue;

import org.apache.hadoop.hdfs.web.JsonUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public static void main(String[] args) {
        ticket ti = new ticket();
        new Thread(() -> {
            for (int i = 0; i < 60; i ++) {
                ti.sale();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 60; i ++) {
                ti.sale();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 60; i ++) {
                ti.sale();
            }
        }, "C").start();
    }
}
class ticket {
    private int num = 50;
    private int i = 0;

    Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();

        try {
            if (num > 0) {
                num--;
                i++;
                System.out.println(Thread.currentThread().getName() + i + "tickets sold" + num + " ;tickets left");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
