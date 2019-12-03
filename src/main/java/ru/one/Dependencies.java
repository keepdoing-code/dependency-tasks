package ru.one;

import java.util.*;

public class Dependencies {

    private static HashMap<String, Set<String>> dep = new HashMap<>();

    public static void main(String[] args) {
        dep.put("1", new HashSet<>(Arrays.asList("2","3")));
        dep.put("2", new HashSet<>(Collections.singleton("4")));
        dep.put("3", new HashSet<>(Collections.singleton("5")));
        dep.put("4", new HashSet<>(Collections.singleton("5")));
    }

    public static void findLoop(HashMap<String, List<String>> dep){

    }
}
