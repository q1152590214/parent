package com;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class testEntity {


    @ExcelProperty(value = "学号" ,index = 0)
    private String son;

    @ExcelProperty(value = "名称",index = 1)
    private String name;
}
