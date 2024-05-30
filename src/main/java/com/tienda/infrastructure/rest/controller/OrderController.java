package com.tienda.infrastructure.rest.controller;

import com.tienda.application.service.IOrderService;
import com.tienda.domain.model.Order;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> getOrders() {
        return new ResponseEntity<>(this.orderService.getOrders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order){
        // TODO: RETURN 200 when update
        return new ResponseEntity<>(orderService.saveOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        return new ResponseEntity<>(this.orderService.getOrderById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        this.orderService.deleteOrder(id);
    }
}
