package com.juejin.security.service;

import java.util.List;

import com.juejin.security.domain.UserOrder;

public interface UserOrderService {

    List<UserOrder> getOrdersByUsername(String username);
}
