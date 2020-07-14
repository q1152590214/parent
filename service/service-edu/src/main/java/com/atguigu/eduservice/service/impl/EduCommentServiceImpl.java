package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-10
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Map<String,Object> getPageCommentList(Page<EduComment> eduCommentPage, String courseId) {
        QueryWrapper<EduComment> eduCommentQueryWrapper = new QueryWrapper<>();
        eduCommentQueryWrapper.orderByDesc("gmt_create");
        eduCommentQueryWrapper.eq("course_id",courseId);

        baseMapper.selectPage(eduCommentPage,eduCommentQueryWrapper);
        List<EduComment> records = eduCommentPage.getRecords();
        //单前页数
        long current = eduCommentPage.getCurrent();
        //总页数
        long pages = eduCommentPage.getPages();
        //总记录数
        long total = eduCommentPage.getTotal();
        //单前页有多少条记录
        long size = eduCommentPage.getSize();
        //是否有下一页
        boolean hasNext = eduCommentPage.hasNext();
        //是否有上一页
        boolean hasPrevious = eduCommentPage.hasPrevious();

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("current",current);
        hashMap.put("items",records);
        hashMap.put("pages",pages);
        hashMap.put("total",total);
        hashMap.put("size",size);
        hashMap.put("hasNext",hasNext);
        hashMap.put("hasPrevious",hasPrevious);
        return hashMap;
    }
}
