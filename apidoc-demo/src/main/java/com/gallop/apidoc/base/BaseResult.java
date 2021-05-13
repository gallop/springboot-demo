package com.gallop.apidoc.base;

import com.gallop.apidoc.enums.BaseResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * class_name: BaseResult
 * describe: 如果使用注解@JsonInclude(JsonInclude.Include.NON_NULL)： 则会保证序列化json的时候,如果是null的对象,key也会消失
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> {
    private Integer code;

    private boolean success;

    private String message;

    private T entity;

    /**
     * 判断是否是成功结果
     * JsonIgnore使之不在json序列化结果当中
     *
     * @return 是否为成功结果
     */
    public boolean isSuccess() {
        return BaseResultEnum.SUCCESS.getCode() == this.code;
    }
    /**
     *  填充错误参数
     */
    public BaseResult fillArgs(Object... args) {
        message = String.format(BaseResultEnum.BIND_ERROR.getMessage(), args);
        return new BaseResult(BaseResultEnum.BIND_ERROR.getCode(),false, message,new HashMap());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
