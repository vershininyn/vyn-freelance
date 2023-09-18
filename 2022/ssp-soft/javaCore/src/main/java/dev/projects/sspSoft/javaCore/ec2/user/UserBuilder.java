package dev.projects.sspSoft.javaCore.ec2.user;

import java.util.HashMap;
import java.util.Map;

public class UserBuilder {
    private final Map<Integer, FieldBlock> blockMap = new HashMap<Integer, FieldBlock>() {{
        put(0, new FieldBlock());
        put(1, new FieldBlock());
        put(2, new FieldBlock());
        put(3, new FieldBlock());
    }};

    public UserBuilder buildBlockByIndex(Integer blockIndex, String field0, String field1, String field2, String field3, String field4) {
        FieldBlock block = blockMap.get(blockIndex);

        block.setField0(field0);
        block.setField1(field1);
        block.setField2(field2);
        block.setField3(field3);
        block.setField4(field4);

        return this;
    }

    public User build() {
        User user = new User();

        user.setBlock0(blockMap.get(0));
        user.setBlock1(blockMap.get(1));
        user.setBlock2(blockMap.get(2));
        user.setBlock3(blockMap.get(3));

        return user;
    }
}
