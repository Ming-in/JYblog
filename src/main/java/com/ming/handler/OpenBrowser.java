package com.ming.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author 邹明
 */
@Component
public class OpenBrowser implements CommandLineRunner {
    @Value("${open.browser.url}")
    private String url = "http://localhost:8080";
    @Value("${open.browser.openUrl}")
    private boolean isOpen;

    @Override
    public void run(String... args) throws Exception {
        if (!isOpen) {
            return;
        }
        System.out.println("开始自动加载指定的页面");
        try {
            //可以指定自己的路径
            Runtime.getRuntime().exec("cmd   /c   start   " + url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
