package com.gallop.springboot.async.dao;

import org.springframework.stereotype.Repository;

/**
 * author gallop
 * date 2021-03-26 14:53
 * Description:
 * Modified By:
 */
@Repository
public class MessageDao {
    public String getMessage(String s) {
        return s;
    }

    public String callBackMessage(String s) {
        return "这是注册回调返回结果s=" + s;
    }

    public String getUserCode(int id) {
        return "000" + id;
    }

    public String getUserName(String code) {
        return "李四";
    }

    public String getUserDepartment(String code) {
        return "技术开发部";
    }
}
