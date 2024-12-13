import java.util.Scanner;

public class Blackjack {
    public static void main(String[] args){
        //Welcome Message
        System.out.println("Welcome to Blackjack!");

        //Create our playing deck
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffle();
        //Create a deck for the player
        Deck playerDeck = new Deck();
        Deck dealerDeck = new Deck();

        double playerMoney = 100.00;

        Scanner userInput = new Scanner(System.in);

        //Game Loop
        while(playerMoney > 0){
            //Play
            //Take the players bet
            System.out.printf("You have %.2f, how much would like to bet?\n", playerMoney);
            double playerBet = userInput.nextDouble();
            if(playerBet > playerMoney){
                System.out.println("Cheating. Please leave");
                break;
            }

            boolean endRound = false;

            //Start Dealing
            //Player gets two cards
            playerDeck.draw(playingDeck);
            playerDeck.draw(playingDeck);

            //Dealer gets two cards
            dealerDeck.draw(playingDeck);
            dealerDeck.draw(playingDeck);

            while(true){
                System.out.println("Your hand:");
                System.out.println(playerDeck.toString());
                System.out.println("Your deck is value at: "+playerDeck.cardsValue());

                //Display Dealer Hand
                System.out.println("Dealer Hand: "+ dealerDeck.getCard(0).toString() + " and [Hidden]");

                //What does the player want to do?
                System.out.println("Would you like to (1)HIT or (2)STAND");
                int response = userInput.nextInt();

                //HIT
                if(response == 1){
                    playerDeck.draw(playingDeck);
                    System.out.println("You draw a:" + playerDeck.getCard(playerDeck.deckSize()-1).toString());
                    //Bust if > 21
                    if(playerDeck.cardsValue() > 21){
                        System.out.println("Bust. Currently valued at: "+ playerDeck.cardsValue());
                        playerMoney -= playerBet;
                        endRound = true;
                        break;
                    }
                }
                if(response==2){
                    break;
                }
            }

            //Reveal Dealers Cards
            System.out.println("Dealer Cards: " + dealerDeck.toString());
            //See if dealer has more points than player
            if((dealerDeck.cardsValue() > playerDeck.cardsValue()) && endRound == false){
                System.out.println("Dealer beats you!");
                playerMoney -= playerBet;
                endRound = true;
            }
            //Dealer draws at 16, stand a 17
            while((dealerDeck.cardsValue() < 17) && endRound == false){
                dealerDeck.draw(playingDeck);
                System.out.println("Dealer Draws " + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
            }
            //Display Total Value for Dealer
            System.out.println("Dealer's Hand is valued at: " + dealerDeck.cardsValue());
            //Determine if dealer buster
            if((dealerDeck.cardsValue() > 21) && endRound == false){
                System.out.println("Dealer busts! You win.");
                playerMoney += playerBet;
                endRound = true;
            }

            //Determine if push
            if((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false){
                System.out.println("Push");
                endRound = true;
            }

            if((playerDeck.cardsValue() > dealerDeck.cardsValue()) && endRound == false){
                System.out.println("You win the hand!");
                playerMoney += playerBet;
                endRound = true;
            }
            else if(endRound == false){
                System.out.println("You lose the hand");
                playerMoney -= playerBet;
                endRound = true;
            }

            playerDeck.moveAllToDeck(playerDeck);
            dealerDeck.moveAllToDeck(playerDeck);
            System.out.println("End of hand");


        }
        System.out.println("Game over! You are out of money-.");
    }
}
