package com.atguigu.exceptionhandler;


import com.atguigu.Result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobaExceptionHandler {



    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.ERROR().massage("执行了全局的异常处理");
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
