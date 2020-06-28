package com.atguigu.exception;


import com.atguigu.Result.Result;
import lombok.Getter;

@Getter
public class MyExcaption extends RuntimeException {

    private  int code;
    private  String mas;

    public MyExcaption(int code, String massage){
        super(massage);
        this.code =code;
        this.mas=massage;
    }


    private MyExcaption(String massage){
        super(massage);
        this.mas=massage;
    }


    public static MyExcaption from(Result result){
        MyExcaption myExcaption = new MyExcaption(result.getCode(),result.getMessage());
        return myExcaption;

    }


    public static MyExcaption from(String massage){
        MyExcaption myExcaption = new MyExcaption(massage);
        return myExcaption;

    }


}
