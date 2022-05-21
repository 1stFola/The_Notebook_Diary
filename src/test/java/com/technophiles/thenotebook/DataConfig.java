package com.technophiles.thenotebook;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableAutoConfiguration
@Configuration
@EnableJpaRepositories(basePackages = {"com.technophiles.thenotebook"})
public class DataConfig {

}