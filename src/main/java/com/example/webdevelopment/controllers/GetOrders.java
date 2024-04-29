package com.example.webdevelopment.controllers;

import com.example.webdevelopment.DTO.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class GetOrders {

    List<OrderDetails> orderDetailsList = new ArrayList<>();

    @GetMapping("/getorder/")
    public List<OrderDetails> getOrderDetailsList() {
//        sou
        return orderDetailsList;
    }

    @PostMapping("/")
    public OrderDetails getOrderDetailsList(@RequestBody OrderDetails orderDetails) {
        orderDetailsList.add(orderDetails);
        sendOrder(orderDetails);
        return orderDetails;
    }

    @GetMapping("/{index}")
    public OrderDetails getOrderDetailsofIndex(@PathVariable int index) {
        if ((orderDetailsList.size() + 1) < index) {
            throw new RuntimeException("Index should be lesser then size of data");
        }
        return orderDetailsList.get(index-1);
    }

    @Autowired
    private KafkaTemplate<String, OrderDetails> kafkaTemplate;

    public void sendOrder(OrderDetails order) {
        kafkaTemplate.send("ordersss", order.getOrderId(), order);
    }

    @GetMapping("/")
    public String getMetadataofOrder(@RequestParam String getMetadataOrder) {
//        if ((orderDetailsList.size() + 1) < index) {
//            throw new RuntimeException("Index should be lesser then size of data");
//        }
//        return orderDetailsList.get(index-1);
        return "";
    }

}
