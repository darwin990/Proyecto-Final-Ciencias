package com.mycompany.proyectofinalciencias.view;

import com.mycompany.proyectofinalciencias.model.GameEvent;

public interface GameStateObserver {
    void onTreeUpdate();
    void onPlayerStatusUpdate();
    void onEventOccurred(GameEvent event);
    void onCampaignUpdate();
}