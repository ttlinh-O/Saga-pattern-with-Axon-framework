package com.example.userservice.projections;

import com.example.commonservice.models.CardDetails;
import com.example.commonservice.models.User;
import com.example.commonservice.queries.GetUserPaymentDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetail(GetUserPaymentDetailQuery request) {
        CardDetails cardDetails = CardDetails.builder()
                .name("TTlinh")
                .validUntilYear(2022)
                .validUntilYear(01)
                .cardNumber("123456789")
                .cvv(111)
                .build();

        return User.builder()
                .userId(request.getUserId())
                .firstName("TT")
                .lastName("Linh")
                .cardDetails(cardDetails)
                .build();
    }

}
