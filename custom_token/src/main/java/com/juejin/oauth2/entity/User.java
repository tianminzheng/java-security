package com.juejin.oauth2.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName(value = "system_users", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @TableId
    private Long id;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 备注
     */
    private String remark;
}
