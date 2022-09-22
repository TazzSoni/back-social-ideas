package com.backsocialideas.dto.enums;

public enum Stage {
    POSTED("postado"),
    REVIEWING("revisando"),
    DONE("acabado");

    private final String value;

    Stage(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
