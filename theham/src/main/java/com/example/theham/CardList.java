package com.example.theham;

public class CardList {

    private int drawableId, card;
    private String item_name, item_division;

    public int getDrawableId() {
        return drawableId;
    }

    public int getCard() {
        return card;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_division() {
        return item_division;
    }

    public CardList(int drawableId, int card, String item_name, String item_division) {
        this.drawableId = drawableId;
        this.card = card;
        this.item_name = item_name;
        this.item_division = item_division;
    }

}


