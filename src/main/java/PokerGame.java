import classes.*;
import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
public class PokerGame {
    private Hand hand;

    public Card highestCard() {
        ArrayList<Integer> cardValues = hand.getCardValues();
        int maxValue = cardValues.stream().max(Integer::compare).get();
        int maxCardIndex = cardValues.indexOf(maxValue);
        Card bestCard = hand.getCards().get(maxCardIndex);
        hand.addToPotentialScores(maxValue);
        return bestCard;
    }

    public ArrayList<Card> onePairPresent(ArrayList<Card> handInput) {
        ArrayList<Card> pairReturned = new ArrayList<>();
        int score = 0;
        ArrayList<Integer> cardValues = hand.getCardValues();
        for (Integer cardValue : cardValues) {
            if (Collections.frequency(cardValues, cardValue) == 2) {
                score = cardValue * 10;
                pairReturned.add(handInput.get(cardValues.indexOf(cardValue)));
                pairReturned.add(handInput.get(cardValues.lastIndexOf(cardValue)));
                break;
            }
        }
        hand.addToPotentialScores(score);
        return pairReturned;
    }

    ;

    public ArrayList<Card> twoPairsPresent() {
        ArrayList<Card> pairReturned;
        Hand copyHand = hand;
        ArrayList<Card> firstPair = onePairPresent(copyHand.getCards());
        if (firstPair.size() != 0) {
            copyHand.getCards().remove(firstPair.get(0));
            copyHand.getCards().remove(firstPair.get(1));
            ArrayList<Card> secondPair = onePairPresent(copyHand.getCards());
            firstPair.addAll(secondPair);
            pairReturned = firstPair;
            if (pairReturned.size() == 4) {
                hand.addToPotentialScores(this.getMaxScore() * 10);
                return pairReturned;
            } else {
                return null;
            }
        } else return null;
    }

    public boolean threeOfAKindPresent() {
        int score = 0;
        boolean isThreeOfAKind = false;
        ArrayList<Integer> cardValues = hand.getCardValues();
        for (Integer cardValue : cardValues) {
            if (Collections.frequency(cardValues, cardValue) == 3) {
                score = cardValue * 1000;
                isThreeOfAKind = true;
                break;
            }
        }
        hand.addToPotentialScores(score);
        return isThreeOfAKind;
    }

    public boolean fourOfAKindPresent() {
        int score = 0;
        boolean isFourOfAKind = false;
        ArrayList<Integer> cardValues = hand.getCardValues();
        for (Integer cardValue : cardValues) {
            if (Collections.frequency(cardValues, cardValue) == 4) {
                score = cardValue * 10000000;
                isFourOfAKind = true;
                break;
            }
        }
        hand.addToPotentialScores(score);
        return isFourOfAKind;
    }

    public boolean fullHousePresent() {
        boolean isFullHouse = false;
        Hand copyHand = hand;
        ArrayList<Card> firstPair = onePairPresent(copyHand.getCards());
        if (!onePairPresent(copyHand.getCards()).isEmpty()) {
            copyHand.getCards().remove(firstPair.get(0));
            copyHand.getCards().remove(firstPair.get(1));
            if (this.threeOfAKindPresent()) {
                hand.addToPotentialScores(this.getMaxScore() * 1000);
                isFullHouse = true;
            }
        }
        return isFullHouse;
    }

    public boolean checkFlush() {
        boolean isFlush = false;
        ArrayList<String> suitsArray = hand.getCardSuits();
        ArrayList<Integer> valuesArray = hand.getCardValues();
        if (Collections.frequency(suitsArray, suitsArray.get(0)) == 5) {
            isFlush = true;
            int score = valuesArray.stream().max(Integer::compare).get();
            hand.addToPotentialScores(score * 100000);
        }
        return isFlush;
    }

    public boolean checkStraight() {
        boolean isStraight = false;
        ArrayList<Integer> valuesArray = hand.getCardValues();
        int maxVal = valuesArray.stream().max(Integer::compare).get();
        int minVal = valuesArray.stream().min(Integer::compare).get();

        if ((maxVal - minVal) == 4) {
            isStraight = true;
            hand.addToPotentialScores(maxVal * 10000);
        }
        return isStraight;
    }

    public boolean checkStraightFlush() {
        boolean isStraightFlush = false;
        if (this.checkFlush() && this.checkStraight()) {
            isStraightFlush = true;
            ArrayList<Integer> valuesArray = hand.getCardValues();
            int maxVal = valuesArray.stream().max(Integer::compare).get();
            hand.addToPotentialScores(maxVal * 100000000);
        }
        return isStraightFlush;
    }

    public boolean checkRoyalFlush() {
        boolean isRoyalFlush = false;
        if (this.checkStraightFlush()) {
            ArrayList<Integer> valuesArray = hand.getCardValues();
            int totalVal = valuesArray.stream().mapToInt(i -> i).sum();
            if (totalVal == 60) {
                isRoyalFlush = true;
                hand.addToPotentialScores(1400000001);
            }
        }
        return isRoyalFlush;
    }

    public int getMaxScore() {
        return hand.getPotentialScores().stream().max(Integer::compare).get();
    }

    public int processHand() {
        this.highestCard();
        this.onePairPresent(hand.getCards());
        this.twoPairsPresent();
        this.threeOfAKindPresent();
        this.checkStraight();
        this.checkFlush();
        this.fullHousePresent();
        this.fourOfAKindPresent();
        this.checkStraightFlush();
        this.checkRoyalFlush();

        return getMaxScore();
    }

}
