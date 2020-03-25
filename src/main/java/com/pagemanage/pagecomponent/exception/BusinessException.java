package com.pagemanage.pagecomponent.exception;


import com.pagemanage.pagecomponent.constant.RestCodeConstants;
import com.pagemanage.pagecomponent.constant.ResultCodeEnum;

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
