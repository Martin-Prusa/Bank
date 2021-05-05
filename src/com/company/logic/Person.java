package com.company.logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
    private String name;
    private String personalId;
    private boolean male;
    private ArrayList<BankAccount> accounts;

    public Person(String name, String personalId, boolean male) {
        this.name = name;
        this.personalId = personalId;
        this.male = male;
        this.accounts = new ArrayList<>();
    }

    //--------------Methods
    public void createAccount(Bank bank) {
        BankAccount ac = bank.createAccount(this);
        this.accounts.add(ac);
    }

    public void deleteAccount(BankAccount account, Bank bank) {
        bank.removeAccount(account.getId());
        this.accounts.remove(account);
    }

    public boolean addMoneyToAccount(BankAccount account, int value) {
        boolean valid = this.owner(account);
        if(!valid) return false;
        account.addMoney(value);
        return true;
    }

    public boolean withdrawMoneyFromAccount(BankAccount account, int value) {
        boolean valid = this.owner(account);
        if(!valid) return false;
        return account.withdrawMoney(value);
    }

    public boolean owner(BankAccount account) {
        boolean valid = false;
        for (BankAccount bankAccount : accounts) {
            if (bankAccount.getId() == account.getId()) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    //--------------Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalId() {
        return personalId;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }
}
