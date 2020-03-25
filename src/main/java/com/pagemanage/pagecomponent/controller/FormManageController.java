package com.pagemanage.pagecomponent.controller;

import com.pagemanage.pagecomponent.bo.BeanStructureInfo;
import com.pagemanage.pagecomponent.export.intf.InportExportBean;
import com.pagemanage.pagecomponent.msg.BaseResponse;
import com.pagemanage.pagecomponent.util.ReturnUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName FormManageController
 * @Description TODO
 * @Author ycn
 * @Date 2020-03-23
 **/
@RestController
@ResponseBody
@RequestMapping("/formManage")
public class FormManageController {
    @Autowired
    private InportExportBean inportExportBean;

    /**
     * 导入对象
     *
     * @param uploadFile
     */
    @RequestMapping("/importObj")
    public BaseResponse importObj(MultipartFile uploadFile) {
        String description = "导入对象";
        BeanStructureInfo beanStructureInfo = null;
        try {
            beanStructureInfo = inportExportBean.importBeanStructureInfo(uploadFile);
        } catch (Exception e) {
            description = ReturnUtils.judgeBaseException(e, description);
        }

        return ReturnUtils.parseReturn(beanStructureInfo, description);
    }
}
