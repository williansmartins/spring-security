package br.com.impacta.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@EnableWebMvc //mvc:annotation-driven
@Configuration
@ComponentScan({"br.com.impacta.controller"})
@Import({ br.com.impacta.config.SecurityConfig.class })
public class WebConfig extends  WebMvcConfigurerAdapter {

}

