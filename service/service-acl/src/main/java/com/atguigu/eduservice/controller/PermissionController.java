package com.atguigu.eduservice.controller;



import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.Permission;
import com.atguigu.eduservice.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
@CrossOrigin
@Api(description = "权限菜单模块")
public class PermissionController {

    @Resource
    PermissionService permissionService;


    @ApiOperation(value = "全部菜单")
    @GetMapping
    public Result getIndexAllPermission(){
        List<Permission> permissionsList = permissionService.getIndexAllPermission();
        return Result.OK().data("children",permissionsList);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public  Result remove(@PathVariable("id") String id){
        permissionService.removeChildenById(id);
        return Result.OK();
    }

    @ApiOperation("添加角色权限")
    @PostMapping("doAssion")
    public Result doAssion(String userID,String[] pisId){
        permissionService.saveRolePermissionRealtionShip(userID,pisId);
        return Result.OK();
    }
}

