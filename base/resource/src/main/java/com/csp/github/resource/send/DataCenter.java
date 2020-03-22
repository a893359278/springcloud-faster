package com.csp.github.resource.send;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 陈少平
 * @date 2020-03-22 16:57
 */
public class DataCenter {
    private static final BlockingQueue<String> resourceList = new LinkedBlockingQueue<>();

    public static void addResource(String resource) {
        resourceList.add(resource);
    }

    public static String getResource() {
        try {
            return resourceList.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

}
