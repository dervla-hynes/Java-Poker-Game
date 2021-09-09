import classes.Hand;
import lombok.*;

import java.util.*;

@Data
public class PokerGame {
    private Hand hand;
    private ArrayList<Integer> potentialScores;

    public PokerGame(Hand hand) {
        this.hand = hand;
        this.potentialScores = new ArrayList<>(Collections.singletonList(0));
    }

    public String highestCard() {
        ArrayList<Integer> cardValues = getCardValues(hand.getCards());
        int maxValue = cardValues.stream().max(Integer::compare).get();
        int maxCardIndex = cardValues.indexOf(maxValue);
        String bestCard = hand.getCards().get(maxCardIndex);
        this.potentialScores.add(maxValue);
        return bestCard;
    }

    public ArrayList<Integer> getCardValues(ArrayList<String> handArray) {
        Map<String, Integer> faceCardMap = new HashMap<>();
        faceCardMap.put("1", 10);
        faceCardMap.put("J", 11);
        faceCardMap.put("Q", 12);
        faceCardMap.put("K", 13);
        faceCardMap.put("A", 14);

        ArrayList<Integer> cardValues = new ArrayList<Integer>();

        for (String card : handArray) {
            String firstCharacter = card.split("")[0];
            if (firstCharacter.equals("J") ||
                    firstCharacter.equals("Q") ||
                    firstCharacter.equals("K") ||
                    firstCharacter.equals("A")) {
                cardValues.add(faceCardMap.get(firstCharacter));
            } else if (firstCharacter.equals("1")) {
                cardValues.add(faceCardMap.get(firstCharacter));
            } else {
                cardValues.add(Integer.parseInt(firstCharacter));
            }
        }
        ;
        return cardValues;
    }

    public ArrayList<String> getCardSuit(ArrayList<String> handArray) {
        ArrayList<String> cardSuits = new ArrayList<String>();
        for (String card : handArray) {
            String secondCharacter = card.split("")[1];
            if (secondCharacter.equals("0")) {
                cardSuits.add(card.split("")[2]);
            } else {
                cardSuits.add(secondCharacter);
            }
        }
        return cardSuits;
    }

    public ArrayList<String> onePairPresent(ArrayList<String> hand) {
        ArrayList<String> pairReturned = new ArrayList<String>();
        int score = 0;
        ArrayList<Integer> cardValues = getCardValues(hand);
        for (Integer cardValue : cardValues) {
            if (Collections.frequency(cardValues, cardValue) == 2) {
                score = cardValue * 10;
                pairReturned.add(hand.get(cardValues.indexOf(cardValue)));
                pairReturned.add(hand.get(cardValues.lastIndexOf(cardValue)));
                break;
            }
        }
        this.potentialScores.add(score);
        return pairReturned;
    }

    ;

    public ArrayList<String> twoPairsPresent() {
        ArrayList<String> pairReturned;
        Hand copyHand = hand;
        ArrayList<String> firstPair = onePairPresent(copyHand.getCards());
        if (firstPair.size() != 0) {
            copyHand.getCards().remove(firstPair.get(0));
            copyHand.getCards().remove(firstPair.get(1));
            ArrayList<String> secondPair = onePairPresent(copyHand.getCards());
            firstPair.addAll(secondPair);
            pairReturned = firstPair;
            if (pairReturned.size() == 4) {
                this.potentialScores.add((this.getMaxScore()) * 10);
                return pairReturned;
            } else {
                return null;
            }
        } else return null;
    }

    public boolean threeOfAKindPresent() {
        int score = 0;
        boolean isThreeOfAKind = false;
        ArrayList<Integer> cardValues = getCardValues(hand.getCards());
        for (Integer cardValue : cardValues) {
            if (Collections.frequency(cardValues, cardValue) == 3) {
                score = cardValue * 1000;
                isThreeOfAKind = true;
                break;
            }
        }
        this.potentialScores.add(score);
        return isThreeOfAKind;
    }

    public boolean fourOfAKindPresent() {
        int score = 0;
        boolean isFourOfAKind = false;
        ArrayList<Integer> cardValues = getCardValues(hand.getCards());
        for (Integer cardValue : cardValues) {
            if (Collections.frequency(cardValues, cardValue) == 4) {
                score = cardValue * 10000000;
                isFourOfAKind = true;
                break;
            }
        }
        this.potentialScores.add(score);
        return isFourOfAKind;
    }

    public boolean fullHousePresent() {
        boolean isFullHouse = false;
        Hand copyHand = hand;
        ArrayList<String> firstPair = onePairPresent(copyHand.getCards());
        if (!onePairPresent(copyHand.getCards()).isEmpty()) {
            copyHand.getCards().remove(firstPair.get(0));
            copyHand.getCards().remove(firstPair.get(1));
            if (this.threeOfAKindPresent()) {
                this.potentialScores.add((this.getMaxScore()) * 1000);
                isFullHouse = true;
            }
        }
        return isFullHouse;
    }

    public boolean checkFlush() {
        boolean isFlush = false;
        ArrayList<String> suitsArray = getCardSuit(hand.getCards());
        ArrayList<Integer> valuesArray = getCardValues(hand.getCards());
        if (Collections.frequency(suitsArray, suitsArray.get(0)) == 5) {
            isFlush = true;
            int score = valuesArray.stream().max(Integer::compare).get();
            this.potentialScores.add(score * 1000000);
        }
        return isFlush;
    }

    public boolean checkStraight() {
        boolean isStraight = false;
        ArrayList<Integer> valuesArray = getCardValues(hand.getCards());
        int maxVal = valuesArray.stream().max(Integer::compare).get();
        int minVal = valuesArray.stream().min(Integer::compare).get();

        if ((maxVal - minVal) == 4) {
            isStraight = true;
            this.potentialScores.add(maxVal * 100000);
        }
        return isStraight;
    }

    public boolean checkStraightFlush() {
        boolean isStraightFlush = false;
        if (this.checkFlush() && this.checkStraight()) {
            isStraightFlush = true;
            ArrayList<Integer> valuesArray = getCardValues(hand.getCards());
            int maxVal = valuesArray.stream().max(Integer::compare).get();
            this.potentialScores.add(maxVal * 10000000);
        }
        return isStraightFlush;
    }

    public boolean checkRoyalFlush() {
        boolean isRoyalFlush = false;
        if (this.checkStraightFlush()) {
            ArrayList<Integer> valuesArray = getCardValues(hand.getCards());
            int totalVal = valuesArray.stream().mapToInt(i -> i).sum();
            if (totalVal == 60) {
                isRoyalFlush = true;
                this.potentialScores.add(1400000001);
            }
        }
        return isRoyalFlush;
    }

    public int getMaxScore() {
        return this.potentialScores.stream().max(Integer::compare).get();
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

