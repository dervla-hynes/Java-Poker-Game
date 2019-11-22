import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;


public class PokerTest {
    @Test
    public void oneHand() {
        ArrayList<String> hand = new ArrayList<>();
        hand.add("4S");
        hand.add("5S");
        hand.add("7H");
        hand.add("8D");
        hand.add("JC");
        assertEquals(hand, new Poker(hand).getBestHand());
    }

    @Test
    public void highestCardWins() {
        ArrayList<String> highest8 = new ArrayList<>();
        highest8.add("4S");
        highest8.add("5S");
        highest8.add("7H");
        highest8.add("8D");
        highest8.add("3C");
        String highestCard = "8D";
        assertEquals(highestCard, new Poker(highest8).highestCard());

        ArrayList<String> highestJack = new ArrayList<>();
        highestJack.add("4S");
        highestJack.add("5S");
        highestJack.add("7H");
        highestJack.add("8D");
        highestJack.add("JC");
        highestCard = "JC";
        assertEquals(highestCard, new Poker(highestJack).highestCard());
    }

    @Test
    public void onePairPresent() {
        ArrayList<String> onePair = new ArrayList<>();
        onePair.add("4C");
        onePair.add("7H");
        onePair.add("4D");
        onePair.add("8D");
        onePair.add("JC");
        ArrayList<String> pair = new ArrayList<String>();
        pair.add("4C");
        pair.add("4D");
        assertEquals(pair, new Poker(onePair).onePairPresent(onePair));

        ArrayList<String> differentPair = new ArrayList<>();
        differentPair.add("4C");
        differentPair.add("7H");
        differentPair.add("5D");
        differentPair.add("QD");
        differentPair.add("QC");
        ArrayList<String> otherPair = new ArrayList<String>();
        otherPair.add("QD");
        otherPair.add("QC");
        assertEquals(otherPair, new Poker(differentPair).onePairPresent(differentPair));
    }

    @Test
    public void onePairNotPresent() {
        ArrayList<String> noPair = new ArrayList<>();
        noPair.add("4C");
        noPair.add("7H");
        noPair.add("5D");
        noPair.add("KD");
        noPair.add("QC");
        ArrayList<String> noPairArray = new ArrayList<String>();
        assertEquals(noPairArray, new Poker(noPair).onePairPresent(noPair));
    }

    @Test
    public void TwoPairsPresent() {
        ArrayList<String> pairs = new ArrayList<String>();
        pairs.add("4D");
        pairs.add("4C");
        pairs.add("6S");
        pairs.add("6D");
        pairs.add("3C");
        ArrayList<String> twoPairs = new ArrayList<String>();
        twoPairs.add("4D");
        twoPairs.add("4C");
        twoPairs.add("6S");
        twoPairs.add("6D");
        Poker poker = new Poker(pairs);
        assertEquals(twoPairs, poker.twoPairsPresent());
        assertEquals(600, poker.getMaxScore());

        ArrayList<String> notTwoPairs = new ArrayList<String>();
        notTwoPairs.add("4D");
        notTwoPairs.add("4C");
        notTwoPairs.add("6S");
        notTwoPairs.add("9D");
        notTwoPairs.add("3C");
        Poker otherPoker = new Poker(notTwoPairs);
        assertEquals(null, otherPoker.twoPairsPresent());
        assertEquals(40, otherPoker.getMaxScore());
    }

    @Test
    public void ThreeOfAKindPresent() {
        ArrayList<String> hand = new ArrayList<String>();
        hand.add("4D");
        hand.add("4C");
        hand.add("4S");
        hand.add("6D");
        hand.add("3C");
        Poker poker = new Poker(hand);
        assertTrue(poker.threeOfAKindPresent());
        assertEquals(4000, poker.getMaxScore());

        ArrayList<String> otherHand = new ArrayList<String>();
        otherHand.add("KD");
        otherHand.add("KC");
        otherHand.add("KS");
        otherHand.add("6D");
        otherHand.add("3C");
        Poker otherPoker = new Poker(otherHand);
        assertTrue(otherPoker.threeOfAKindPresent());
        assertEquals(13000, otherPoker.getMaxScore());
    }

    @Test
    public void FourOfAKindPresent() {
        ArrayList<String> hand = new ArrayList<String>();
        hand.add("4D");
        hand.add("4C");
        hand.add("4S");
        hand.add("4H");
        hand.add("3C");
        Poker poker = new Poker(hand);
        assertTrue(poker.fourOfAKindPresent());
        assertEquals(40000000, poker.getMaxScore());
    }

    @Test
    public void FullHousePresent() {
        ArrayList<String> hand = new ArrayList<String>();
        hand.add("4D");
        hand.add("4C");
        hand.add("4S");
        hand.add("3H");
        hand.add("3C");
        Poker poker = new Poker(hand);
        assertTrue(poker.fullHousePresent());
        assertEquals(4000000, poker.getMaxScore());

        ArrayList<String> newHand = new ArrayList<String>();
        newHand.add("4D");
        newHand.add("4C");
        newHand.add("4S");
        newHand.add("2H");
        newHand.add("3C");
        Poker newPoker = new Poker(newHand);
        assertFalse(newPoker.fullHousePresent());
        assertEquals(0, newPoker.getMaxScore());

        ArrayList<String> otherHand = new ArrayList<String>();
        otherHand.add("7D");
        otherHand.add("4C");
        otherHand.add("4S");
        otherHand.add("2H");
        otherHand.add("3C");
        Poker otherPoker = new Poker(otherHand);
        assertFalse(otherPoker.fullHousePresent());
        assertEquals(40, otherPoker.getMaxScore());
    }

