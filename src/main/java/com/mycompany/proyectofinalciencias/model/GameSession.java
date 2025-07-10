package com.mycompany.proyectofinalciencias.model;

import com.mycompany.proyectofinalciencias.controller.CampaignController;
import com.mycompany.proyectofinalciencias.view.GameStateObserver;  // Asegúrate que esta importación esté presente
import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private CorruptionTree arbol;
    private ExternalGraph grafo;
    private PlayerStatus jugador;
    private EventSystem eventos;
    private Campaign campaign;
    private CampaignController campaignController;
    private List<GameStateObserver> observers;  // Declara la lista
    private MoneyLaunderingSystem launderingSystem;
    private JusticeSystem justiceSystem;

    public GameSession(CorruptionTree arbol, ExternalGraph grafo, PlayerStatus jugador,
                       EventSystem eventos, Campaign campaign) {
        this.arbol = arbol;
        this.grafo = grafo;
        this.jugador = jugador;
        this.eventos = eventos;
        this.campaign = campaign;
        this.campaignController = new CampaignController(campaign);
        this.observers = new ArrayList<>();  // Inicializa la lista
        this.launderingSystem = new MoneyLaunderingSystem(this);
        this.justiceSystem = new JusticeSystem(this);
    }

    public CorruptionTree getArbol() {
        return arbol;
    }

    public ExternalGraph getGrafo() {
        return grafo;
    }

    public PlayerStatus getJugador() {
        return jugador;
    }

    public EventSystem getEventos() {
        return eventos;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public CampaignController getCampaignController() {
        return campaignController;
    }

    public void addObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    public void notifyTreeUpdate() {
        for (GameStateObserver observer : observers) {
            observer.onTreeUpdate();
        }
    }

    public void notifyPlayerUpdate() {
        for (GameStateObserver observer : observers) {
            observer.onPlayerStatusUpdate();
        }
    }

    public void notifyEvent(GameEvent event) {
        for (GameStateObserver observer : observers) {
            observer.onEventOccurred(event);
        }
    }

    public void notifyCampaignUpdate() {
        for (GameStateObserver observer : observers) {
            observer.onCampaignUpdate();
        }
    }

    public MoneyLaunderingSystem getLaunderingSystem() {
        return launderingSystem;
    }

    public JusticeSystem getJusticeSystem() {
        return justiceSystem;
    }
}
