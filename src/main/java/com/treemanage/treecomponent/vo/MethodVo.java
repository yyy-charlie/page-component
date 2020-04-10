package com.treemanage.treecomponent.vo;

import lombok.Data;

/**
 * @ClassName MethodVo
 * @Description TODO
 * @Author ycn
 * @Date 2020-04-07
 **/
@Data
public class MethodVo {
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数
     */
    private String param;
    /**
     * 返回
     */
    private String returnStr;
}
