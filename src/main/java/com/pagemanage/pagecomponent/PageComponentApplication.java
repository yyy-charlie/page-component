package com.pagemanage.pagecomponent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan({"com.pagemanage.pagecomponent.mapper","com.bocontainer.mapper"})
@ComponentScan(basePackages={"com.bocontainer","com.container.treecontainer", "com.pagemanage.pagecomponent"})
public class PageComponentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PageComponentApplication.class, args);
    }

}
