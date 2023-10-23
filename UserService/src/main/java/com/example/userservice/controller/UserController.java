package com.example.userservice.controller;

import com.example.commonservice.models.User;
import com.example.commonservice.queries.GetUserPaymentDetailQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final QueryGateway queryGateway;

    public UserController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{user-id}")
    public User getUser(@PathVariable(name = "user-id") String userId) {
        GetUserPaymentDetailQuery query = new GetUserPaymentDetailQuery(userId);
        return queryGateway.query(query, ResponseTypes.instanceOf(User.class)).join();
    }
}
