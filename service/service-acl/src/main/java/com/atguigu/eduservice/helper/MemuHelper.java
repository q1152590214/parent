package com.atguigu.eduservice.helper;

import com.atguigu.eduservice.entity.Permission;

import java.util.ArrayList;
import java.util.List;

public class MemuHelper {

    public static  List<Permission>  bulidPermission(List<Permission> permissionList){
        List<Permission> finalNode  = new ArrayList<>();
        for (Permission permissionNode : permissionList){
            if("0".equals(permissionNode.getPid())){
                permissionNode.setLevel(1);
                finalNode.add(selectChildren(permissionList,permissionNode));

            }


        }
        return finalNode;
    }

    /**
     * 递归添加
     * @param permissionList 全部的数据
     * @param permissionNode 父类
     * @return
     */
    public static Permission  selectChildren(List<Permission> permissionList,Permission permissionNode){

        //循环全部数据
        for (Permission pis : permissionList){
            //判断全部数据里是否有父类的子类
            if(permissionNode.getId().equals(pis.getPid())){
                Integer level = permissionNode.getLevel()+1;
                pis.setLevel(level);
                if(permissionNode.getChildren()==null){
                    permissionNode.setChildren(new ArrayList<Permission>());
                }
                //递归添加：如果有吧子类当为父类在次添加
                permissionNode.getChildren().add(selectChildren(permissionList,pis));
            }
        }
        return permissionNode;
    }

}
