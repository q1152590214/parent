package com.atguigu.eduorder.Utils;

import com.alibaba.nacos.client.naming.utils.RandomUtils;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class OrderOnUtli {

    public static  String  GetOrderOn(){

        String result  = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String data =  simpleDateFormat.format(new Date());
        Random  random = new Random();
        for (int i = 0;i<3;i++){
           result +=random.nextInt(1000);
        }
        result = result+data;
        return result;
    }
}
