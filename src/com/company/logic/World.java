package com.company.logic;

import com.company.data.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class World implements Serializable {
    private ArrayList<Person> people;
    private ArrayList<Bank> banks;
    private Person loginPerson;

    public World(boolean load) {
        if(!load) {
            people = new ArrayList<>();
            banks = new ArrayList<>();
        } else {
            World w = Data.load();
            this.people = w.people;
            this.banks = w.banks;
        }

        this.loginPerson = null;
    }

    public Bank getBankById(int id) {
        for (Bank bank : banks) {
            if(bank.getId() == id) return bank;
        }
        return null;
    }

    public boolean createBank(String name) {
        for (Bank bank : banks) {
            if (bank.getName().equals(name)) return false;
        }
        banks.add(new Bank(name, getNewBankID()));
        return true;
    }

    public boolean createPerson(String name, String personalId, boolean male) {
        for (Person person : people) {
            if (person.getPersonalId().equals(personalId)) return false;
        }

        people.add(new Person(name, personalId, male));

        return true;
    }

    private int getNewBankID() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Bank bank : this.banks) {
            ids.add(bank.getId());
        }

        Random rand = new Random();
        int id = rand.nextInt(899) + 100;
        while (ids.contains(id)) {
            id = rand.nextInt(899) + 100;
        }
        return id;
    }

    public Person isPeopleRegistered(String pId) {
        for (Person person : this.people) {
            if (pId.equals(person.getPersonalId())) return person;
        }
        return null;
    }

    public void close() {
        Data.save(this);
    }


    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public ArrayList<Bank> getBanks() {
        return banks;
    }

    public void setBanks(ArrayList<Bank> banks) {
        this.banks = banks;
    }

    public Person getLoginPerson() {
        return loginPerson;
    }

    public void setLoginPerson(Person loginPerson) {
        this.loginPerson = loginPerson;
    }
}
