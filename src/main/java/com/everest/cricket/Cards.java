package com.everest.cricket;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cards {
    public static final List<String> BOWLING_CARDS = new ArrayList<>(List.of("bouncer", "out-swinger", "off-cutter", "yorker", "off-break", "inswinger", "leg-cutter", "slower-ball", "pace", "doosra"));
    public static final List<String> BATTING_CARDS = new ArrayList<>(List.of("straight", "flick", "leglance", "long-on", "square-cut", "sweep", "cover-drive", "pull", "scoop", "uppercut"));
    public static final List<String> SHOT_TIMING = new ArrayList<>(List.of("perfect", "good", "early", "late"));

    public static Map<Integer, List<Integer>> parseConfig(){
        Yaml yaml = new Yaml();
        InputStream inputStream = Cards.class
                .getClassLoader()
                .getResourceAsStream("config.yaml");
        return yaml.load(inputStream);
    }
}
