package com.atguigu.eduservice.entity.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;


@ApiModel(value = "课程发布信息")
@Data
public class CouresPublishVo {

    private String id;
    private String title;
    private String price;
    private String lessonNum;
    private String oneSubject;
    private String twoSubject;
    private String description;
    private String teacherName;
    private  String cover;

}
