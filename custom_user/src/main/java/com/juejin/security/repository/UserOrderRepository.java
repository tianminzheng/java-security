package com.juejin.security.repository;

import java.util.List;

import com.juejin.security.domain.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

	List<UserOrder> findUserOrdersByUsername(String username);
}
