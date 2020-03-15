package com.csp.github.zuul.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈少平
 * @date 2020-03-14 22:49
 */
public class TestHystrix extends HystrixCommand<String> {
    private final String name;
    protected TestHystrix(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;

    }

    @Override
    protected String getFallback() {
        System.out.println("qweqwe");
        return "123";
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("qweqe");
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        TestHystrix hystrix = new TestHystrix("sync-hystrix");
        String result = hystrix.execute();
        System.out.println("result=" + result);
        hystrix = new TestHystrix("async-hystrix");
        Future<String> future = hystrix.queue();
        result = future.get(1000, TimeUnit.MILLISECONDS);
        System.out.println("result=" + result);
        System.out.println("mainThread=" + Thread.currentThread().getName());
    }
}
