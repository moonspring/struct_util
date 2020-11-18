package com.lzq.struct.enums;

public enum TagEnum {

    delTag("<old>","del-diffrence"),
    addTag("<new>","add-diffrence");

    private String tag;
    private String css;

    TagEnum(String tag, String css){
        this.tag = tag;
        this.css = css;
    }

    public String getTag() {
        return tag;
    }

    public String getCss() {
        return css;
    }
}
