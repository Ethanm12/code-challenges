import java.io.*;
import java.util.*;
import java.util.regex.*;

public class PokerHands {
	public static void main(String[] args) {
        
		Pattern p = Pattern.compile("^(?:[0-9TJQKA]|1[0123])[CDHS](?:([/ -])(?:[0-9TJQKA]|1[0123])[CDHS]){4}$");
        String seperators = "-/ ";
		Scanner scan = new Scanner(System.in);
		boolean valid = true;

        while (scan.hasNextLine()) {
            String rawInput = scan.nextLine();
            String input = rawInput.toUpperCase();
            Matcher m = p.matcher(input);

            if (!m.find()) { 
                System.out.println("Invalid: " + rawInput);                
				continue;
            }

            String seperator = m.group(1);
			String[] cardArray = input.split(seperator);

            if (cardArray.length != 5) {
                System.out.println("Invalid: " + rawInput);
                continue;
            }
            
			//duplicate card checking
            for (int i = 0; i < cardArray.length - 1; i++) {
                for (int j = i + 1; j < cardArray.length; j++) {
                    if (cardArray[i].equals(cardArray[j])) {
                        valid = false;
                        break;
                    }
                }

                if (!valid) {
                    break;
                }
            }

            if (!valid) {
                System.out.println("Invalid: " + rawInput);
                continue;
            }
			
			
			
            Card[] cards = new Card[cardArray.length];

            for (int i = 0; i < cardArray.length; i++) {
                int offset = cardArray[i].length() > 2 ? 1 : 0;
                String value = cardArray[i].substring(0, 1 + offset);
				//System.out.println(value);
                String suit = cardArray[i].substring(1 + offset, 2 + offset);
                cards[i] = new Card(value, suit);
            }
			
			
			//Sort by numerical then suit order.
            Comparator<Card> comp = Comparator.comparingInt(Card::getValue).thenComparingInt(Card::getSuit);
            Arrays.sort(cards, comp);

            int i = 0;
            System.out.print(cards[i++]);
            for (; i < cards.length; i++) {
                System.out.print(" " + cards[i]);
            }
            System.out.println();
        }
    }	

	
	public static class Card {
		/* Card order
		2 < 3 < 4 < 5 < 6 < 7 < 8 < 9 < 10 < 11 < 12 < 13 < 1
		Suit Order
		C < D < H < S
		*/
        public int suit;
		public int value;
        		
		// Accessor methods
		public int getSuit() {
            return suit;
        }
		
		public int getValue() {
            return value;
        }
        

        public Card(String value, String suit) {
            switch (value) {
                // case "T":
                    // this.value = 10;
                    // break;
                case "J":
                    this.value = 11;
                    break;
                case "Q":
                    this.value = 12;
                    break;
                case "K":
                    this.value = 13;
                    break;
                case "A":
                case "1":
                    this.value = 14;
                    break;
                default:
                    this.value = Integer.parseInt(value);
            }

            switch (suit) {
                case "C":
                    this.suit = 1;
                    break;
                case "D":
                    this.suit = 2;
                    break;
                case "H":
                    this.suit = 3;
                    break;
                case "S":
                    this.suit = 4;
                    break;
            }
        }

        public String toString() {
            String output;

            switch (value) {
                case 11:
                    output = "J";
                    break;
                case 12:
                    output = "Q";
                    break;
                case 13:
                    output = "K";
                    break;
                case 14:
                    output = "A";
                    break;
                default:
                    output = Integer.toString(value);
            }

            switch (suit) {
                case 1:
                    output += "C";
                    break;
                case 2:
                    output += "D";
                    break;
                case 3:
                    output += "H";
                    break;
                case 4:
                    output += "S";
                    break;
            }

            return output;
        }

        
    }
	
}