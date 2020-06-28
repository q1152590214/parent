package com.atguigu.eduservice.service.impl;

import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.ConresInfoVo;
import com.atguigu.eduservice.entity.vo.CouresPublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.exception.MyExcaption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-22
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private EduCourseDescriptionService eduCourseDescriptionService;


    /**
     * 添加课程基本信息
     * @param conresInfoVo
     * @return
     */
    @Override
    public String saveConrseInfo(ConresInfoVo conresInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(conresInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert<0){
            throw  MyExcaption.from(Result.ERROR().massage("添加课程信息失败"));
        }

        EduCourseDescription eduCourseDescription  = new EduCourseDescription();
        BeanUtils.copyProperties(conresInfoVo,eduCourseDescription);
        eduCourseDescription.setId(eduCourse.getId());
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        System.out.println(eduCourseDescription);
        return eduCourse.getId();
    }

    /**
     * 根据课程ID查询课程基本信息
     * @param courseId
     * @return
     */
    @Override
    public ConresInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        ConresInfoVo conresInfoVo = new ConresInfoVo();
        BeanUtils.copyProperties(eduCourse,conresInfoVo);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(eduCourseDescription,conresInfoVo);
        return conresInfoVo;
    }


    /**
     * 根据课程ID更新课程的基本信息
     * @param conresInfoVo
     */
    @Override
    public void updataCourseInfo(ConresInfoVo conresInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(conresInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update==0){
            throw  new MyExcaption(20000,"修改信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(conresInfoVo,eduCourseDescription);

        eduCourseDescriptionService.updateById(eduCourseDescription);


    }

    @Override
    public CouresPublishVo getPublishCourseInfo(String courseId) {
        CouresPublishVo publishCouresInfo = baseMapper.getPublishCouresInfo(courseId);
        return publishCouresInfo;
    }
}
