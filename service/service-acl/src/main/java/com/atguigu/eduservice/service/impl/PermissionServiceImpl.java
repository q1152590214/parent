package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.Permission;
import com.atguigu.eduservice.entity.RolePermission;
import com.atguigu.eduservice.helper.MemuHelper;
import com.atguigu.eduservice.mapper.PermissionMapper;
import com.atguigu.eduservice.service.PermissionService;
import com.atguigu.eduservice.service.RolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-15
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    RolePermissionService rolePermissionService;

    @Override
    public List<Permission> getIndexAllPermission() {
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.orderByDesc("id");
        List<Permission> permissions = baseMapper.selectList(permissionQueryWrapper);
        List<Permission> indexAllPermission = MemuHelper.bulidPermission(permissions);
        return indexAllPermission;
    }

    @Override
    public void removeChildenById(String id) {
        Permission permission = baseMapper.selectById(id);
        List<String> permissionsIdList = new ArrayList<>();
        this.selectChildenByIdDelete(id,permissionsIdList);
        permissionsIdList.add(id);
        baseMapper.deleteBatchIds(permissionsIdList);
    }


    @Override
    public void saveRolePermissionRealtionShip(String userID, String[] pisId) {
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (int i =0;i<pisId.length;i++){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(pisId[i]);
            rolePermission.setRoleId(userID);
            rolePermissions.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissions);
    }

    /**
     * 递归删除
     * @param id
     * @param permissionsIdList
     */
    private void selectChildenByIdDelete(String id, List<String> permissionsIdList) {
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.eq("pid",id);
        List<Permission> childenList = baseMapper.selectList(permissionQueryWrapper);
            childenList.stream().forEach(item ->{
                permissionsIdList.add(item.getId());
                selectChildenByIdDelete(item.getId(),permissionsIdList);
        });
    }
}
