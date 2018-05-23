package com.gamepoch.profileui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PollingService extends Service {

    public static final String ACTION = "com.gamepoch.profileui.service.PollingService";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }


    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */
    int count = 0;
    class PollingThread extends Thread {
        @Override
        public void run() {
            count++;
            System.out.println("查询中...");
            if(count >= 5) {
                System.out.println("获得查询结果！");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }

}