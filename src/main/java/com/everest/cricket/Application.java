package com.everest.cricket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) throws IOException {
        Map<Integer, List<Integer>> configMap = Cards.parseConfig();
        Map<Integer, List<String>> commentaryMap = Cards.parseCommentaryConfig();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter '1' for normal play and '2' for Super Over");
        int playType = Integer.parseInt(bufferedReader.readLine());
        if (playType == 1) {
            Game game = new Game(configMap, commentaryMap);
            System.out.println("Enter No. of inputs");
            int noOfPlays = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Enter inputs in format: 'bowl-type, bat-type, timing'");
            while (noOfPlays > 0) {
                String[] play = bufferedReader.readLine().split(" ");
                String outcome = game.computeProbabilisticOutcome(play[0], play[1], play[2]);
                System.out.println(outcome);
                noOfPlays = noOfPlays - 1;
            }
        } else if (playType == 2) {
            SuperOver superOver = new SuperOver(configMap, commentaryMap);
            List<String> selectedBowlingCards = superOver.initializeSuperOver();
            System.out.println("Bowling cards: " + selectedBowlingCards);
            int firstInningsScore = superOver.initializeFirstInningsScore();
            int target = firstInningsScore + 1;
            int wicketsAvailable = 2;
            System.out.println("India 11 Score:");
            System.out.println(firstInningsScore + " runs (Target Runs: " + target + " )");
            System.out.println("Wickets Available: " + wicketsAvailable);
            int ballsLeft = selectedBowlingCards.size();
            int australiaScore = 0;
            for (String bowlingCard : selectedBowlingCards) {
                --ballsLeft;
                System.out.println("Enter inputs in format: 'bat-type, timing'");
                String[] play = bufferedReader.readLine().split(" ");
                String outcome = superOver.computeProbabilisticOutcome(bowlingCard, play[0], play[1]);
                System.out.println(outcome);
                int score = getScoreFromOutcome(outcome);
                if (score == -1) {
                    --wicketsAvailable;
                } else {
                    target -= score;
                    australiaScore += score;
                }
                if (checkIfWonOrLost(target, wicketsAvailable, ballsLeft)) {
                    System.out.println("Australia scored: " + australiaScore + " runs");
                    break;
                }
            }
        } else {
            System.out.println("Invalid input. Please restart");
        }
    }

    private static boolean checkIfWonOrLost(int target, int wicketsAvailable, int ballsLeft) {
        if (wicketsAvailable == 0 && target > 0) {
            System.out.println("Australia is all out, and lost by: " + target + " runs");
            return true;
        } else if (target > 0 && ballsLeft == 0) {
            System.out.println("Australia lost by: " + target + " runs");
            return true;
        } else if (target <= 0) {
            System.out.println("Australia won by " + wicketsAvailable + " wicket");
            return true;
        }
        return false;
    }

    private static int getScoreFromOutcome(String outcome) {
        if (outcome.startsWith("1 wicket")) {
            return -1;
        } else {
            return Integer.parseInt(outcome.substring(0, 1));
        }
    }
}
