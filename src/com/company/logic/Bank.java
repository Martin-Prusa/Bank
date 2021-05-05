package com.company.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Bank implements Serializable {
    private ArrayList<BankAccount> accounts;
    private String name;
    private final int id;

    public Bank(String name, int id) {
        this.accounts = new ArrayList<>();
        this.name = name;
        this.id = id;
    }

    //-------------Methods
    public BankAccount createAccount(Person person) {
        int acId = Integer.parseInt(this.id+""+getNewAccountID());
        BankAccount newAccount = new BankAccount(person, acId);
        this.accounts.add(newAccount);
        return newAccount;
    }

    public void removeAccount(int id) {
        for (int i = 0; i < this.accounts.size(); i++) {
            if(this.accounts.get(i).getId() == id) {
                this.accounts.remove(i);
                break;
            }
        }
    }

    private int getNewAccountID() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (BankAccount account : this.accounts) {
            ids.add(account.getId());
        }
        Random rand = new Random();
        int id = rand.nextInt(89999) + 10000;
        while (ids.contains(id)) {
            id = rand.nextInt(89999) + 10000;
        }
        return id;
    }

    public boolean transferMoney(BankAccount from, BankAccount to, int value) {
        boolean valid = false;
        for (BankAccount account : accounts) {
            if (account.getId() == from.getId()){
                valid=true;
                break;
            }
        }
        if(!valid) return false;
        boolean valid2 = from.withdrawMoney(value);
        if(!valid2) return false;

        to.addMoney(value);
        return true;
    }

    //-------------Getters and Setters
    //---accounts
    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<BankAccount> accounts) {
        this.accounts = accounts;
    }

    //---name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //---id
    public int getId() {
        return this.id;
    }
}
