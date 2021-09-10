import classes.Card;
import classes.Hand;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PokerGameTest {
    @Test
    public void oneHand() {
        Card cardOne = createCard("4", "S");
        Card cardTwo = createCard("5", "S");
        Card cardThree = createCard("7", "H");
        Card cardFour = createCard("8", "D");
        Card cardFive = createCard("J", "C");
        Hand hand = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        assertEquals(hand, new PokerGame(hand).getHand());
    }

    @Test
    public void highestCardWins() {
        Card cardOne = createCard("4", "S");
        Card cardTwo = createCard("5", "S");
        Card cardThree = createCard("7", "H");
        Card cardFour = createCard("8", "D");
        Card cardFive = createCard("3", "C");
        Hand handWithHighestEight = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        String highestCard = "8D";
        assertEquals(highestCard, new PokerGame(handWithHighestEight).highestCard().toString());

        Card cardSix = createCard("J", "C");
        Hand handWithHighestJack = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardSix);

        highestCard = "JC";
        assertEquals(highestCard, new PokerGame(handWithHighestJack).highestCard().toString());
    }

    @Test
    public void onePairPresent() {
        Card cardOne = createCard("4", "C");
        Card cardTwo = createCard("7", "H");
        Card cardThree = createCard("4", "D");
        Card cardFour = createCard("8", "D");
        Card cardFive = createCard("J", "C");
        Hand handWithOnePair = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        ArrayList<Card> pairOfFours = new ArrayList<>(Arrays.asList(cardOne, cardThree));

        assertEquals(pairOfFours, new PokerGame(handWithOnePair).onePairPresent(handWithOnePair.getCards()));

        Card cardSix = createCard("5", "D");
        Card cardSeven = createCard("Q", "D");
        Card cardEight = createCard("Q", "C");
        Hand differentHandWithOnePair = createNewHand(cardOne, cardTwo, cardSix, cardSeven, cardEight);

        ArrayList<Card> differentPair = new ArrayList<>(Arrays.asList(cardSeven, cardEight));

        assertEquals(differentPair, new PokerGame(differentHandWithOnePair).onePairPresent(differentHandWithOnePair.getCards()));
    }

    @Test
    public void onePairNotPresent() {
        Card cardOne = createCard("4", "C");
        Card cardTwo = createCard("7", "H");
        Card cardThree = createCard("5", "D");
        Card cardFour = createCard("K", "D");
        Card cardFive = createCard("Q", "C");
        Hand handWithNoPair = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        ArrayList<Card> noPair = new ArrayList<>();

        assertEquals(noPair, new PokerGame(handWithNoPair).onePairPresent(handWithNoPair.getCards()));
    }

    @Test
    public void TwoPairsPresent() {
        Card cardOne = createCard("4", "D");
        Card cardTwo = createCard("4", "C");
        Card cardThree = createCard("6", "S");
        Card cardFour = createCard("6", "D");
        Card cardFive = createCard("3", "C");
        Hand handWithTwoPairs = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        ArrayList<Card> twoPairs = new ArrayList<>(Arrays.asList(cardOne, cardTwo, cardThree, cardFour));
        PokerGame poker = new PokerGame(handWithTwoPairs);

        assertEquals(twoPairs, poker.twoPairsPresent());
        assertEquals(600, poker.getMaxScore());

        Card cardSix = createCard("9", "D");
        Hand handWithOnlyOnePair = createNewHand(cardOne, cardTwo, cardThree, cardSix, cardFive);

        PokerGame newPoker = new PokerGame(handWithOnlyOnePair);

        assertNull(newPoker.twoPairsPresent());
        assertEquals(40, newPoker.getMaxScore());
    }

    @Test
    public void ThreeOfAKindPresent() {
        Card cardOne = createCard("4", "D");
        Card cardTwo = createCard("4", "C");
        Card cardThree = createCard("4", "S");
        Card cardFour = createCard("6", "D");
        Card cardFive = createCard("3", "C");
        Hand handWithThreeOfAKind = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        PokerGame poker = new PokerGame(handWithThreeOfAKind);

        assertTrue(poker.threeOfAKindPresent());
        assertEquals(4000, poker.getMaxScore());

        Card cardSix = createCard("K", "D");
        Card cardSeven = createCard("K", "C");
        Card cardEight = createCard("K", "S");
        Hand handWithOtherThreeOfAKind = createNewHand(cardSix, cardSeven, cardEight, cardFour, cardFive);

        PokerGame otherPoker = new PokerGame(handWithOtherThreeOfAKind);

        assertTrue(otherPoker.threeOfAKindPresent());
        assertEquals(13000, otherPoker.getMaxScore());
    }

    @Test
    public void FourOfAKindPresent() {
        Card cardOne = createCard("4", "D");
        Card cardTwo = createCard("4", "C");
        Card cardThree = createCard("4", "S");
        Card cardFour = createCard("4", "H");
        Card cardFive = createCard("3", "C");
        Hand handWithFourOfAKind = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);
        PokerGame poker = new PokerGame(handWithFourOfAKind);

        assertTrue(poker.fourOfAKindPresent());
        assertEquals(40000000, poker.getMaxScore());
    }

    @Test
    public void FullHousePresent() {
        Card cardOne = createCard("4", "D");
        Card cardTwo = createCard("4", "C");
        Card cardThree = createCard("4", "S");
        Card cardFour = createCard("3", "H");
        Card cardFive = createCard("3", "C");
        Hand handWithFullHouse = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        PokerGame poker = new PokerGame(handWithFullHouse);

        assertTrue(poker.fullHousePresent());
        assertEquals(4000000, poker.getMaxScore());

        Card cardSix = createCard("2", "H");
        Hand handWithNoFullHouse = createNewHand(cardOne, cardTwo, cardThree, cardSix, cardFive);

        PokerGame newPoker = new PokerGame(handWithNoFullHouse);

        assertFalse(newPoker.fullHousePresent());
        assertEquals(0, newPoker.getMaxScore());
    }

    @Test
    public void checkCardSuits() {
        Card cardOne = createCard("4", "D");
        Card cardTwo = createCard("4", "C");
        Card cardThree = createCard("4", "S");
        Card cardFour = createCard("3", "H");
        Card cardFive = createCard("3", "C");
        Hand hand = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        ArrayList<String> suits = new ArrayList<String>(Arrays.asList("D", "C", "S", "H", "C"));

        assertEquals(suits, hand.getCardSuits());
    }

    @Test
    public void checkFlush() {
        Card cardOne = createCard("K", "H");
        Card cardTwo = createCard("4", "H");
        Card cardThree = createCard("10", "H");
        Card cardFour = createCard("9", "H");
        Card cardFive = createCard("A", "H");
        Hand handWithFlush = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        PokerGame newPoker = new PokerGame(handWithFlush);

        assertTrue(newPoker.checkFlush());
        assertEquals(1400000, newPoker.getMaxScore());

        Card cardSix = createCard("2", "D");
        Card cardSeven = createCard("4", "H");
        Card cardEight = createCard("5", "S");
        Card cardNine = createCard("9", "H");
        Hand handWithNoFlush = createNewHand(cardSix, cardSeven, cardEight, cardNine, cardFive);

        PokerGame poker = new PokerGame(handWithNoFlush);

        assertFalse(poker.checkFlush());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkStraight() {
        Card cardOne = createCard("9", "H");
        Card cardTwo = createCard("10", "D");
        Card cardThree = createCard("J", "H");
        Card cardFour = createCard("K", "S");
        Card cardFive = createCard("Q", "H");
        Hand handWithStraight = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        PokerGame newPoker = new PokerGame(handWithStraight);

        assertTrue(newPoker.checkStraight());
        assertEquals(130000, newPoker.getMaxScore());

        Card cardSix = createCard("2", "H");
        Card cardSeven = createCard("7", "D");
        Card cardEight = createCard("5", "H");
        Card cardNine = createCard("6", "S");
        Card cardTen = createCard("4", "H");
        Hand handWithNoStraight = createNewHand(cardSix, cardSeven, cardEight, cardNine, cardTen);

        PokerGame poker = new PokerGame(handWithNoStraight);

        assertFalse(poker.checkStraight());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkStraightFlush() {
        Card cardOne = createCard("10", "H");
        Card cardTwo = createCard("9", "H");
        Card cardThree = createCard("8", "H");
        Card cardFour = createCard("6", "H");
        Card cardFive = createCard("7", "H");
        Hand handWithStraightFlush = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        PokerGame newPoker = new PokerGame(handWithStraightFlush);

        assertTrue(newPoker.checkStraightFlush());
        assertEquals(1000000000, newPoker.getMaxScore());

        Card cardSix = createCard("2", "H");
        Card cardSeven = createCard("7", "D");
        Card cardEight = createCard("5", "H");
        Card cardNine = createCard("6", "S");
        Card cardTen = createCard("4", "H");
        Hand handWithNoStraightFlush = createNewHand(cardSix, cardSeven, cardEight, cardNine, cardTen);

        PokerGame poker = new PokerGame(handWithNoStraightFlush);

        assertFalse(poker.checkStraightFlush());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkRoyalFlush() {
        Card cardOne = createCard("10", "H");
        Card cardTwo = createCard("J", "H");
        Card cardThree = createCard("A", "H");
        Card cardFour = createCard("K", "H");
        Card cardFive = createCard("Q", "H");
        Hand handWithRoyalFlush = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);

        PokerGame newPoker = new PokerGame(handWithRoyalFlush);

        assertTrue(newPoker.checkRoyalFlush());
        assertEquals(1400000001, newPoker.getMaxScore());

        Card cardSix = createCard("2", "H");
        Card cardSeven = createCard("7", "D");
        Card cardEight = createCard("5", "H");
        Card cardNine = createCard("6", "S");
        Card cardTen = createCard("4", "H");
        Hand handWithNoRoyalFlush = createNewHand(cardSix, cardSeven, cardEight, cardNine, cardTen);

        PokerGame poker = new PokerGame(handWithNoRoyalFlush);

        assertFalse(poker.checkStraightFlush());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkProcessHand() {
        Card cardOne = createCard("10", "H");
        Card cardTwo = createCard("J", "H");
        Card cardThree = createCard("A", "H");
        Card cardFour = createCard("K", "H");
        Card cardFive = createCard("Q", "H");
        Hand hand = createNewHand(cardOne, cardTwo, cardThree, cardFour, cardFive);
        PokerGame newPoker = new PokerGame(hand);

        assertEquals(1400000001, newPoker.processHand());
    }

    private Hand createNewHand(Card cardOne,
                               Card cardTwo,
                               Card cardThree,
                               Card cardFour,
                               Card cardFive) {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(cardOne, cardTwo, cardThree, cardFour, cardFive));
        ArrayList<Integer> potentialScores = new ArrayList<>(Collections.singletonList(0));

        return Hand.builder()
                .cards(cards)
                .potentialScores(potentialScores)
                .build();
    }

    private Card createCard(String value, String suit) {
        return Card.builder()
                .value(value)
                .suit(suit)
                .build();
    }
}

