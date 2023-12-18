package com.uknown.kelompok8_project_akhir_8.homepage.model;

import java.io.Serializable;
import java.util.List;

public class TicketModel implements Serializable {
    private String dateToday;
    private int numberBook;
    private float rate;
    private int totalPassanger;
    private String dateOrder;
    private BusModel busModel;
    private User user;
    private NearbyLocationModel departune;
    private NearbyLocationModel arrival;
    private List<String> numberSeat;
    private String busId;
    private String paymentMethod;
    private String uid;
    private int accumulatePrice;
    private CreditCardModel cardModel;

    public CreditCardModel getCardModel() {
        return cardModel;
    }

    public int getAccumulatePrice() {
        return accumulatePrice;
    }

    public String getUid() {
        return uid;
    }

    public String getBusId() {
        return busId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public TicketModel(){}

    public TicketModel(String dateToday, int numberBook, float rate, int totalPassanger, String dateOrder, BusModel busModel, User user, NearbyLocationModel departune, NearbyLocationModel arrival, List<String> numberSeat, String busId, String paymentMethod, String uid, int accumulatePrice, CreditCardModel cardModel) {
        this.dateToday = dateToday;
        this.numberBook = numberBook;
        this.rate = rate;
        this.totalPassanger = totalPassanger;
        this.dateOrder = dateOrder;
        this.busModel = busModel;
        this.user = user;
        this.departune = departune;
        this.arrival = arrival;
        this.numberSeat = numberSeat;
        this.busId = busId;
        this.paymentMethod = paymentMethod;
        this.uid = uid;
        this.accumulatePrice = accumulatePrice;
        this.cardModel = cardModel;
    }

    public String getDateToday() {
        return dateToday;
    }

    public int getNumberBook() {
        return numberBook;
    }

    public float getRate() {
        return rate;
    }

    public int getTotalPassanger() {
        return totalPassanger;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public BusModel getBusModel() {
        return busModel;
    }

    public User getUser() {
        return user;
    }

    public NearbyLocationModel getDepartune() {
        return departune;
    }

    public NearbyLocationModel getArrival() {
        return arrival;
    }

    public List<String> getNumberSeat() {
        return numberSeat;
    }
}
