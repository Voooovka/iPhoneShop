package com.javovka.userservice.repo;

import com.javovka.userservice.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders, Long> {

}
