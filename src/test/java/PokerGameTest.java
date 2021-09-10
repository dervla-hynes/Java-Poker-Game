import classes.Hand;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PokerGameTest {
    @Test
    public void oneHand() {
        Hand hand = createNewHand("4S", "5S", "7H", "8D", "JC");
        assertEquals(hand, new PokerGame(hand).getHand());
    }

    @Test
    public void highestCardWins() {
        Hand handWithHighestEight = createNewHand("4S", "5S", "7H", "8D", "3C");
        String highestCard = "8D";
        assertEquals(highestCard, new PokerGame(handWithHighestEight).highestCard());

        Hand handWithHighestJack = createNewHand("4S", "5S", "7H", "8D", "JC");
        highestCard = "JC";
        assertEquals(highestCard, new PokerGame(handWithHighestJack).highestCard());
    }

    @Test
    public void onePairPresent() {
        Hand handWithOnePair = createNewHand("4C", "7H", "4D", "8D", "JC");
        ArrayList<String> pairOfFours = new ArrayList<String>(Arrays.asList("4C", "4D"));

        assertEquals(pairOfFours, new PokerGame(handWithOnePair).onePairPresent(handWithOnePair.getCards()));

        Hand differentHandWithOnePair = createNewHand("4C", "7H", "5D", "QD", "QC");
        ArrayList<String> differentPair = new ArrayList<String>(Arrays.asList("QD", "QC"));

        assertEquals(differentPair, new PokerGame(differentHandWithOnePair).onePairPresent(differentPair));
    }

    @Test
    public void onePairNotPresent() {
        Hand handWithNoPair = createNewHand("4C", "7H", "5D", "KD", "QC");
        ArrayList<String> noPair = new ArrayList<String>();

        assertEquals(noPair, new PokerGame(handWithNoPair).onePairPresent(handWithNoPair.getCards()));
    }

    @Test
    public void TwoPairsPresent() {
        Hand handWithTwoPairs = createNewHand("4D", "4C", "6S", "6D", "3C");
        ArrayList<String> twoPairs = new ArrayList<String>(Arrays.asList("4D", "4C", "6S", "6D"));
        PokerGame poker = new PokerGame(handWithTwoPairs);

        assertEquals(twoPairs, poker.twoPairsPresent());
        assertEquals(600, poker.getMaxScore());

        Hand handWithOnlyOnePair = createNewHand("4D", "4C", "6S", "9D", "3C");
        PokerGame newPoker = new PokerGame(handWithOnlyOnePair);

        assertNull(newPoker.twoPairsPresent());
        assertEquals(40, newPoker.getMaxScore());
    }

    @Test
    public void ThreeOfAKindPresent() {
        Hand handWithThreeOfAKind = createNewHand("4D", "4C", "4S", "6D", "3C");
        PokerGame poker = new PokerGame(handWithThreeOfAKind);

        assertTrue(poker.threeOfAKindPresent());
        assertEquals(4000, poker.getMaxScore());

        Hand handWithOtherThreeOfAKind = createNewHand("KD", "KC", "KS", "6D", "3C");
        PokerGame otherPoker = new PokerGame(handWithOtherThreeOfAKind);

        assertTrue(otherPoker.threeOfAKindPresent());
        assertEquals(13000, otherPoker.getMaxScore());
    }

    @Test
    public void FourOfAKindPresent() {
        Hand handWithFourOfAKind = createNewHand("4D", "4C", "4S", "4H", "3C");
        PokerGame poker = new PokerGame(handWithFourOfAKind);

        assertTrue(poker.fourOfAKindPresent());
        assertEquals(40000000, poker.getMaxScore());
    }

    @Test
    public void FullHousePresent() {
        Hand handWithFullHouse = createNewHand("4D", "4C", "4S", "3H", "3C");
        PokerGame poker = new PokerGame(handWithFullHouse);

        assertTrue(poker.fullHousePresent());
        assertEquals(4000000, poker.getMaxScore());

        Hand handWithNoFullHouse = createNewHand("4D", "4C", "4S", "2H", "3C");
        PokerGame newPoker = new PokerGame(handWithNoFullHouse);

        assertFalse(newPoker.fullHousePresent());
        assertEquals(0, newPoker.getMaxScore());
    }

    @Test
    public void checkCardSuits() {
        Hand hand = createNewHand("4D", "4C", "4S", "3H", "3C");
        ArrayList<String> suits = new ArrayList<String>(Arrays.asList("D", "C", "S", "H", "C"));
        PokerGame poker = new PokerGame(hand);

        assertEquals(suits, poker.getCardSuit(poker.getHand().getCards()));

        Hand newHand = createNewHand("2H", "4H", "5H", "9H", "3H");
        ArrayList<String> newSuits = new ArrayList<String>(Arrays.asList("H", "H", "H", "H", "H"));
        PokerGame newPoker = new PokerGame(newHand);

        assertEquals(newSuits, newPoker.getCardSuit(newPoker.getHand().getCards()));
    }

    @Test
    public void checkFlush() {
        Hand handWithFlush = createNewHand("KH", "4H", "10H", "9H", "AH");
        PokerGame newPoker = new PokerGame(handWithFlush);

        assertTrue(newPoker.checkFlush());
        assertEquals(1400000, newPoker.getMaxScore());

        Hand handWithNoFlush = createNewHand("2D", "4H", "5S", "9H", "3C");
        PokerGame poker = new PokerGame(handWithNoFlush);

        assertFalse(poker.checkFlush());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkStraight() {
        Hand handWithStraight = createNewHand("9H", "10D", "JH", "KS", "QH");
        PokerGame newPoker = new PokerGame(handWithStraight);

        assertTrue(newPoker.checkStraight());
        assertEquals(130000, newPoker.getMaxScore());

        Hand handWithNoStraight = createNewHand("2H", "7D", "5H", "6S", "4H");
        PokerGame poker = new PokerGame(handWithNoStraight);

        assertFalse(poker.checkStraight());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkStraightFlush() {
        Hand handWithStraightFlush = createNewHand("10H", "9H", "8H", "6H", "7H");
        PokerGame newPoker = new PokerGame(handWithStraightFlush);

        assertTrue(newPoker.checkStraightFlush());
        assertEquals(1000000000, newPoker.getMaxScore());

        Hand handWithNoStraightFlush = createNewHand("2H", "7D", "5H", "6S", "4H");
        PokerGame poker = new PokerGame(handWithNoStraightFlush);

        assertFalse(poker.checkStraightFlush());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkRoyalFlush() {
        Hand handWithRoyalFlush = createNewHand("10H", "JH", "AH", "KH", "QH");
        PokerGame newPoker = new PokerGame(handWithRoyalFlush);

        assertTrue(newPoker.checkRoyalFlush());
        assertEquals(1400000001, newPoker.getMaxScore());

        Hand handWithNoRoyalFlush = createNewHand("2H", "7D", "5H", "6S", "4H");
        PokerGame poker = new PokerGame(handWithNoRoyalFlush);

        assertFalse(poker.checkStraightFlush());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkProcessHand() {
        Hand hand = createNewHand("10H", "JH", "AH", "KH", "QH");
        PokerGame newPoker = new PokerGame(hand);

        assertEquals(1400000001, newPoker.processHand());
    }

    private Hand createNewHand(String cardOne,
                               String cardTwo,
                               String cardThree,
                               String cardFour,
                               String cardFive) {
        ArrayList<String> cards = new ArrayList<>(Arrays.asList(cardOne, cardTwo, cardThree, cardFour, cardFive));

        return Hand.builder()
                .cards(cards)
                .potentialScores(new ArrayList<>(Collections.singletonList(0)))
                .build();
    }
}

