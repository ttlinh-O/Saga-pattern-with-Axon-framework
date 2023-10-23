package com.example.oderservice.command.api.events;

import com.example.commonservice.events.OrderCompletedEvent;
import com.example.oderservice.command.api.data.Order;
import com.example.oderservice.command.api.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OderEventHandler {

    @Autowired
    private OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent event){
        log.error("Order created event in sage for orderId: {}", event.getOrderId());
        Order order = new Order();
        BeanUtils.copyProperties(event, order);
        this.orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        log.error("Order completed event in sage for orderId: {}", event.getOrderId());
        Order order = orderRepository.findById(event.getOrderId()).get();
        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);
    }
}
