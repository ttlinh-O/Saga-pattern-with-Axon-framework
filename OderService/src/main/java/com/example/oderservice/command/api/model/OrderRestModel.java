package com.example.oderservice.command.api.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRestModel {

    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
}
