package com.bs.common.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 自定义异常类
 */
@Data
public class GuliException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "异常信息")
    private String msg;

    public GuliException(){ }

    public GuliException(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
