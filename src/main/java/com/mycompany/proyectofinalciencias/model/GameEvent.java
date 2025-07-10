package com.mycompany.proyectofinalciencias.model;

public class GameEvent {
    private final String type;
    private final String message;
    private final EventSeverity severity;

    public GameEvent(String type, String message, EventSeverity severity) {
        this.type = type;
        this.message = message;
        this.severity = severity;
    }

    public String getType() { return type; }
    public String getMessage() { return message; }
    public EventSeverity getSeverity() { return severity; }

    public enum EventSeverity {
        INFO, WARNING, CRITICAL
    }
}