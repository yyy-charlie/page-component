package com.treemanage.treecomponent.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ControllerVo
 * @Description TODO
 * @Author ycn
 * @Date 2020-04-07
 **/
@Data
public class ControllerVo {
    /**
     * 控制器名称
     */
    private String controllerName;
    /**
     * 控制器里面的方法
     */
    private List<MethodVo> methodVoList;
}
