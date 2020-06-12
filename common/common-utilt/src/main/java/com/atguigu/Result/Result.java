package com.atguigu.Result;

import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {

    private  Result(){}

    private String message;
    private Integer code;
    private Map<String,Object> data =  new HashMap<>();
    private Boolean success;

    public static Result OK(){
        Result result = new Result();
        result.setCode(ResultCode.OK);
        result.setSuccess(true);
       result.setMessage("成功");
       return result;
    }

    public static Result ERROR(){
        Result result = new Result();
        result.setCode(ResultCode.ERROR);
        result.setSuccess(false);
        result.setMessage("失败");
        return result;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result code(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result data(String k,Object o) {
        this.getData().put(k,o);
        return this;
    }

    public Result data(Map data) {
        this.setData(data);
        return this;
    }

    public Result massage(String massage) {
        this.setMessage(massage);
        return this;
    }


}
