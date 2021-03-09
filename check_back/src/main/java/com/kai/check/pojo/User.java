package com.kai.check.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kai.check.config.security.component.CustomAuthorityDeserializer;
import com.kai.check.config.security.component.CustomUrlDecisionManager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author kai
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    // id 需要加TableId 注解
    @ApiModelProperty(value = "用户名")
    @TableId(value = "username")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;


    @ApiModelProperty(value = "3学生,2老师,1管理员")
    private Integer userRoleId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    private String userRole;

    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = null;
        switch (userRoleId) {
            case 1:
                authority = new SimpleGrantedAuthority("管理员");
                break;
            case 2:
                authority = new SimpleGrantedAuthority("教师");
                break;
            case 3:
                authority = new SimpleGrantedAuthority("学生");
                break;
            default:
                break;
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
