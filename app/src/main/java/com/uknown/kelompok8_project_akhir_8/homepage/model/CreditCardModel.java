package com.uknown.kelompok8_project_akhir_8.homepage.model;

import java.io.Serializable;

public class CreditCardModel implements Serializable {
    private int numberCard;
    private int validUntilMonth;
    private int validUntilYear;
    private String cardName;
    private int vcc;

    public CreditCardModel(){}
    public CreditCardModel(int numberCard, int validUntilMonth, int validUntilYear, String cardName, int vcc) {
        this.numberCard = numberCard;
        this.validUntilMonth = validUntilMonth;
        this.validUntilYear = validUntilYear;
        this.cardName = cardName;
        this.vcc = vcc;
    }

    public int getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(int numberCard) {
        this.numberCard = numberCard;
    }

    public int getValidUntilMonth() {
        return validUntilMonth;
    }

    public void setValidUntilMonth(int validUntilMonth) {
        this.validUntilMonth = validUntilMonth;
    }

    public int getValidUntilYear() {
        return validUntilYear;
    }

    public void setValidUntilYear(int validUntilYear) {
        this.validUntilYear = validUntilYear;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getVcc() {
        return vcc;
    }

    public void setVcc(int vcc) {
        this.vcc = vcc;
    }
}
