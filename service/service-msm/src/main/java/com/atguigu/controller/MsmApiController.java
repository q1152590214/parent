package com.atguigu.controller;

import com.atguigu.Result.Result;
import com.atguigu.service.MsmService;
import com.atguigu.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("edumsm/msm")
@CrossOrigin
@Api(description = "短信模块")
public class MsmApiController {


    @Resource
    private MsmService msmService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value = "短信发送验证码")
    @GetMapping("send/{phone}")
    public Result  sendMsm(@PathVariable("phone") String phone){

        //从redis中取，如果没有在进行阿里云发送
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return Result.OK();
        }


        // 生成随机数，传递至阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        HashMap<String,Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("code",code);
      boolean isSend =  msmService.send(stringObjectHashMap,phone);
      if(isSend){
          //向redis中放 k-v 并设置过期时间
          redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
          return Result.OK();
      }else {
          return  Result.ERROR();
      }

    }
}
