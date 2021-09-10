package classes;

import lombok.*;

import java.util.*;

@AllArgsConstructor
@Data
@Builder
public class Hand {
    private ArrayList<String> cards;
    private ArrayList<Integer> potentialScores;

    public void addToPotentialScores(Integer value) {
        potentialScores.add(value);
    }
}