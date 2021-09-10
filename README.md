# JAVA PokerGame Game

This project is a poker game written in Java, using test driven development
The tests drive the functionality of the game

## Game Rules

When a new poker game is instantiated, the player gets a hand.
The hand contains 5 cards.

The aim is to get the best hand of possible, following the rules of poker.

The 'best score' is calculated in the processHand() method of the JavaGame class. It works by calculating a score for each of the following possibilities:

- Highest Card: value of card (2 - 14)
- One Pair: value of card * 10 (20 - 140)
- Two Pairs: value of card * 100 (200 - 1400)
- Three of a Kind: value of card * 1000 (2000 - 14,000)
- Straight: value of card * 10,000 (20,000 - 140,000)
- Flush: value of card * 100,000 (200,000 - 1,400,000)
- Full House: value of card * 1,000,000 (2,000,000 - 14,000,000)
- Four of a Kind: value of highest card * 10,000,000 (20,000,000 - 140,000,000)
- Straight Flush: value of highest card * 100,000,000 (200,000,000 - 1,400,000,000)
- Royal Flush: Score of 1,400,000,001

If a hand contains one of these, that score will be added to an array of possible scores for that hand.
At the end of processing, the highest of these scores will be the final score for the hand.
This gives a number which can be compared to another hand to deduce a winner.