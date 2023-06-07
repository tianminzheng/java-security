package com.juejin.security.service.impl;

import com.juejin.security.domain.UserOrder;
import com.juejin.security.repository.UserOrderRepository;
import com.juejin.security.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private UserOrderRepository orderRepository;

    @Override
    public List<UserOrder> getOrdersByUsername(String username) {
    	
        return orderRepository.findUserOrdersByUsername(username);
    }
}
