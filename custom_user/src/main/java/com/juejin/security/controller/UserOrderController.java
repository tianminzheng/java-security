package com.juejin.security.controller;

import com.juejin.security.domain.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import com.juejin.security.service.UserOrderService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserOrderController {

    @Autowired
    private UserOrderService orderService;
    
    @GetMapping("/orders")
    public List<UserOrder> order(Authentication authentication) {
    	String username = authentication.getName();
        List<UserOrder> orders = orderService.getOrdersByUsername(username);
        return orders;
    }
}
