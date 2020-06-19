package com.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.testEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelListener extends AnalysisEventListener<testEntity> {

    public  static List<testEntity> list = new ArrayList<>();

    @Override
    public void invoke(testEntity testEntity, AnalysisContext analysisContext) {
        System.out.println(testEntity);
          list.add(testEntity);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info(String.valueOf(list.size()));
    }
}
