package com.everest.cricket;

import com.everest.cricket.util.RandomUtil;

import java.util.*;

public class Game {
    private static final List<List<Integer>> scores = new ArrayList<>(List.of(List.of(-1), List.of(0), List.of(1, 2), List.of(3, 5), List.of(4, 6)));
    private static final int maxMultiple = Cards.BATTING_CARDS.size() * Cards.SHOT_TIMING.size();
    public static final Random random = new Random();
    private final Map<Integer, List<Integer>> configMap;
    private final Map<Integer, List<String>> commentaryMap;
    private final Map<Integer, List<Integer>> multipleToScoreMap;

    public Game(Map<Integer, List<Integer>> configMap, Map<Integer, List<String>> commentaryMap) {
        this.configMap = configMap;
        this.commentaryMap = commentaryMap;
        this.multipleToScoreMap = new TreeMap<>();
        for (int i = 1; i <= scores.size(); i++) {
            multipleToScoreMap.put((maxMultiple / scores.size()) * i, scores.get(i - 1));
        }
    }

    public String computeProbabilisticOutcome(String bowlType, String batType, String timing) {
        int bowlIndex = Cards.BOWLING_CARDS.indexOf(bowlType.toLowerCase());
        if (bowlIndex == -1) {
            return "Invalid Bowling Type. Allowed types are: " + Cards.BOWLING_CARDS;
        }
        int batIndex = Cards.BATTING_CARDS.indexOf(batType.toLowerCase());
        if (batIndex == -1) {
            return "Invalid Batting Type. Allowed types are: " + Cards.BATTING_CARDS;
        }
        int timingIndex = Cards.SHOT_TIMING.indexOf(timing.toLowerCase());
        if (timingIndex == -1) {
            return "Invalid Timing. Allowed timings are: " + Cards.SHOT_TIMING;
        }
        int multiple = getMultiple(bowlIndex, batIndex, timingIndex);
        List<Integer> mostProbableScores = calculateProbableScoreForMultiple(multiple);
        int finalScore = randomlySelectScoreFromMostProbableScores(mostProbableScores);
        String commentary = randomlySelectCommentary(finalScore);
        return parseFinalScore(finalScore, commentary);
    }

    private String randomlySelectCommentary(int finalScore) {
        List<String> commentary = this.commentaryMap.get(finalScore);
        return commentary.get(getRandomInRange(0, commentary.size()));
    }

    private String parseFinalScore(int finalScore, String commentary) {
        if (finalScore == -1) {
            return "1 wicket, " + commentary;
        } else {
            return finalScore + " runs, " + commentary;
        }
    }

    private int randomlySelectScoreFromMostProbableScores(List<Integer> mostProbableScores) {
        if (mostProbableScores.size() == 1) {
            return mostProbableScores.get(0);
        }
        return mostProbableScores.get(getRandomInRange(0, mostProbableScores.size()));
    }

    private List<Integer> calculateProbableScoreForMultiple(int multiple) {
        List<Integer> keys = new ArrayList<>(this.multipleToScoreMap.keySet());
        int maxWeight = scores.size() * 2;
        int multipleKey = 0;
        for (int i = 0; i < keys.size(); i++) {
            if (multiple > keys.get(i)) {
                multipleKey = i + 1;
            }
        }
        RandomUtil random = new RandomUtil();
        random.add(multipleKey, maxWeight);
        int maxWeightCopy = maxWeight - 2;
        for (int i = multipleKey - 1; i >= 0; i--, maxWeightCopy -= 2) {
            random.add(i, maxWeightCopy);
        }
        maxWeightCopy = maxWeight - 2;
        for (int i = multipleKey + 1; i < keys.size(); i++, maxWeightCopy -= 2) {
            random.add(i, maxWeightCopy);
        }
        return this.multipleToScoreMap.get(keys.get(random.getOneRandom()));
    }

    /**
     * This will return number by multiplying batting_index with timing_index
     **/
    private int getMultiple(int bowlIndex, int batIndex, int timingIndex) {
        List<Integer> preferredBattingIndexes = this.configMap.get(bowlIndex);
        return calculateBattingIndex(batIndex, preferredBattingIndexes) * calculateTimingIndex(timingIndex);
    }

    private int calculateTimingIndex(int timingIndex) {
        return Cards.SHOT_TIMING.size() - timingIndex;
    }

    private int calculateBattingIndex(int batIndex, List<Integer> preferredBattingIndexes) {
        return Cards.BATTING_CARDS.size() - preferredBattingIndexes.indexOf(batIndex);
    }

    public int getRandomInRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
