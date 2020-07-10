package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chaptervo.ChapterVo;
import com.atguigu.eduservice.entity.chaptervo.VideoVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.exception.MyExcaption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    @Resource
    EduVideoService eduVideoService;


    /**
     * 根据课程ID查询章节，包含下面小节
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCouresId(String courseId) {

        //课程下面的章节
        QueryWrapper<EduChapter> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(eduCourseQueryWrapper);

        //课程下面的小节
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(eduVideoQueryWrapper);
        List<ChapterVo> list = new ArrayList<>();

        //循环章节
        for (int i=0;i<eduChapterList.size();i++){
            EduChapter eduChapter = eduChapterList.get(i);

            ChapterVo chapterVo = new ChapterVo();

            BeanUtils.copyProperties(eduChapter,chapterVo);

            List<VideoVo> videoVos = new ArrayList<>();

            //判断小节属于那个章节
            for (int m=0;m<eduVideoList.size();m++){
                EduVideo eduVideo = eduVideoList.get(m);
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVos.add(videoVo);
                }

            }
            chapterVo.setList(videoVos);
            list.add(chapterVo);

        }

        return list;
    }


    /**
     * 删除章节,判断下面是否还有小节，如果没就删除，有就不删
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(eduVideoQueryWrapper);
        if(count>0){
            throw new MyExcaption(20001,"章节内还有小节，不能删除");
        }else {
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    @Override
    public void deleteByCourseId(String courseId) {
        QueryWrapper<EduChapter> eduEduChapterWrapper = new QueryWrapper<>();
        eduEduChapterWrapper.eq("course_id",courseId);
        baseMapper.delete(eduEduChapterWrapper);
    }



}
