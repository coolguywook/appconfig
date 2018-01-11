package com.rbc.app.common;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

 
@Configuration
public class TestContextCommons {
 
	@Bean
    public ResourceBundleMessageSource messageSource() {
     ResourceBundleMessageSource source = new ResourceBundleMessageSource();
     source.setBasenames("messages/messages"); // name of the resource bundles
     source.setUseCodeAsDefaultMessage(true);
     return source;
    }
  
}