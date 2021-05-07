package com.company.logic;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Person implements Serializable {
    private String name;
    private String personalId;
    private boolean male;

    public Person(String name, String personalId, boolean male) {
        this.name = name;
        this.personalId = personalId;
        this.male = male;
    }

    //--------------Methods
    public void createAccount(Bank bank) {
        bank.createAccount(this);
    }

    public void deleteAccount(BankAccount account, Bank bank) {
        bank.removeAccount(account.getId());
    }

    public boolean addMoneyToAccount(BankAccount account, int value, ArrayList<Bank> list) {
        boolean valid = this.owner(account, list);
        if(!valid) return false;
        account.addMoney(value);
        return true;
    }

    public boolean withdrawMoneyFromAccount(BankAccount account, int value, ArrayList<Bank> list) {
        boolean valid = this.owner(account, list);
        if(!valid) return false;
        return account.withdrawMoney(value);
    }

    public boolean owner(BankAccount account, ArrayList<Bank> list) {
        boolean valid = false;
        for (BankAccount bankAccount : personAccounts(list)) {
            if (bankAccount.getId() == account.getId()) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    public ArrayList<BankAccount> personAccounts(ArrayList<Bank> list) {
        ArrayList<BankAccount> ac = new ArrayList<>();
        for (Bank bank : list) {
            for (BankAccount account : bank.getAccounts()) {
                if(account.getOwner().getPersonalId().equals(this.getPersonalId())) ac.add(account);
            }
        }
        return ac;
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
}
