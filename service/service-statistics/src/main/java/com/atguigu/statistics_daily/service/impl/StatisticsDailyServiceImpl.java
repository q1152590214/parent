package com.atguigu.statistics_daily.service.impl;

import com.atguigu.Result.Result;
import com.atguigu.statistics_daily.client.UcenterClient;
import com.atguigu.statistics_daily.entity.StatisticsDaily;
import com.atguigu.statistics_daily.mapper.StatisticsDailyMapper;
import com.atguigu.statistics_daily.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-14
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
   @Resource
   private UcenterClient ucenterClient;


    /**
     * 查询那天有多少人注册
     * @param day
     */
    @Override
    public void registerCount(String day) {
        QueryWrapper<StatisticsDaily> statisticsDailyQueryWrapper  = new QueryWrapper<>();
        statisticsDailyQueryWrapper.eq("date_calculated",day);
        baseMapper.delete(statisticsDailyQueryWrapper);
        Result result = ucenterClient.countRegister(day);
        Object count = result.getData().get("countRegister");
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum((Integer) count);
        Integer num1= RandomUtils.nextInt(100,200);
        Integer num2= RandomUtils.nextInt(100,200);
        Integer num3= RandomUtils.nextInt(100,200);
        statisticsDaily.setCourseNum(num1);
        statisticsDaily.setLoginNum(num2);
        statisticsDaily.setVideoViewNum(num3);
        statisticsDaily.setDateCalculated(day);
        baseMapper.insert(statisticsDaily);
    }


    /**
     * 图片显示数据
     * @param type 查询那一列
     * @param begin 开始时间
     * @param edn 结束时间
     * @return
     */
    @Override
    public Map<String, Object> getShowData(String type, String begin, String edn) {
        QueryWrapper<StatisticsDaily> statisticsDailyQueryWrapper = new QueryWrapper<>();
        statisticsDailyQueryWrapper.ge("date_calculated",begin);
        statisticsDailyQueryWrapper.le("date_calculated",edn);
        statisticsDailyQueryWrapper.select("date_calculated",type);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(statisticsDailyQueryWrapper);
        List<String> data_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();
        for (StatisticsDaily daily :statisticsDailies){
            StatisticsDaily statisticsDaily = daily;
            data_calculatedList.add(daily.getDateCalculated());
            switch (type){
                case "register_num" :
                    numDataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num" :
                    numDataList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num" :
                    numDataList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num" :
                    numDataList.add(statisticsDaily.getCourseNum());
                    break;
            }

        }
        Map<String,Object> map = new HashMap<>();
        map.put("data_calculatedList",data_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
