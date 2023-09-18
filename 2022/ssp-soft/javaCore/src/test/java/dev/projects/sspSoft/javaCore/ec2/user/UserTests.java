package dev.projects.sspSoft.javaCore.ec2.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTests {
    /**
     * Создать класс Пользователь с большим кол-вом полей (20, на свой выбор). Реализовать для данного класса Builder;
     */
    @Test
    public void userTest() {
        User user = (new UserBuilder())
                .buildBlockByIndex(0, "1", "2", "3", "4", "5")
                .buildBlockByIndex(1, "6", "7", "8", "9", "10")
                .buildBlockByIndex(2, "11", "12", "13", "14", "15")
                .buildBlockByIndex(3, "16", "17", "18", "19", "20")
                .build();

        Assertions.assertEquals("12345,678910,1112131415,1617181920", user.toString());
    }

}
