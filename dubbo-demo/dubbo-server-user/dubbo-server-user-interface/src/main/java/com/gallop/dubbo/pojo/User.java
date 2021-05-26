package com.gallop.dubbo.pojo;

import lombok.Data;

/**
 * author gallop
 * date 2021-05-24 21:25
 * Description:
 * Modified By:
 */
@Data
public class User implements java.io.Serializable {
    private static final long serialVersionUID = 3222208667750166654L;
    private Long id;
    private String username;
    private String password;
    private Integer age;
}
