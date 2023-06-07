package com.juejin.oauth2.utils;

import lombok.Data;

@Data
public abstract class BaseReq {

    private String traceId;

    private Integer pageNum = 1;

    private Integer pageSize = 10;


}
