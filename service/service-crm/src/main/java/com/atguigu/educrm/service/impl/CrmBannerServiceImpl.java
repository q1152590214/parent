package com.atguigu.educrm.service.impl;

import com.atguigu.educrm.entity.CrmBanner;
import com.atguigu.educrm.mapper.CrmBannerMapper;
import com.atguigu.educrm.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-04
 */
@Service
@CacheConfig(keyGenerator = "keyGenerator",cacheNames = "banner")
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        crmBannerQueryWrapper.orderByDesc("id");
        crmBannerQueryWrapper.last("LIMIT 2");
        List<CrmBanner> crmBanners = baseMapper.selectList(crmBannerQueryWrapper);
        return crmBanners;
    }
}
