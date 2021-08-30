package com.gallop.file.security.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * author gallop
 * date 2021-08-24 15:00
 * Description: 用来包装一下角色名称
 * Modified By:
 */
@Data
@AllArgsConstructor
@Deprecated
public class MyAuthority implements GrantedAuthority {
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
