package com.fwxgx.ae.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class URLOutbox {

    private static final Logger log = Logger.getGlobal();
    private static URLOutbox monitor = null;

    private static URLOutbox getInstance() {
        if (monitor == null) {
            synchronized (URLOutbox.class) {
                if (monitor == null) {
                    monitor = new URLOutbox();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URLOutbox.monitor.deliver();
                        }
                    });
                    thread.run();
                }
            }
        }
        return monitor;
    }

    public static void addUrl(String url) {
        getInstance().queue.push(url);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////


    private BlockingDeque<String> queue = new LinkedBlockingDeque<String>();
    private URLOutbox() { }
    private void deliver() {
        while (true) {
            try {
                String url = this.queue.take();
                this.sendURL(url);    //todo
            } catch (InterruptedException e) {
                log.log(Level.WARNING, "url递交发送器异常");
            } finally {

            }
        }
    }

    private void sendURL(String url) {
        HttpURLConnection conn = null;
        BufferedReader in = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            System.out.println("发送url  " + url);
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            log.log(Level.WARNING, "请求发送异常  " + url);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                log.log(Level.WARNING, "请求连接断开异常");
            }
        }
    }

}
