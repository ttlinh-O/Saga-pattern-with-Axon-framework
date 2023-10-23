package com.example.commonservice.config;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(allowTypesByWildcard());
        return xStream;
    }

    protected String[] allowTypesByWildcard() {
        return new String[] {
                "com.example.commonservice.**"
        };
    }
}
