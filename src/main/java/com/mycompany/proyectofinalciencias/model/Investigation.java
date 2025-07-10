package com.mycompany.proyectofinalciencias.model;

public class Investigation {
    private final String targetId;
    private double evidenceGathered;
    private int duration;
    private final double evidenceThreshold;
    private boolean completed;
    
    public Investigation(String targetId, int duration) {
        this.targetId = targetId;
        this.duration = duration;
        this.evidenceGathered = 0;
        this.evidenceThreshold = 100.0;
        this.completed = false;
    }
    
    public boolean advance() {
        if (completed) return false;
        
        duration--;
        evidenceGathered += calculateEvidenceGain();
        
        if (evidenceGathered >= evidenceThreshold || duration <= 0) {
            completed = true;
            return true;
        }
        
        return false;
    }
    
    private double calculateEvidenceGain() {
        return 15 + Math.random() * 10; // Entre 15-25 por turno
    }
    
    public String getTargetId() { return targetId; }
    public double getEvidence() { return evidenceGathered; }
    public int getRemainingDuration() { return duration; }
    public boolean isCompleted() { return completed; }
}