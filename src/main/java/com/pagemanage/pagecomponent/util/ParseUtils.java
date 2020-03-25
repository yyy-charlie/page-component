package com.pagemanage.pagecomponent.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pagemanage.pagecomponent.constant.Constants;
import com.pagemanage.pagecomponent.constant.ResultCodeEnum;
import com.pagemanage.pagecomponent.exception.BusinessException;
import com.pagemanage.pagecomponent.msg.BaseResponse;
import com.pagemanage.pagecomponent.msg.BaseResult;
import com.pagemanage.pagecomponent.msg.BaseResults;
import com.pagemanage.pagecomponent.msg.TableResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Slf4j
public class ParseUtils implements  Constants {

    public static <T> T parseJSON2Object(String json, Class<T> property) {

        isEmptyByJSON(json);

        return  JSONObject.parseObject(json,property);
    }

    public static <T> T parseJSONObjectByKey(String json, Class<T> property, String key) {

        JSONObject obj = parseObject(json);

        if(obj.containsKey(key)){

            JSONObject object = obj.getJSONObject(key);

            return ToolUtils.isNull(object) ? null:JSON.toJavaObject(object,property);
        }
        return null;
    }

    public static Map<String, Object> parseJSON2Map(String Parameter) {

        JSONObject json = parseObject(Parameter);

        return JSONObject.toJavaObject(json, Map.class);
    }

    public static boolean parseReturnData2Bool(String json, String key) {

        JSONObject obj = parseObject(json);

        return obj.containsKey(key) ? Constants.CODE_OK.equals(obj.getString(key)) : false;
    }

    public static <T> List<T>  parseReturnData2Arrays(String json, Class<T> property, String key) {

        JSONObject obj = parseObject(json);

        if(obj.containsKey(key)){

            List<T> list = null;

            try {
                list = parseJSON2Arrays(obj.getString(key),property);
            } catch (Exception e) {
                return null;
            }

            return  ToolUtils.judgeList(list) ? list : null;
        }

        return null;
    }

    public static List<String> parseReturnData2Arrays(String json, String key) {

       return parseReturnData2Arrays(json,String.class,key);
    }

    public static JSONObject buildJSONObject(String key,String value) {

        JSONObject object = new JSONObject();

        if(!ToolUtils.isEmpty(value)){

            object.put(key,value);
        }
        return object;
    }

    public static JSONObject buildJSONObject(String key,Object value) {

        JSONObject object = new JSONObject();

        if(!ToolUtils.isNull(value)){

            object.put(key,value);
        }
        return object;
    }

    public static JSONObject buildJSONObject(String[] keys,String ...value) {

        JSONObject object = new JSONObject();

        for (int i = 0 ; i < keys.length ;i ++ ){

            if(!ToolUtils.isNull(value[i])){

                object.put(keys[i],value[i]);
            }
        }
        return object;
    }

    public static JSONObject buildJSONObject(String[] keys,Object ...value) {

        JSONObject object = new JSONObject();

        for (int i = 0 ; i < keys.length ;i ++ ){

            if(!ToolUtils.isNull(value[i])){

                object.put(keys[i],value[i]);
            }
        }
        return object;
    }

    public static JSONObject buildJSONObject(List<String> list, String key) {

        JSONObject object = new JSONObject();

        if(ToolUtils.judgeList(list)){
            object.put(key,list);
        }
        return object;
    }

    public static <T> List<T>  parseJSON2Arrays(String json, Class<T> property) {

        isEmptyByJSON(json);

        return JSON.parseArray(json,property);
    }

    public static JSONObject parseObject(String parameter) {

        isEmptyByJSON(parameter);

        return JSONObject.parseObject(parameter);
    }

    public static <T> JSONObject parseObject(T t) {

        if(ToolUtils.isNull(t)) return null;

        return JSONObject.parseObject(JSON.toJSONString(t));
    }

    public static JSONArray parseArray(String parameter){

        isEmptyByJSON(parameter);

        return JSONArray.parseArray(parameter);
    }

    public static  <T> String parseObjectToJson(T t) {
        return ToolUtils.isNull(t) ? null : JSONObject.toJSONString(t);
    }

    /*-------------------------  主流格式 -------------------------------------*/

    public static BaseResponse parse2Response(int code, String discription) {

        return new BaseResponse(code,discription);
    }

