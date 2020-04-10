package com.treemanage.treecomponent.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ServicesVo
 * @Description TODO
 * @Author ycn
 * @Date 2020-04-07
 **/
@Data
public class ServicesVo {
    private String serviceName;

    private List<MethodVo> methodVoList;
}
