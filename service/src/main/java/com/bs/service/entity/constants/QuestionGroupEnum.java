package com.bs.service.entity.constants;


public enum QuestionGroupEnum {
    READ_QUESTION("历年真题", 1),
    SIMULATION("仿真模拟", 2),
    SPECIAL("专项练习", 3),
    GENERAL("一般练习", 4);

    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private QuestionGroupEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
