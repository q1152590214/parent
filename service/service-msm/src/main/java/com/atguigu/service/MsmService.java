package com.atguigu.service;

import java.util.HashMap;

public interface MsmService {
    boolean send(HashMap<String, Object> stringObjectHashMap, String phone);
}
