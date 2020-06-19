package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.exception.MyExcaption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SubjectListener extends AnalysisEventListener<ExcelData> {


    List<ExcelData> list = new ArrayList<ExcelData>();

    private EduSubjectService eduSubjectService;

    public SubjectListener(){

    }

    /**
     * 手动注入service
     * @param eduSubjectService
     */
    public SubjectListener(EduSubjectService eduSubjectService){
        this.eduSubjectService = eduSubjectService;
    }


    /**
     * 读取Excel文件保存在数据库
     * @param excelData
     * @param analysisContext
     */


    /**
     * 数据为一行一行的读取，重第二行开始
     * @param excelData
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {

        if(excelData==null){
            log.error("文件为空");
            return;
        }
        //判断是否纯在一级分类，如果没有就进行保存
        EduSubject eduSubjectOne = this.existOneEduSubject(eduSubjectService, excelData.getOneSubjectName());
        if(eduSubjectOne==null){
            eduSubjectOne = new EduSubject();
            eduSubjectOne.setTitle(excelData.getOneSubjectName());
            eduSubjectOne.setParentId("0");
            eduSubjectService.save(eduSubjectOne);
            System.out.println(eduSubjectOne);
        }
        //判断是否纯在二级分类，如果没有就进行保存
        String Pid = eduSubjectOne.getId(); //或者一级分类的ID
        EduSubject eduSubjectTwo = this.existTwoEduSubject(eduSubjectService, excelData.getTwoSubjectName(), Pid);
        if(eduSubjectTwo==null){
            eduSubjectTwo = new EduSubject();
            eduSubjectTwo.setParentId(Pid);
            eduSubjectTwo.setTitle(excelData.getTwoSubjectName());
            eduSubjectService.save(eduSubjectTwo);
            System.out.println(eduSubjectTwo);
        }


    }

    /**
     * 按名称查询是否存在一级分类
     * @param eduSubjectService
     * @param Name
     * @return
     */
    public EduSubject existOneEduSubject(EduSubjectService eduSubjectService,String Name){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
            eduSubjectQueryWrapper.eq("parent_id",0);
            eduSubjectQueryWrapper.eq("title",Name);
        EduSubject one = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return one;

    }

    /**
     * 按名称和父ID查询二级分类
     * @param eduSubjectService
     * @param Name
     * @param parentId
     * @return
     */
    public EduSubject existTwoEduSubject(EduSubjectService eduSubjectService,String Name,String parentId){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id",parentId);
        eduSubjectQueryWrapper.eq("title",Name);
        EduSubject two = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return two;

    }



    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
