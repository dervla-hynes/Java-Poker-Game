package classes;

import lombok.*;

import java.util.*;

@AllArgsConstructor
@Data
@Builder
public class Hand {
    private ArrayList<Card> cards;
    private ArrayList<Integer> potentialScores;

    public void addToPotentialScores(Integer value) {
        potentialScores.add(value);
    }

    public ArrayList<Integer> getCardValues() {
        Map<String, Integer> faceCardMap = new HashMap<>();
        faceCardMap.put("J", 11);
        faceCardMap.put("Q", 12);
        faceCardMap.put("K", 13);
        faceCardMap.put("A", 14);

        ArrayList<Integer> cardValues = new ArrayList<Integer>();

        for (Card card : cards) {
            String value = card.getValue();
            if (value.equals("J") ||
                    value.equals("Q") ||
                    value.equals("K") ||
                    value.equals("A")) {
                cardValues.add(faceCardMap.get(value));
            } else {
                cardValues.add(Integer.parseInt(value));
            }
        }

        return cardValues;
    }

    public ArrayList<String> getCardSuits() {
        ArrayList<String> cardSuits = new ArrayList<String>();
        for (Card card : cards) {
            cardSuits.add(card.getSuit());
        }
        return cardSuits;
    }
}