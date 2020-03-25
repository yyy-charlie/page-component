package com.pagemanage.pagecomponent.msg;

import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class BaseResponse<Entity> {

    private int code = 200;
    private String description;

    public BaseResponse() {
    }

    public BaseResponse(String description) {
        this.description = description;
    }

    public BaseResponse(String description,Exception e) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.description = description + e.getMessage();
    }

    public BaseResponse(HttpStatus httpStatus , String description) {
        this.code = httpStatus.value();
        this.description = description;
    }

    public BaseResponse(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
