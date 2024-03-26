package com.game.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardView {
    private ImageView card;
    private Image image;

    CardView(String cardType) {
        image = new Image("/com/game/masterblackjack/images/cards/" + cardType + ".png");
        card = new ImageView(image);
        card.preserveRatioProperty();
        card.setFitHeight(121.0);
        card.setFitWidth(84.0);
    }

    public ImageView getCard() {
        return card;
    }
}
