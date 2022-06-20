package com.everest.cricket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuperOver extends Game {
    private static final int MAX_SUPER_OVER_SCORE = 36;

    public SuperOver(Map<Integer, List<Integer>> configMap, Map<Integer, List<String>> commentaryMap) {
        super(configMap, commentaryMap);
    }

    public List<String> initializeSuperOver() {
        return randomlySelectSixBowlingCards();
    }

    public int initializeFirstInningsScore() {
        return this.getRandomInRange(0, MAX_SUPER_OVER_SCORE + 1);
    }

    private List<String> randomlySelectSixBowlingCards() {
        List<String> bowlingCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            bowlingCards.add(Cards.BOWLING_CARDS.get(this.getRandomInRange(0, Cards.BOWLING_CARDS.size())));
        }
        return bowlingCards;
    }
}
