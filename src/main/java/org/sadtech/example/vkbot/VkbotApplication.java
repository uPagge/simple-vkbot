package org.sadtech.example.vkbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.sadtech.vk.bot", "org.sadtech.example.vkbot"})
public class VkbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(VkbotApplication.class, args);
    }

}
