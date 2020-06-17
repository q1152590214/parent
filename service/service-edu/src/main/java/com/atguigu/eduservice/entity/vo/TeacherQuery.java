package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {

    @ApiModelProperty(value = "讲师名称")
    private  String name;

    @ApiModelProperty("头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "开始时间")
    private String begin;

    @ApiModelProperty(value = "结束时间")
    private String edn;
}
