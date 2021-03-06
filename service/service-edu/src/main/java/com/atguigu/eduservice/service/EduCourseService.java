package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.ConresInfoVo;
import com.atguigu.eduservice.entity.vo.CouresPublishVo;
import com.atguigu.exception.MyExcaption;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-22
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveConrseInfo(ConresInfoVo conresInfoVo) throws MyExcaption;

    ConresInfoVo getCourseInfo(String courseId);

    void updataCourseInfo(ConresInfoVo conresInfoVo);

    CouresPublishVo getPublishCourseInfo(String courseId);

    void pageQuery(Page<EduCourse> eduCoursePage, String courseName, String type);

    void deleteCourse(String courseId);

    Map<String,Object>  getFrontCourseList(Page<EduCourse> eduCoursePage, CourseFrontVo couresPublishVo);


    CourseWebVo getBaseCourseInfo(String courseId);
}
