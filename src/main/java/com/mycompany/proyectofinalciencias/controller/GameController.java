package com.mycompany.proyectofinalciencias.controller;

import com.mycompany.proyectofinalciencias.controller.PlayerActionController;
import com.mycompany.proyectofinalciencias.controller.TurnController;
import com.mycompany.proyectofinalciencias.model.GameEvent;
import com.mycompany.proyectofinalciencias.model.GameSession;

import java.util.Map;

public class GameController {
    private final GameSession session;
    private final TurnController turnController;
    private final PlayerActionController actionController;

    public GameController(GameSession session) {
        this.session = session;
        this.turnController = new TurnController(session);
        this.actionController = new PlayerActionController(session);
    }

    public void ejecutarAccion(String tipoAccion, Map<String, Object> params) {
        String resultado = "";
        
        switch(tipoAccion) {
            case "SOBORNAR":
                resultado = actionController.sobornarNodo((String)params.get("nodeId"));
                break;
            case "SABOTEAR":
                resultado = actionController.sabotearNodo((String)params.get("targetId"));
                break;
            case "MEJORAR_LEALTAD":
                resultado = actionController.mejorarLealtad((String)params.get("nodeId"));
                break;
            default:
                resultado = "Acción no reconocida";
                break;
        }

        // Notificar el resultado como un evento
        session.notifyEvent(new GameEvent("ACCION", resultado, GameEvent.EventSeverity.INFO));
        
        // Actualizar vistas
        session.notifyTreeUpdate();
        session.notifyPlayerUpdate();
    }

    public void iniciarTurno() {
        turnController.ejecutarTurno();
        session.notifyTreeUpdate();
        session.notifyPlayerUpdate();
        session.notifyCampaignUpdate();
    }
}