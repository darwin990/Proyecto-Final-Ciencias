// src/main/java/com/mycompany/proyectofinalciencias/controller/PlayerActionController.java
package com.mycompany.proyectofinalciencias.controller;

import com.mycompany.proyectofinalciencias.model.*;

public class PlayerActionController {

    private final GameSession session;

    public PlayerActionController(GameSession session) {
        this.session = session;
    }

    public String sobornarNodo(String idNodo) {
        CorruptionNode nodo = session.getArbol().findById(idNodo);
        PlayerStatus jugador = session.getJugador();

        if (nodo == null) return "❌ Nodo no encontrado.";
        if (!nodo.isAcceptsBribes()) return "❌ Este nodo no acepta sobornos.";
        if (jugador.getDinero() < nodo.getBribeCost()) return "❌ Fondos insuficientes.";

        jugador.restarDinero(nodo.getBribeCost());
        nodo.setState("activo");
        nodo.setLoyalty((int) (40 + Math.random() * 60));
        nodo.setAmbition((int) (20 + Math.random() * 50));
        nodo.setExposureRisk((int) (10 + Math.random() * 50));

        return "💵 Nodo sobornado exitosamente: " + nodo.getName();
    }

    public String sabotearNodo(String nodeId) {
        boolean ok = session.getJugador().realizarSabotaje(session.getGrafo(), nodeId, 0, 10);
        return ok ? "🧨 Nodo saboteado correctamente." : "❌ No se pudo sabotear el nodo.";
    }

    public String romperConexion(String nodoA, String nodoB) {
        boolean ok = session.getJugador().romperConexion(session.getGrafo(), session.getArbol(), nodoA, nodoB, 20);
        return ok ? "🔌 Conexión eliminada exitosamente." : "❌ Falló la desconexión.";
    }

    public String mejorarLealtad(String idNodo) {
        CorruptionNode nodo = session.getArbol().findById(idNodo);
        if (nodo == null) return "❌ Nodo no encontrado.";
        if (!nodo.getState().equalsIgnoreCase("activo")) return "❌ El nodo debe estar activo.";

        if (session.getJugador().getDinero() < 50)
            return "❌ No hay suficiente dinero para mejorar la lealtad.";

        nodo.setLoyalty(Math.min(100, nodo.getLoyalty() + 10));
        session.getJugador().restarDinero(50);
        return "👍 Lealtad mejorada: " + nodo.getName();
    }
}
