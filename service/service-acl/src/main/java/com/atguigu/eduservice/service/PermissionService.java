package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-15
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> getIndexAllPermission();

    void removeChildenById(String id);

    void saveRolePermissionRealtionShip(String userID, String[] pisId);
}
