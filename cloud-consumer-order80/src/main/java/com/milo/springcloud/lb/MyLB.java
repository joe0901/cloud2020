package com.milo.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalance{

    private AtomicInteger atomicInteger = new AtomicInteger(0);


    @Override
    public ServiceInstance getInstance(List<ServiceInstance> instances) {
        int index = getAndSet() % instances.size();

        return instances.get(index);
    }

    public int getAndSet() {
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        } while (!atomicInteger.compareAndSet(current, next));
        System.out.println("第几次请求,次数:" + next);
        return next;
    }
}