    @Test
    public void checkCardSuits() {
        ArrayList<String> hand = new ArrayList<String>();
        hand.add("4D");
        hand.add("4C");
        hand.add("4S");
        hand.add("3H");
        hand.add("3C");

        ArrayList<String> suits = new ArrayList<String>();
        suits.add("D");
        suits.add("C");
        suits.add("S");
        suits.add("H");
        suits.add("C");

        Poker poker = new Poker(hand);
        assertEquals(suits, poker.getCardSuit(poker.hand));

        ArrayList<String> newHand = new ArrayList<String>();
        newHand.add("2H");
        newHand.add("4H");
        newHand.add("5H");
        newHand.add("9H");
        newHand.add("3H");
        ArrayList<String> otherSuits = new ArrayList<String>();
        otherSuits.add("H");
        otherSuits.add("H");
        otherSuits.add("H");
        otherSuits.add("H");
        otherSuits.add("H");

        Poker newPoker = new Poker(newHand);
        assertEquals(otherSuits, newPoker.getCardSuit(newPoker.hand));
    }

    @Test
    public void checkFlush() {
        ArrayList<String> newHand = new ArrayList<String>();
        newHand.add("KH");
        newHand.add("4H");
        newHand.add("10H");
        newHand.add("9H");
        newHand.add("AH");

        Poker newPoker = new Poker(newHand);
        assertTrue(newPoker.isFlush());
        assertEquals(14000000, newPoker.getMaxScore());

        ArrayList<String> hand = new ArrayList<String>();
        hand.add("2D");
        hand.add("4H");
        hand.add("5S");
        hand.add("9H");
        hand.add("3C");

        Poker poker = new Poker(hand);
        assertFalse(poker.isFlush());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkStraight() {
        ArrayList<String> newHand = new ArrayList<String>();
        newHand.add("9H");
        newHand.add("10D");
        newHand.add("JH");
        newHand.add("KS");
        newHand.add("QH");

        Poker newPoker = new Poker(newHand);
        assertTrue(newPoker.isStraight());
        assertEquals(1300000, newPoker.getMaxScore());

        ArrayList<String> hand = new ArrayList<String>();
        hand.add("2H");
        hand.add("7D");
        hand.add("5H");
        hand.add("6S");
        hand.add("4H");

        Poker poker = new Poker(hand);
        assertFalse(poker.isStraight());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    void checkStraightFlush() {

        Poker hand = Poker.aHandOfPokerWith("10H 9H 8H 6H 7H");
        assertThat(Poker.aHandOfPokerWith("10H 9H 8H 6H 7H").checkStraightFlush()).isEqualTo();

        ArrayList<String> newHand = new ArrayList();
        newHand.add("10H");
        newHand.add("9H");
        newHand.add("8H");
        newHand.add("6H");
        newHand.add("7H");

        Poker newPoker = new Poker(newHand);
        assertTrue(newPoker.checkStraightFlush());
        assertEquals(100000000, newPoker.getMaxScore());

        ArrayList<String> hand = new ArrayList();
        hand.add("2H");
        hand.add("7D");
        hand.add("5H");
        hand.add("6S");
        hand.add("4H");

        Poker poker = new Poker(hand);
        assertFalse(poker.checkStraightFlush());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkRoyalFlush() {
        ArrayList<String> newHand = new ArrayList<String>();
        newHand.add("10H");
        newHand.add("JH");
        newHand.add("AH");
        newHand.add("KH");
        newHand.add("QH");

        Poker newPoker = new Poker(newHand);
        assertTrue(newPoker.checkRoyalFlush());
        assertEquals(1400000001, newPoker.getMaxScore());

        ArrayList<String> hand = new ArrayList<String>();
        hand.add("2H");
        hand.add("7D");
        hand.add("5H");
        hand.add("6S");
        hand.add("4H");

        Poker poker = new Poker(hand);
        assertFalse(poker.checkStraightFlush());
        assertEquals(0, poker.getMaxScore());
    }

    @Test
    public void checkProcessHand() {
        ArrayList<String> newHand = new ArrayList<String>();
        newHand.add("10H");
        newHand.add("JH");
        newHand.add("AH");
        newHand.add("KH");
        newHand.add("QH");

        Poker newPoker = new Poker(newHand);
        assertEquals(1400000001, newPoker.processHand());

        ArrayList<String> otherHand = new ArrayList<String>();
        otherHand.add("9H");
        otherHand.add("9D");
        otherHand.add("AH");
        otherHand.add("AS");
        otherHand.add("AC");

        Poker poker = new Poker(otherHand);
        assertEquals(14000000, poker.processHand());
    }

    @Test
    public void checkProcessHandWithStraight() {
        ArrayList<String> newHand = new ArrayList<String>();
        newHand.add("5S");
        newHand.add("6H");
        newHand.add("7D");
        newHand.add("8H");
        newHand.add("9H");

        Poker newPoker = new Poker(newHand);
        assertEquals(900000, newPoker.processHand());

        ArrayList<String> otherHand = new ArrayList<String>();
        otherHand.add("2H");
        otherHand.add("2D");
        otherHand.add("4H");
        otherHand.add("5S");
        otherHand.add("6C");

        Poker poker = new Poker(otherHand);
        assertEquals(20, poker.processHand());
    }
}

