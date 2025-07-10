package com.mycompany.proyectofinalciencias.controller;

import com.mycompany.proyectofinalciencias.model.*;
import java.util.Random; // Importar Random

public class SimpleActionController {
    private final PlayerStatus jugador;
    private final CorruptionTree arbol;
    private final ExternalGraph grafo;
    private final Random random = new Random(); // Agregar Random

    public SimpleActionController(PlayerStatus jugador, CorruptionTree arbol, ExternalGraph grafo) {
        this.jugador = jugador;
        this.arbol = arbol;
        this.grafo = grafo;
    }

    public String recolectarRecursos() {
        // Calcular base de recursos según el árbol
        double baseDinero = arbol.calcularRiquezaTotal();
        double baseInfluencia = arbol.calcularInfluenciaTotal();

        // Aplicar factor aleatorio (entre 60% y 140% del valor base)
        double factorDinero = 0.6 + (random.nextDouble() * 0.8);  // 0.6 a 1.4
        double factorInfluencia = 0.6 + (random.nextDouble() * 0.8);

        double dineroFinal = baseDinero * factorDinero;
        double influenciaFinal = baseInfluencia * factorInfluencia;

        // Generar mensaje con emojis según si fue buena o mala recolección
        String emojiDinero = factorDinero > 1.0 ? "💰" : "💸";
        String emojiInfluencia = factorInfluencia > 1.0 ? "⭐" : "⚡";

        jugador.aumentarDinero(dineroFinal);
        jugador.aumentarInfluencia(influenciaFinal);

        return String.format("%s Obtuviste $%.2f %s y %.2f influencia %s", 
            factorDinero > 1.0 ? "¡Gran recolección!" : "Recolección modesta:",
            dineroFinal,
            emojiDinero,
            influenciaFinal,
            emojiInfluencia);
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

    public String sobornarNodo(String idNodo) {
        CorruptionNode nodo = arbol.findById(idNodo);
        if (nodo == null) return "❌ Nodo no encontrado.";
        if (!nodo.isAcceptsBribes()) return "❌ Este nodo no acepta sobornos.";
        if (jugador.getDinero() < nodo.getBribeCost()) return "❌ Fondos insuficientes.";

        jugador.restarDinero(nodo.getBribeCost());
        nodo.setState("activo");
        return "💰 Nodo sobornado exitosamente: " + nodo.getName();
    }
}
