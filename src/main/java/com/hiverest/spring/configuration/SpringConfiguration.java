package com.hiverest.spring.configuration;


import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Created by yann blanc on 3/2/16.
*/

@Configuration
@EnableWebMvc
@Import(SpringBeanConfiguration.class)
public class SpringConfiguration {}
