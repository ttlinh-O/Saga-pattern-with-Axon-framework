package com.example.shipmentservice.command.api.events;

import com.example.commonservice.events.ShipOrderEvent;
import com.example.shipmentservice.command.api.data.Shipment;
import com.example.shipmentservice.command.api.repositories.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentsEventHandler {
    private ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentsEventHandler(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @EventHandler
    public void on(ShipOrderEvent event) {
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(event, shipment);
        shipmentRepository.save(shipment);
    }
}
