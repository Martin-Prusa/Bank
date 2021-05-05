package com.company.logic;


import java.io.Serializable;

public class BankAccount implements Serializable {
    private final int id;
    private Person owner;
    private int balance;

    public BankAccount(Person owner, int id) {
        this.balance = 0;
        this.owner = owner;
        this.id = id;
    }

    //--------------Methods

    public void addMoney(int value){
        this.balance += value;
    }

    public boolean withdrawMoney(int value) {
        if(this.balance - value >= 0) {
            this.balance -= value;
            return true;
        }
        return false;
    }

    //--------------Getters and setters


    public int getId() {
        return id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
