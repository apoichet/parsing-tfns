package com.ouitech.wdi.tfn.builder.xml.output.surefire;

public class Cause {

    private String type;
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Cause(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
