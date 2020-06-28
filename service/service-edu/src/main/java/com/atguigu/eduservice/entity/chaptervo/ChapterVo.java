package com.atguigu.eduservice.entity.chaptervo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    private String id;
    private String title;
    private List<VideoVo> list = new ArrayList<>();
}
