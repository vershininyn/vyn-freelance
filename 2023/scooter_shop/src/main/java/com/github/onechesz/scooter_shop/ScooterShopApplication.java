package com.github.onechesz.scooter_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;

@SpringBootApplication
public class ScooterShopApplication {

    public static void main(String[] args) {
        System.out.println(new DecimalFormat( "###,###.##" ).format(74554542.224463));
        SpringApplication.run(ScooterShopApplication.class, args);

//        +++$2a$10$LodPlq9Xl2gqQ7D1CObw5OYpSAdS.qJ8hXWoefc0q4hLc.q.09eT6+++ user
//        +++$2a$10$chPTHqitengdvmo14yDIKu0qIYcdx0NmNCOHmAS1q.inzPWqvbPbS+++ admin

//        System.out.println("+++" + (new BCryptPasswordEncoder()).encode("user") + "+++");
//        System.out.println("+++" + (new BCryptPasswordEncoder()).encode("admin") + "+++");
    }

}
