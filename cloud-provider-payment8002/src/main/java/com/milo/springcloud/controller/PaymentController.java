package com.milo.springcloud.controller;

import com.milo.springcloud.entities.CommonResult;
import com.milo.springcloud.entities.Payment;
import com.milo.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String SERVER_PORT;
    @PostMapping("/payment/create")
    public  CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info(result+"");

        if(result > 0) return new CommonResult(200, "创建成功,PORT:"+SERVER_PORT, result);
        return new CommonResult(400, "创建失败", null);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) return new CommonResult(200, "查找成功", payment);
        return new CommonResult(400, "查找失败,id为" + id, null);
    }

    @GetMapping("/payment/lb")
    public String lb() {
        return SERVER_PORT;
    }

    //模拟调用超时
    @GetMapping("/payment/feign/timeout")
    public String timeout() {
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return SERVER_PORT;
    }
}
