package com.gallop.apidoc.base;


import com.gallop.apidoc.enums.BaseResultEnum;

import java.util.HashMap;

public class BaseResultGenerator {
    /**
     * 生成返回结果
     *
     * @param code    返回编码
     * @param message 返回消息
     * @param data    返回数据
     * @param <T>     返回数据类型
     * @return 返回结果
     */
    public static <T> BaseResult<T> generate(final int code, final String message, T data) {
        return new BaseResult<>(code, false, message, data);
    }


    /**
     * 操作成功响应结果， 自定义数据及信息
     *
     * @param message 自定义信息
     * @param data    自定义数据
     * @param <T>     自定义数据类型
     * @return 响应结果
     */
    public static <T> BaseResult<T> success(final String message, final T data) {
        return new BaseResult<>(BaseResultEnum.SUCCESS.getCode(), true, message, data);
    }

    /**
     * 操作成功响应结果，自定义数据，默认信息
     *
     * @param data 自定义数据
     * @param <T>  自定义数据类型
     * @return 响应结果
     */
    public static <T> BaseResult<T> success(final T data) {
        return new BaseResult<>(BaseResultEnum.SUCCESS.getCode(), true, BaseResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 操作成功响应结果，自定义信息，无数据
     *
     * @param message 自定义信息
     * @return 响应结果
     */
    public static <T> BaseResult<T> success4Message(final String message, T data) {
        return new BaseResult<>(BaseResultEnum.SUCCESS.getCode(), true, message, data);
    }

    /**
     * 操作失败响应结果， 默认结果
     *
     * @return 操作失败的默认响应结果
     */
    public static <T> BaseResult<T> failure() {
        BaseResult<T> tBaseResult =(BaseResult<T>) new BaseResult<>(BaseResultEnum.FAIL.getCode(), false, BaseResultEnum.FAIL.getMessage(), new HashMap<>());
        return tBaseResult;
    }
    /**
     * 操作失败响应结果， 传入消息参数
     *
     * @return 操作失败 返回传入的消息
     */
    public static <T> BaseResult<T> failure(String message) {
        BaseResult<T> tBaseResult = (BaseResult<T>) new BaseResult<>(BaseResultEnum.FAIL.getCode(), false, message, new HashMap<>());
        return tBaseResult;
    }

    /**
     * 操作失败响应结果， 自定义错误编码及信息
     *
     * @param code    自定义错误编码
     * @param message 自定义信息
     * @return 响应结果
     */
    public static <T> BaseResult<T> failure(final int code, final String message) {
        BaseResult<T> tBaseResult = (BaseResult<T>)  new BaseResult<>(code, false, message, new HashMap<>());
        return tBaseResult;
    }

    public static <T> BaseResult<T> failureLoginOut(final String message) {
        BaseResult<T> tBaseResult = (BaseResult<T>)  new BaseResult<>(BaseResultEnum.NOT_AUTH.getCode(), false, message, new HashMap<>());
        return tBaseResult;
    }


    /**
     * 操作失败响应结果，自定义错误编码
     *
     * @param baseResultEnum 自定义错误编码枚举
     * @return 响应结果
     */
    public static <T> BaseResult<T> failure(final BaseResultEnum baseResultEnum, T data) {
        return new BaseResult<>(baseResultEnum.getCode(), false, baseResultEnum.getMessage(), data);
    }

    /**
     * 操作失败响应结果，自定义信息
     *
     * @param message 自定义信息
     * @return 响应结果
     */
    public static <T> BaseResult<T> failure(final String message, T data) {
        return new BaseResult<>(BaseResultEnum.FAIL.getCode(), false, message, data);
    }

    /**
     * 异常响应结果， 默认结果
     *
     * @return 操作成功的默认响应结果
     */
    public static <T> BaseResult<T> error(String message) {
        BaseResult<T> tBaseResult = (BaseResult<T>) new BaseResult<>(BaseResultEnum.SERVER_ERROR.getCode(), false,  message, new HashMap<>());
        return tBaseResult;
    }
    /*    *//**
     * 异常响应结果， 默认结果
     *
     * @return 操作成功的默认响应结果
     *//*
    public static <T> BaseResult<T> errorEx(Object o) {
        if(o instanceof String){
            return BaseResultGenerator.error((String)o);
        }else if(o instanceof BaseResultEnum){
            BaseResultEnum e=(BaseResultEnum)o;
            return BaseResultGenerator.error(e);
        }else{
            return BaseResultGenerator.error();
        }
    }*/
    /**
     * 请求参数错误
     *
     * @return 操作成功的默认响应结果
     */
    public static <T> BaseResult<T> validateFailed(Object... args) {
        String message=String.format(BaseResultEnum.BIND_ERROR.getMessage(),args);
        BaseResult<T> tBaseResult = (BaseResult<T>) new BaseResult<>(BaseResultEnum.BIND_ERROR.getCode(), false,  message, new HashMap<>());
        return tBaseResult;
    }
    /**
     * 异常响应结果， 默认结果
     *
     * @return 操作成功的默认响应结果
     */
    public static <T> BaseResult<T> error() {
        BaseResult<T> tBaseResult = (BaseResult<T>) new BaseResult<>(BaseResultEnum.SERVER_ERROR.getCode(), false,BaseResultEnum.SERVER_ERROR.getMessage(), new HashMap<>());
        return tBaseResult;
    }
    /**
     * 异常响应结果， 自定义错误编码及信息
     *
     * @param code    自定义错误编码
     * @param message 自定义信息
     * @return 响应结果
     */
    public static <T> BaseResult<T> error(final int code, final String message, T data) {
        return new BaseResult<>(code, false, message, data);
    }

    /**
     * 异常响应结果，自定义错误编码
     *
     * @param baseResultEnum 自定义错误编码枚举
     * @return 响应结果
     */
    public static <T> BaseResult<T> error(final BaseResultEnum baseResultEnum, T data) {
        return new BaseResult<>(baseResultEnum.getCode(), false, baseResultEnum.getMessage(), data);
    }
    /**
     * 异常响应结果，自定义错误编码
     *
     * @param baseResultEnum 自定义错误编码枚举
     * @return 响应结果
     */
    public static <T> BaseResult<T> error(final BaseResultEnum baseResultEnum) {

        BaseResult<T> tBaseResult = (BaseResult<T>) new BaseResult<>(baseResultEnum.getCode(), false,baseResultEnum.getMessage(), new HashMap<>());
        return tBaseResult;
        //return new BaseResult<>(baseResultEnum.getCode(), false, baseResultEnum.getMessage(),new HashMap<>());
    }

    /**
     * 异常响应结果，自定义信息
     *
     * @param message 自定义信息
     * @return 响应结果
     */
    public static <T> BaseResult<T> error(final String message, T data) {
        return new BaseResult<>(BaseResultEnum.SERVER_ERROR.getCode(), false, message, data);
    }
}
