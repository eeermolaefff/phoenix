package com.phoenix.hermes.common.configuration;

import com.phoenix.common.configuration.CommonConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        CommonConfiguration.class
})
public class HermesConfiguration {
}
