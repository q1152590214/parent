package com.atguigu.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomUtil {

    private  static  final Random RANDOM = new Random();
    private static  final DecimalFormat FOURD_FORMAT = new DecimalFormat("0000");
    private  static  final  DecimalFormat SIX_FORMAT = new DecimalFormat("000000");
    public static String getFourBitRandom(){
        return FOURD_FORMAT.format( RANDOM.nextInt(10000));
    }

    public static    String getSixBirRandom(){
        return  SIX_FORMAT.format(RANDOM.nextInt(1000000));
    }


    public static ArrayList getRandom(List list,int n){

        Random random = new Random();
        HashMap<Object,Object> objectObjectHashMap = new HashMap<>();

        for ( int i = 0; i<list.size();i++){
            int number = random.nextInt(100)+1;
            objectObjectHashMap.put(number,i);
        }

        ArrayList arrayList = new ArrayList();
        Object[] objects = objectObjectHashMap.values().toArray();

        for (int  i = 0;i<n;i++){
            arrayList.add(list.get((Integer) objects[i]));
            System.out.println(list.get((Integer) objects[i]));
        }
        return arrayList;

    }
}




