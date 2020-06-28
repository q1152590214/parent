package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-12
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    /**
     * 分页条件查询
     * @param pageParam
     * @param teacherQuery
     */
    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>();
        String name = teacherQuery.getName();
        String end = teacherQuery.getEdn();
        String begin = teacherQuery.getBegin();
        Integer level = teacherQuery.getLevel();

        if(!StringUtils.isEmpty(name)){
                wrapper.like("name",name);
        }

        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }

        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }

        if(!StringUtils.isEmpty(level)){
            wrapper.like("level",level);
        }


      baseMapper.selectPage(pageParam,wrapper);


    }
}
