package com.juejin.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientStatus {

    ENABLE(1, "开启"),
    DISABLE(0, "关闭");

    private final Integer status;

    private final String name;

}