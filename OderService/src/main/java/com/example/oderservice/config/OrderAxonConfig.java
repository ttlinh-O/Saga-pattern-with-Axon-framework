package com.example.oderservice.config;

import com.example.commonservice.config.AxonConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderAxonConfig extends AxonConfig {

    @Override
    protected String[] allowTypesByWildcard() {
        return new String[] {
                "com.example.commonservice.**",
                "com.example.oderservice.**"
        };
    }
}
