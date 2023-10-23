package com.example.shipmentservice.command.api.aggregate;

import com.example.commonservice.commands.ShipOrderCommand;
import com.example.commonservice.events.ShipOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class ShipmentAggregate {
    @TargetAggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    public ShipmentAggregate () {}

    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipOrderCommand) {
        // Validate the command
        // Publish th ship commands
        ShipOrderEvent event = ShipOrderEvent.builder()
                .shipmentId(shipOrderCommand.getShipmentId())
                .orderId(shipOrderCommand.getOrderId())
                .shipmentStatus("COMPLETED")
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ShipOrderEvent shipOrderEvent) {
        this.orderId = shipOrderEvent.getOrderId();
        this.shipmentId = shipOrderEvent.getShipmentId();
        this.shipmentStatus = shipOrderEvent.getShipmentStatus();

    }
}
