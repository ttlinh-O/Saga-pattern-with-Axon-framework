package com.example.paymentservice.command.api.aggregate;

import com.example.commonservice.commands.ValidatePaymentCommand;
import com.example.commonservice.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate() {}

    @CommandHandler
    public  PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
            // Validate the payment details
            // Publish the Payment Processed event
            log.info("Executing ValidatePaymentCommand for OrderId: {} and PaymentId: {}",
                    validatePaymentCommand.getPaymentId(),
                    validatePaymentCommand.getOrderId());

            PaymentProcessedEvent paymentProcessEvent = new PaymentProcessedEvent(validatePaymentCommand.getPaymentId(),
                    validatePaymentCommand.getOrderId());
            AggregateLifecycle.apply(paymentProcessEvent);
            log.info("PaymentProcessEvent Applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
         this.paymentId = event.getPaymentId();
         this.orderId = event.getOrderId();
    }
}
