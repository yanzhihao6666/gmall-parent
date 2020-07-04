package com.yan.gmaillogger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.yan.gmall.common.constant.GmaillConstant.KAFKA_EVENT;
import static com.yan.gmall.common.constant.GmaillConstant.KAFKA_STARTUP;

/**
 * @author : yanzhihao
 * @create: 2020-07-03 15:27
 * @description :
 */

@Controller
@Slf4j
public class LoggerController {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    /**
     *
     *  处理： https://logserver/log?logString={xxxxxxxxx} 的请求
     *           logserver : 服务器主机名称
     *           @RequestParam("logString") 接收 logString的请求
     *
     *  lombok: 使用注解 @Slf4j，会产生日志对象
     *          log.debug()
     *
     *  添加时间戳：
     *          转换为json对象后，添加时间戳
     *
     */
    @PostMapping("/log")
    @ResponseBody
    public String log(@RequestParam("logString") String logString){
        // 添加时间戳
        JSONObject jsonObject = JSON.parseObject(logString);
        jsonObject.put("ts", System.currentTimeMillis());

        // 根据type 发送到不同主题
        if ("event".equals(jsonObject.get("type"))) {
            kafkaTemplate.send(KAFKA_EVENT,jsonObject.toJSONString());
        }else if("startup".equals(jsonObject.get("type"))){
            kafkaTemplate.send(KAFKA_STARTUP,jsonObject.toJSONString());
        }

        // 使用 lombok
        log.info(logString);
        return "success";
    }
}
