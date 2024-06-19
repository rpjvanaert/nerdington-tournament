package ralf.vanaert.nerdingtontournament.Model;

import java.util.List;

public record Pairing(
        List<Player> players,
        Game game
) {
}
