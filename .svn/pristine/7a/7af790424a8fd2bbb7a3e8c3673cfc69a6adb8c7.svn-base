package com.treemanage.treecomponent.util;

import com.container.treecontainer.exception.TreeContainerException;
import com.container.treecontainer.util.ObjectUtils;
import com.treemanage.treecomponent.exception.BaseException;
import com.treemanage.treecomponent.msg.BaseResponse;
import org.springframework.http.HttpStatus;

/**
 * @Author: Mist
 * @Version: 1.0
 * @Date: 2020/1/3 15:46
 */
public class ReturnUtils {

    public static String judgeTreeContainerException(Exception e, String description, String newDescription) {
        if(e instanceof TreeContainerException){
            return newDescription;
        }
        return description;
    }

    public static String judgeBaseException(Exception e, String description) {
        return e instanceof BaseException ? e.getMessage():description;
    }


    public static BaseResponse parseReturn(Object result, String description) {
        return !ObjectUtils.isNull(result)?ParseUtils.parse2Response(HttpStatus.OK, description + "，成功", result):
                ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR, description + "，失败", result);
    }


    public static BaseResponse parseReturn(boolean result, String description) {
        return result?ParseUtils.parse2Response(HttpStatus.OK, description + "，成功"):
                ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR, description + "，失败");
    }
}
