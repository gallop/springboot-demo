package com.gallop.redis.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * author gallop
 * date 2021-06-04 9:44
 * Description:
 * Modified By:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private Integer age;
    private Date createTime;

}
