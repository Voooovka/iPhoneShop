package com.javovka.userservice.api;

import com.javovka.userservice.dto.OrderDto;
import com.javovka.userservice.models.Orders;
import com.javovka.userservice.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping
    public ResponseEntity<List<Orders>> getOrders(){
        return ResponseEntity.ok().body(ordersService.getAllOrders());
    }

    @PostMapping
    public void saveOrder(@RequestBody OrderDto order, @RequestHeader("Authorization") String header) {
        ordersService.saveOrder(order, header);
    }

}
