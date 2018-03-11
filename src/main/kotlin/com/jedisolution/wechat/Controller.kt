package com.jedisolution.wechat

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class CoreController {
    //验证是否来自微信服务器的消息
    @RequestMapping(value = "", method = arrayOf(RequestMethod.GET))
    fun checkSignature(@RequestParam(name = "signature", required = false) signature: String,
                       @RequestParam(name = "nonce", required = false) nonce: String,
                       @RequestParam(name = "timestamp", required = false) timestamp: String,
                       @RequestParam(name = "echostr", required = false) echostr: String): String {
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            log.info("接入成功")
            return echostr
        }
        log.error("接入失败")
        return ""
    }

    companion object {
        //增加日志
        private val log = LoggerFactory.getLogger(CoreController::class.java)
    }
}