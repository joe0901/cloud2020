package com.milo.springcloud.controller;

import com.milo.springcloud.entities.CommonResult;
import com.milo.springcloud.entities.Payment;
import com.milo.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping("/payment/create")
    public  CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info(result+"");

        if(result > 0) return new CommonResult(200, "创建成功", result);
        return new CommonResult(400, "创建失败", null);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) return new CommonResult(200, "查找成功", payment);
        return new CommonResult(400, "查找失败,id为" + id, null);
    }
}
