package com.atguigu.exceptionhandler;


import com.atguigu.Result.Result;
import com.atguigu.Result.ResultCode;
import com.atguigu.exception.MyExcaption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@ControllerAdvice
@Slf4j
public class GlobaExceptionHandler {



    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.ERROR().massage("执行了全局的异常处理");
    }


    @ExceptionHandler(MyExcaption.class)
    public Result error(MyExcaption e){
        log.error("异常信息{}",e);
        return Result.ERROR().code(e.getCode()).massage(e.getMas());
    }

    @ExceptionHandler(IOException.class)
    public Result error(IOException e){
        log.error("异常信息{}",e);
        throw MyExcaption.from(String.valueOf(ResultCode.ERROR));

    }
//    绑定全局数据
//    @ModelAttribute(name = "mp")
//    public Map<String,Object> mymap(){
//        Map<String,Object> map = new HashMap<>();
//        map.put("傻逼",1);
//        map.put("旺仔",2);
//        return map;
//    }
}
