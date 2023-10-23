package com.example.oderservice.command.api.saga;

import com.example.commonservice.commands.CompleteOrderCommand;
import com.example.commonservice.commands.ShipOrderCommand;
import com.example.commonservice.commands.ValidatePaymentCommand;
import com.example.commonservice.events.OrderCompletedEvent;
import com.example.commonservice.events.PaymentProcessedEvent;
import com.example.commonservice.events.ShipOrderEvent;
import com.example.commonservice.models.User;
import com.example.commonservice.queries.GetUserPaymentDetailQuery;
import com.example.oderservice.command.api.events.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
@AllArgsConstructor
public class OrderProcessingSaga {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    protected OrderProcessingSaga() {
        // For Axon instantiation
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga for OrderId: {}", event.getOrderId());

        GetUserPaymentDetailQuery getUserPaymentDetailQuery = new GetUserPaymentDetailQuery(event.getUserId());
        User user = null;

        try {
            user = queryGateway.query(
                    getUserPaymentDetailQuery,
                    ResponseTypes.instanceOf(User.class)
            ).join();
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensating transaction
        }

        if (user != null) {
            String string = UUID.randomUUID().toString();
            ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                    .cardDetails(user.getCardDetails())
                    .orderId(event.getOrderId())
                    .paymentId(string)
                    .build();
            commandGateway.sendAndWait(validatePaymentCommand);
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(PaymentProcessedEvent event) {
        try {
            log.info("PaymentProcessEvent in Saga for OrderId: {}", event.getOrderId());
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();
            commandGateway.sendAndWait(shipOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensate transaction
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ShipOrderEvent event) {
        log.info("ShipOrderEvent in Saga for OrderId: {}", event.getOrderId());
        try {
            CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
                    .orderId(event.getOrderId())
                    .orderStatus("APPROVED")
                    .build();
            commandGateway.sendAndWait(completeOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensate transaction
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCompletedEvent event) {
        log.info("OrderCompletedEvent in Saga for OrderId: {}", event.getOrderId());

    }

}
