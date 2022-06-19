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
        Game game = new Game(configMap, commentaryMap);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int noOfPlays = Integer.parseInt(bufferedReader.readLine());
        while (noOfPlays-- > 0) {
            String[] play = bufferedReader.readLine().split(" ");
            String outcome = game.computeProbabilisticOutcome(play[0], play[1], play[2]);
            System.out.println(outcome);
        }
    }
}
