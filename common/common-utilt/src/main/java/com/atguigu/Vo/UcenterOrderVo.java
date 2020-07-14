package com.atguigu.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UcenterOrderVo {
    @ApiModelProperty(value = "会员id")
    private String id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickname;
}
