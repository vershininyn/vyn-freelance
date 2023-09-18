package dev.projects.sspSoft.javaCore.ec2.user;

import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
public class FieldBlock {
    private String field0 = "";
    private String field1 = "";
    private String field2 = "";
    private String field3 = "";
    private String field4 = "";


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("", "", "");

        return joiner.add(field0)
                .add(field1)
                .add(field2)
                .add(field3)
                .add(field4)
                .toString();
    }
}
