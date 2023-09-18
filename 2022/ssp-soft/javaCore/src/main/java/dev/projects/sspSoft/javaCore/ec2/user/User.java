package dev.projects.sspSoft.javaCore.ec2.user;

import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
public class User {
    private FieldBlock block0 = null,
            block1 = null,
            block2 = null,
            block3 = null;


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "", "");

        return joiner.add(block0.toString())
                .add(block1.toString())
                .add(block2.toString())
                .add(block3.toString())
                .toString();
    }
}