    public static BaseResponse parse2Response(HttpStatus httpStatus, String discription) {

        return new BaseResponse(httpStatus,discription);
    }

    public static <T> BaseResponse parse2Response(int code, String discription, T data) {

        return new BaseResult(code,discription,data);
    }

    public static <T> BaseResponse parse2Response(String discription, T data) {

        return new BaseResult(HttpStatus.OK,discription,data);
    }

    public static <T> BaseResponse parse2Response(HttpStatus httpStatus, String discription, T data) {

        return new BaseResult(httpStatus,discription,data);
    }

    public static <T> BaseResponse parse2Response(int code, String discription, List<T> data) {

        return new BaseResults(code,discription,data);
    }

    public static <T> BaseResponse parse2Response(String discription, List<T> data) {

        return new BaseResults(HttpStatus.OK,discription,data);
    }

    public static <T> BaseResponse parse2Response(HttpStatus httpStatus, String discription, List<T> data) {

        return new BaseResults(httpStatus,discription,data);
    }

    public static <T> BaseResponse parse2Response(int code, String discription, long total, List<T> rows) {

        return new TableResult(code,discription,total,rows);
    }

    public static <T> BaseResponse parse2Response(HttpStatus httpStatus, String discription, long total, List<T> rows) {

        return new TableResult(httpStatus,discription,total,rows);
    }

    public static <T> BaseResponse parse2Response(long total, List<T> rows) {

        return new TableResult(total,rows);
    }


    /*-------------------------------------------- 特殊格式 + state -----------------------------------------------------------------*/


    public static String  parse2StateResponse(int code, String discription){

        return buildJSONObject(JSON_STATE,new BaseResponse(code,discription)).toJSONString();
    }

    public static String parse2StateResponse(HttpStatus httpStatus, String discription){

        return buildJSONObject(JSON_STATE,new BaseResponse(httpStatus,discription)).toJSONString();
    }

    public static <T> String parse2StateResponse(int code, String discription, T data) {

       JSONObject json =  buildJSONObject(JSON_STATE,new BaseResponse(code,discription));

       return returnJSONString(json,data);
    }

    public static <T> String parse2StateResponse(String discription, T data) {

        JSONObject json =  buildJSONObject(JSON_STATE,new BaseResponse(discription));

        return returnJSONString(json,data);
    }

    public static <T> String parse2StateResponse(HttpStatus httpStatus, String discription, T data) {

        JSONObject json =  buildJSONObject(JSON_STATE,new BaseResponse(httpStatus,discription));

        return returnJSONString(json,data);
    }

    public static <T> String parse2StateResponse(int code, String discription, List<T> data) {

        JSONObject json =  buildJSONObject(JSON_STATE,new BaseResponse(code,discription));

        return returnJSONString(json,data);
    }

    public static <T> String parse2StateResponse(String discription, List<T> data) {

        JSONObject json =  buildJSONObject(JSON_STATE,new BaseResponse(discription));

        return returnJSONString(json,data);
    }

    public static <T> String parse2StateResponse(HttpStatus httpStatus, String discription, List<T> data) {

        JSONObject json =  buildJSONObject(JSON_STATE,new BaseResponse(httpStatus,discription));

        return returnJSONString(json,data);
    }

    public static <T> String parse2StateResponse(int code, String discription, long total, List<T> rows) {

        JSONObject json =  buildJSONObject(JSON_STATE,new BaseResponse(code,discription));

        return returnJSONString(json,new TableResult(total,rows));
    }

    public static <T> String parse2StateResponse(HttpStatus httpStatus, String discription, long total, List<T> rows) {

        JSONObject json =  buildJSONObject(JSON_STATE,new BaseResponse(httpStatus,discription));

        return returnJSONString(json,new TableResult(total,rows));
    }

    public static <T> String parse2StateResponse(String discription,long total, List<T> rows) {

        JSONObject json =  buildJSONObject(JSON_STATE,new BaseResponse(discription));

        return returnJSONString(json,new TableResult(total,rows));
    }


    /*------------------------------------private ------------------------------------------------*/

    private static void isEmptyByJSON(String parameter){

        if(ToolUtils.isEmpty(parameter)){

            log.error("参数为空" + parameter);
            throw new BusinessException(ResultCodeEnum.NULL_ARGUMENT);
        }
    }

    private static String  returnJSONString(JSONObject json,Object data){

        json.put(JSON_DATA,data);

        return json.toJSONString();
    }
}
