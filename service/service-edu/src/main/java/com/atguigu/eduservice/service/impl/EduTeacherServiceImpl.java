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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 前端分页显示讲师列表
     * @param eduTeacherPage
     * @return
     */
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> eduTeacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(eduTeacherPage,wrapper);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        //单前页数
        long current = eduTeacherPage.getCurrent();
        //总页数
        long pages = eduTeacherPage.getPages();
        //总记录数
        long total = eduTeacherPage.getTotal();
        //单前页有多少条记录
        long size = eduTeacherPage.getSize();
        //是否有下一页
        boolean hasNext = eduTeacherPage.hasNext();
        //是否有上一页
        boolean hasPrevious = eduTeacherPage.hasPrevious();

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("current",current);
        hashMap.put("records",records);
        hashMap.put("pages",pages);
        hashMap.put("total",total);
        hashMap.put("size",size);
        hashMap.put("hasNext",hasNext);
        hashMap.put("hasPrevious",hasPrevious);
        return hashMap;
    }


}
