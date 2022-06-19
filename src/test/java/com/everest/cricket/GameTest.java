package com.everest.cricket;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {
    @ParameterizedTest
    @CsvSource({
            "bouncer, pull, perfect, 4, 6",
            "bouncer, sweep, late, -1, -2",
            "bouncer, scoop, early, -1, -2"
    })
    public void should_verify_most_probabilistic_outcome(String bowlType, String batType, String timing, int expectedScore1, int expectedScore2) {
        Game game = new Game(Cards.parseConfig(), Cards.parseCommentaryConfig());
        int totalIterations = 10000;
        int actualCount = 0;
        for (int i = 0; i < totalIterations; i++) {
            String outcome = game.computeProbabilisticOutcome(bowlType, batType, timing);
            int score = getScoreFromOutcome(outcome);
            if (expectedScore1 == score || expectedScore2 == score) {
                actualCount++;
            }
        }
        assertTrue((actualCount / (double) totalIterations) > 0.25);
    }

    private int getScoreFromOutcome(String outcome) {
        if (outcome.startsWith("1 wicket")) {
            return -1;
        } else {
            return Integer.parseInt(outcome.substring(0, 1));
        }
    }
}
