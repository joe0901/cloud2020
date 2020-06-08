package com.milo.springcloud.controller;

import com.milo.springcloud.entities.CommonResult;
import com.milo.springcloud.entities.Payment;
import com.milo.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String SERVER_PORT;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        //log.info(result+"");

        if (result > 0) return new CommonResult(200, "创建成功,PORT:" + SERVER_PORT, result);
        return new CommonResult(400, "创建失败", null);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) return new CommonResult(200, "查找成功,PORT:" + SERVER_PORT, payment);
        return new CommonResult(400, "查找失败,id为" + id, null);
    }

    @GetMapping("/payment/getInstance")
    public Object getInstance() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            // log.info("****" + element);
        }
        //========================
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            //log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort());
        }
        return this.discoveryClient;
    }




    @GetMapping("/payment/lb")
    public String lb() {
        return SERVER_PORT;
    }

    //模拟调用超时
    @GetMapping("/payment/feign/timeout")
    public String timeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return SERVER_PORT;
    }

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        return "ok";
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
    })
    //@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
    //方法调用超时数为1.5秒,超过则调用fallback方法
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "paymentInfo_TimeOut,id" + id + "\t" + "笑脸";
    }

    public String paymentInfo_TimeoutHandler(Integer id) {
        return "8001系统繁忙或者运行报错请稍后再试,id" + id + "\t" + "哭哭";
    }

    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable Integer id){
        return paymentService.paymentCircuitBreaker(id);
    }





}
