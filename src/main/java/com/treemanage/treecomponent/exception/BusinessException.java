package com.treemanage.treecomponent.exception;


import com.treemanage.treecomponent.constant.RestCodeConstants;
import com.treemanage.treecomponent.constant.ResultCodeEnum;

public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message, RestCodeConstants.EX_BUSINESS_BASE_CODE);
    }

    public BusinessException(int status, String message) {
        super(message, status);
    }

    public BusinessException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage(), codeEnum.getHttpStatus().value());
    }
}
