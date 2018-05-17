package com.common.Controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;


@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.common.mapper")
public class Application extends SpringBootServletInitializer  {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
    public static void main( String[] args )
    {
    	SpringApplication.run(Application.class, args);
    }
	 
}
