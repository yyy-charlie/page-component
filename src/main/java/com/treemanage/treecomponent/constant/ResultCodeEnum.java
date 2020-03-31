package com.treemanage.treecomponent.constant;

import org.springframework.http.HttpStatus;

public enum ResultCodeEnum {

    //JSON转换错误
    INVALID_JSON(HttpStatus.BAD_REQUEST,"101","非法json对象"),
    INVALID_OBJECT(HttpStatus.BAD_REQUEST,"102","非法实例对象数组"),
    INVALID_PAGE(HttpStatus.BAD_REQUEST,"103","非法的page对象"),
    NOT_LIKE_JSON_OBJECT(HttpStatus.BAD_REQUEST,"103","JSON对象与实例对象不匹配"),
    INSTANTIA_OBJECT_ERROR(HttpStatus.BAD_REQUEST,"103","实例化对象报错"),

    /*-IO-*/
    CLOSE_IO_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","关流异常"),

    /*---解析--*/
    PARSE_XML_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","解析xml异常"),

    LOAD_PATH_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","根据path读取文件异常"),

    /** 操作成功 */
    SUCCESS(HttpStatus.OK, "200","操作成功"),

    //权限相关
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "403", "访问被拒绝"),

    /** 系统错误 */
    SYSTEM_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "","系统错误"),

    //服务不可用
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "007", "服务不可用"),

    /** 参数为空 */
    NULL_ARGUMENT(HttpStatus.BAD_REQUEST,"", "参数为空"),

    /** 参数不正确 */
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST,"", "参数不正确"),

    /** 邮箱非法 */
    EMAIL_ILLEGAL(HttpStatus.BAD_REQUEST, "","邮箱非法"),

    GET_LOCK_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"306","获取线程锁异常"),

    ADD_DATA_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","非法参数,新增数据异常"),

    DELETE_DATA_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","非法参数,删除数据异常"),

    UPDATE_DATA_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","非法参数,更新数据异常"),

    SELECT_DATA_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","非法参数,查询数据异常"),


    //任务操作异常
    CREATE_A_TASK(HttpStatus.INTERNAL_SERVER_ERROR,"411","创建定时任务异常"),

    UPDATE_TASK(HttpStatus.INTERNAL_SERVER_ERROR,"412","更新定时任务异常"),

    DELETE_TASK(HttpStatus.INTERNAL_SERVER_ERROR,"413","删除定时任务异常"),

    PAUSE_TASK(HttpStatus.INTERNAL_SERVER_ERROR,"414","暂停定时任务异常"),

    RESUME_TASK(HttpStatus.INTERNAL_SERVER_ERROR,"415","恢复定时任务异常"),

    /**-----------反射-------------*/
    NO_SUCH_FIELD(HttpStatus.INTERNAL_SERVER_ERROR,"","无此属性异常"),

    REFLECT_ILLEGAL(HttpStatus.INTERNAL_SERVER_ERROR,"","反射执行异常"),

    BUILD_EXCEL_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","生成excel文件异常"),

    DOWNLOAD_FILE_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","下载文件异常"),

    UPLOAD_FILE_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"","上传文件异常"),

    /*------------------- 线程 -------------------------*/

    THREAD_BLOCK_INVOKED(HttpStatus.INTERNAL_SERVER_ERROR,"","线程调用了阻塞方法异常"),

    THREAD_TASK_EXECUTE(HttpStatus.INTERNAL_SERVER_ERROR,"","正在执行的线程抛出的任何异常");


    private ResultCodeEnum(HttpStatus httpStatus, String value, String message) {
        this.httpStatus = httpStatus;
        this.value = value;
        this.message = message;
    }

    public static ResultCodeEnum getEnumByValue(String value) {
        for (ResultCodeEnum resultCode : ResultCodeEnum.values()) {
            if (resultCode.getValue().equals(value)) {
                return resultCode;
            }
        }
        return null;
    }

    private HttpStatus httpStatus;

    private String value;

    private String message;


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
