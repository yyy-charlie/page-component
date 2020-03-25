package com.pagemanage.pagecomponent.vo;

import lombok.Data;

/**
 * @Author: Mist
 * @Version: 1.0
 * @Date: 2020/1/3 16:00
 */
@Data
public class VOAddBeanTreeStuctureInfo {
    private Long parentNodeId;
    private VOBeanTreeStuctureInfo beanTreeStructureInfo;
}
