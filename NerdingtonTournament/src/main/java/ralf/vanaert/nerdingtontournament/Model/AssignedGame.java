package ralf.vanaert.nerdingtontournament.Model;

import java.util.List;

public record AssignedGame(
        Game game,
        List<Player> players
) {
}
