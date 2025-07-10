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
        boolean success = switch(tipoAccion) {
            case "SOBORNAR" -> actionController.sobornarNodo((String)params.get("nodeId"));
            case "SABOTEAR" -> actionController.sabotearNodo((String)params.get("targetId"));
            case "MEJORAR_LEALTAD" -> actionController.mejorarLealtad((String)params.get("nodeId"));
            default -> false;
        };

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