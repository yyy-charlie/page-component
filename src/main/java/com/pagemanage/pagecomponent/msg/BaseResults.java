package com.pagemanage.pagecomponent.msg;

import com.pagemanage.pagecomponent.util.ToolUtils;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class BaseResults<T> extends BaseResponse{

    private List<T> data = new ArrayList<>();

    public BaseResults() {

    }

    public BaseResults(int code, String discription, List<T> data) {
        super(code,discription);
        this.data = data;
    }

    public BaseResults(HttpStatus httpStatus, String discription, List<T> data) {
        super(httpStatus,discription);
        this.data = data;
    }

    public BaseResults(String discription, List<T> data){
        if(ToolUtils.judgeList(data)){
           super.setCode(HttpStatus.OK.value());
           super.setDescription(discription);
           this.data = data;
        }else {
            super.setCode(HttpStatus.NO_CONTENT.value());
            super.setDescription(discription);
        }
    }
}
