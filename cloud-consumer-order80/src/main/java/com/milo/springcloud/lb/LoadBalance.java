package com.milo.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalance {
    ServiceInstance getInstance(List<ServiceInstance> instances);
}
