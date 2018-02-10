package com.ouitech.wdi.tfn.builder.xml.output.surefire;

import java.util.Optional;

public class Event {

    public String state;
    public Optional<String> type;
    public Optional<String> message;

    public Event(String state) {
        this.state = state;
    }

    public Event(String state, Optional<String> type, Optional<String> message) {
        this.state = state;
        this.type = type;
        this.message = message;
    }
}
