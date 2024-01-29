package com.github.aidenlortie.comp1008week4lesson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class MemoryGameController implements Initializable {

    @FXML
    private Label CorrectGuessLabel;

    @FXML
    private FlowPane cardFlowPlane;

    @FXML
    private Label guessLabel;

    private ArrayList<MemoryCard> cardsInGame;
    private MemoryCard firstCard, secondCard;
    private int numOfGuess;
    private int numOfMatches;

    @FXML
    void playAgain(ActionEvent event){
        firstCard = null;
        secondCard = null;

        DeckOfCards deck = new DeckOfCards();
        deck.shuffle();

        cardsInGame = new ArrayList<>();

        for (int i = 0; i < cardFlowPlane.getChildren().size()/2; i++){
            Card cardDealt = deck.dealTopCard();
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
        }
        Collections.shuffle(cardsInGame);
        flipAllCards();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This will add a number to each imageView and set the image to the back of the card
     */
    private void initializeImageView(){
        for(int i = 0; i < cardFlowPlane.getChildren().size(); i++) {
            ImageView imageView = (ImageView) cardFlowPlane.getChildren().get(i);
            imageView.setImage(new Image(Card.class.getResourceAsStream("images/back_of_card.png")));
            imageView.setUserData(i);
            //register a click listener
            imageView.setOnMouseClicked(event ->{
                flipCard((int) imageView.getUserData());
            });
        }
    }

    private void flipAllCards(){
        for(int i = 0; i<cardsInGame.size(); i++){
            ImageView imageView = (ImageView) cardFlowPlane.getChildren().get(i);
            MemoryCard card = cardsInGame.get(i);

            if(card.isMatched())
                imageView.setImage(card.getImage());
            else
                imageView.setImage(card.getBackOfCardImage());
        }

    }

    private void flipCard(int indexOfCard){
        if( firstCard == null && secondCard == null)
            flipAllCards();

        ImageView imageView = (ImageView) cardFlowPlane.getChildren().get(indexOfCard);

        if (firstCard == null){
            firstCard = cardsInGame.get(indexOfCard);
            imageView.setImage(firstCard.getImage());
        } else if (secondCard == null){
            secondCard = cardsInGame.get(indexOfCard);
            imageView.setImage(secondCard.getImage());
            checkForMatch();
            updateLabels();
        }

    }
}