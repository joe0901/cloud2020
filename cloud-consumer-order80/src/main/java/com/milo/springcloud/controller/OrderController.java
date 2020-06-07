package com.milo.springcloud.controller;

import com.milo.springcloud.entities.CommonResult;
import com.milo.springcloud.entities.Payment;
import com.milo.springcloud.lb.LoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    //这里必须是http全路径
    //public final String PAYMENT_URL = "http://localhost:8001/";
    public final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE/";
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private LoadBalance loadBalance;
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    //浏览器访问,只能是get,但是restTemplate使用的是post发送
    @GetMapping("/consumer/payment/create")
    public  CommonResult create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create/",payment,CommonResult.class);
    }
    //restTemplate.getForEntity()
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public  CommonResult getForEntity(@PathVariable("id") Long id){

        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        return new CommonResult(404, "entity 查找失败");
    }

    @GetMapping("/consumer/payment/lb")
    public String lb() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance instance = loadBalance.getInstance(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb", String.class);
    }
}
