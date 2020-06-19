package com.atguigu.exception;




public class MyExcaption extends Exception {

    private  int code;

    public MyExcaption(int code, String massage){
        super(massage);
        this.code =code;
    }


}
