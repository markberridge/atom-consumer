package com.learndirect.resultsportal.event;

public enum EventType {
    
    RESULT("Result");

    private String label;

    public String getLabel() {
        return label;
    }

    private EventType(String label) {
        this.label = label;
    }
}
