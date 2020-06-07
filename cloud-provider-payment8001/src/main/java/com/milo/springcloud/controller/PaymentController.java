package com.milo.springcloud.controller;

import com.milo.springcloud.entities.CommonResult;
import com.milo.springcloud.entities.Payment;
import com.milo.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sun.corba.se.impl.legacy.connection.USLPort;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
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

    public static void main(String[] args) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\admin\\Desktop\\1.txt")));
        List<String> list = new ArrayList<>();
        list.add("https://m.ybjk.com/n_8cd11f");
        list.add("https://x.ybjk.com/2hab4c");
        list.add("https://m.ybjk.com/n_86894f");
        list.add("https://m.ybjk.com/n_db5494");
        list.add("https://x.ybjk.com/hd5c28");
        list.add("https://m.ybjk.com/n_a33918");
        list.add("https://x.ybjk.com/a9983f");
        list.add("https://x.ybjk.com/4hcad5");
        list.add("https://x.ybjk.com/48a28f");
        list.add("https://x.ybjk.com/2499fb");
        list.add("https://x.ybjk.com/e89a4d");
        list.add("https://x.ybjk.com/b8h8cf");
        list.add("https://x.ybjk.com/228ed3");
        list.add("https://x.ybjk.com/aef33h");
        list.add("https://x.ybjk.com/h42ghd");
        list.add("https://m.ybjk.com/n_f20efb");
        list.add("https://m.ybjk.com/n_006cb1");
        list.add("https://x.ybjk.com/fd76b6");
        list.add("https://m.ybjk.com/n_8d0d43");
        list.add("https://x.ybjk.com/7eb538");
        list.add("https://m.ybjk.com/n_b874e2");
        list.add("https://m.ybjk.com/n_404dd8");
        list.add("https://m.ybjk.com/n_f6c9a0");
        list.add("https://m.ybjk.com/n_fb3a06");
        list.add("https://x.ybjk.com/g9aa5e");
        list.add("https://m.ybjk.com/n_96f6ad");
        list.add("https://m.ybjk.com/n_c6782b");
        list.add("https://x.ybjk.com/db49f7");
        list.add("https://m.ybjk.com/n_ed295d");
        list.add("https://m.ybjk.com/n_fcf2b3");
        list.add("https://m.ybjk.com/n_ebbb25");
        list.add("https://m.ybjk.com/n_10d3c5");
        list.add("https://m.ybjk.com/n_3aaebf");
        list.add("https://m.ybjk.com/n_d8cd1f");
        list.add("https://m.ybjk.com/n_0277d9");
        list.add("https://m.ybjk.com/n_41de10");
        list.add("https://m.ybjk.com/n_846554");
        list.add("https://m.ybjk.com/n_738993");
        list.add("https://m.ybjk.com/n_36d208");
        list.add("https://m.ybjk.com/n_70f5a7");
        list.add("https://m.ybjk.com/n_10ec00");
        list.add("https://x.ybjk.com/626ffa");
        list.add("https://x.ybjk.com/9539c5");
        list.add("https://x.ybjk.com/c55b3a");
        list.add("https://m.ybjk.com/n_0f892a");
        list.add("https://m.ybjk.com/n_e7883c");
        list.add("https://m.ybjk.com/n_87761d");
        list.add("https://x.ybjk.com/af2f65");
        list.add("https://x.ybjk.com/5ccgdh");
        list.add("https://x.ybjk.com/c3ae82");
        list.add("https://m.ybjk.com/n_969c0b");
        list.add("https://m.ybjk.com/n_a18c60");
        list.add("https://x.ybJk.com/heh4d5");
        list.add("https://m.ybjk.com/n_5c9316");
        list.add("https://x.ybjk.com/32f5e7");
        list.add("https://x.ybjk.com/32ge22");
        list.add("https://m.ybjk.com/n_2fa8b3");
        list.add("https://x.ybjk.com/92h7c9");
        list.add("https://x.ybjk.com/b4ab5d");
        list.add("https://m.ybjk.com/n_447991");
        list.add("https://x.ybjk.com/gddd47");
        list.add("https://m.ybjk.com/n_e0893f");
        list.add("https://x.ybjk.com/95abb5");
        list.add("https://x.ybjk.com/gf4b7f");
        list.add("https://x.ybjk.com/da96g1");

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            for (String url : list) {
                // 创建Get请求
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                // 由客户端执行(发送)Get请求
                response = httpClient.execute(httpGet);
                // 从响应模型中获取响应实体
                HttpEntity responseEntity = response.getEntity();
                //获取网页内容
                String s = EntityUtils.toString(responseEntity);
                int startIndex = s.indexOf("<title>") + 7;
                int endIndex = s.indexOf("</title>");
                s = s.substring(startIndex, endIndex);
                s = s + "::" + url.substring(url.lastIndexOf("/") + 1);
//                System.out.println(s);
                bw.write(s);
                bw.newLine();
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                System.out.println("响应码为:" + statusCode);
                System.out.println("&&&&:" + url);

                if (statusCode == 404) {
                    System.out.println("****:" + url);
                    bw.write(url);
                    bw.newLine();
                }
            }
        } finally {
            bw.flush();
            try {
/*                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }*/
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
