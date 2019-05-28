package com.o2o.action.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.o2o.action.server.db.UserEntity;
import com.o2o.action.server.db.UserEntity.UserRole;
import com.o2o.action.server.repo.UserRepository;

@ServletComponentScan
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        UserRepository userRepository = context.getBean(UserRepository.class);
        userRepository.save(new UserEntity("강성욱", 55, UserRole.ADMIN));
        UserEntity user = userRepository.findByUserName("강성욱");
        System.out.println("나이 : " + user.getAge() + ", " 
        + "이름: " + user.getUserName()
        + "생성일: " + user.getCreatedAt());
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}