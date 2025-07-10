package com.mycompany.proyectofinalciencias.controller;

import com.mycompany.proyectofinalciencias.model.*;

public class SimpleActionController {

    private final PlayerStatus jugador;
    private final CorruptionTree arbol;
    private final ExternalGraph grafo;

    public SimpleActionController(PlayerStatus jugador, CorruptionTree arbol, ExternalGraph grafo) {
        this.jugador = jugador;
        this.arbol = arbol;
        this.grafo = grafo;
    }

    public String recolectarRecursos() {
        double totalDinero = arbol.calcularRiquezaTotal();
        double totalInfluencia = arbol.calcularInfluenciaTotal();
        jugador.aumentarDinero(totalDinero);
        jugador.aumentarInfluencia(totalInfluencia);
        return "💰 Obtuviste $" + totalDinero + " y +" + totalInfluencia + " influencia.";
    }

    public String mejorarLealtad(String id) {
        CorruptionNode nodo = arbol.findById(id);
        if (nodo == null) return "❌ Nodo no encontrado.";
        if (!nodo.getState().equals("activo")) return "❌ El nodo debe estar activo.";

        nodo.setLoyalty(Math.min(100, nodo.getLoyalty() + 15));
        return "✅ Lealtad de " + nodo.getName() + " aumentada a " + nodo.getLoyalty();
    }

    public String agregarNodo(String idPadre, CorruptionNode nuevo) {
        CorruptionNode padre = arbol.findById(idPadre);
        if (padre == null) return "❌ Nodo padre no encontrado.";
        if (jugador.getDinero() < nuevo.getBribeCost()) return "❌ Dinero insuficiente.";

        if (arbol.insert(idPadre, nuevo)) {
            jugador.restarDinero(nuevo.getBribeCost());
            return "🌱 Nodo agregado: " + nuevo.getName();
        } else {
            return "❌ Falló al insertar el nodo.";
        }
    }

    public String encubrir() {
        boolean ok = jugador.realizarEncubrimiento(arbol, 50);
        return ok ? "✅ Encubrimiento exitoso." : "❌ No se pudo realizar encubrimiento.";
    }

    public String sabotear(String id) {
        boolean ok = jugador.realizarSabotaje(grafo, id, 0, 5);
        return ok ? "💥 Sabotaje exitoso a " + id : "❌ Sabotaje fallido.";
    }

    public String romperConexion(String a, String b) {
        boolean ok = jugador.romperConexion(grafo, arbol, a, b, 10);
        return ok ? "🔌 Conexión entre " + a + " y " + b + " eliminada." : "❌ Falló la operación.";
    }
}
