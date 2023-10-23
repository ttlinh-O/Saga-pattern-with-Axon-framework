package com.example.commonservice.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipOrderEvent {
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;
}
