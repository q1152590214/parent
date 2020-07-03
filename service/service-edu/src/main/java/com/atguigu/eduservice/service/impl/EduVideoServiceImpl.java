package com.atguigu.eduservice.service.impl;

import com.atguigu.Result.Result;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-22
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Resource
    VodClient vodClient;

    @Override
    public void deleteByCourseId(String courseId) {
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        eduVideoQueryWrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(eduVideoQueryWrapper);
        List<String> vodIdList = new ArrayList<>();
        for (int i=0;i<eduVideos.size();i++){
            EduVideo eduVideo = eduVideos.get(i);
            String videoId =eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoId)){
                vodIdList.add(videoId);
            }

        }
        if(vodIdList.size()>0){
            Result result = vodClient.deleteBatch(vodIdList);
            System.out.println(result);
        }

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
