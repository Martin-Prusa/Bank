package com.company.presentation;

import com.company.logic.Bank;
import com.company.logic.BankAccount;
import com.company.logic.World;

import java.util.ArrayList;
import java.util.Scanner;

public class Presentation {
    private final World logic;
    private final Scanner sc;

    public Presentation() {
        this.logic = new World(true);
        sc = new Scanner(System.in);
    }

    public void startSelect() {
        while(true) {
            System.out.println();
            System.out.println("-----------------Menu--------------");
            System.out.println("Zadej, co chceš udělat: ");
            System.out.println("1. Založit banku");
            System.out.println("2. Vytvořit osobu");
            System.out.println("3. Přihlásit se");
            System.out.println("4. Seznam bank");
            System.out.println("5. Seznam osob");
            System.out.println("6. Převod peněz z jednoho účtu na jiný účet");
            System.out.println("7 nebo \"exit\". Ukončit aplikaci");
            int num = getNumber(1, 7);
            if(num == 1) createBank();
            else if(num == 2) createPerson();
            else if(num == 3) login();
            else if(num == 4) showBanks();
            else if(num == 5) showPeople();
            else if(num == 6) transfer();
            else {
                logic.close();
                break;
            }
        }
    }

    private void login() {
        System.out.println();
        System.out.println("-----------Přihlásit se-------------");
        System.out.println();
        System.out.println("Zadej svoje rodné číslo");
        String rodneCislo = sc.nextLine();
        if(logic.isPeopleRegistered(rodneCislo) == null) System.out.println("Toto rodné číslo není v databázi");
        else {
            this.logic.setLoginPerson(logic.isPeopleRegistered(rodneCislo));
            loginMenu();
        }
    }

    private void loginMenu() {
        while(true) {
            System.out.println();
            System.out.println("-----------------Menu 2--------------");
            System.out.println("Zadej, co chceš udělat: ");
            System.out.println("1. Založit účet");
            System.out.println("2. Zobrazit moje účty");
            System.out.println("3. Přidat peníze na účet");
            System.out.println("4. Vybrat peníze z účtu");
            System.out.println("5. Zrušit účet");
            System.out.println("6. Odhlásit se");
            int num = getNumber(1, 6);
            if(num == -1) return;
            else if(num == 1) createAccount();
            else if(num == 2) showAccounts(this.logic.getLoginPerson().getAccounts());
            else if(num == 3) addMoneyToAccount();
            else if(num == 4) withdrawMoneyFromAccount();
            else if(num == 5) deleteAccount();
            else if(num == 6) {
                this.logic.setLoginPerson(null);
                break;
            }
        }
    }

    private void deleteAccount() {
        System.out.println();
        System.out.println("---------------------------Zrušení účtu------------------------------");
        showAccounts(this.logic.getLoginPerson().getAccounts());
        System.out.println("Zadej číslo účtu, který chceš zrušit. (exit = zpět)");
        int num = getNumber(1, this.logic.getLoginPerson().getAccounts().size())-1;
        Bank b = this.logic.getBankById(Integer.parseInt((this.logic.getLoginPerson().getAccounts().get(num).getId()+"").substring(0,3)));
        if(num == -2) return;
        this.logic.getLoginPerson().deleteAccount(this.logic.getLoginPerson().getAccounts().get(num), b);
    }

    private void createAccount() {
        System.out.println();
        System.out.println("---------------------------Založení účtu------------------------------");
        showBanks();
        System.out.println("Vyber banku, u které chceš založit účet. (seznam je nad touto zprávou) (exit = zpět)");
        int num = getNumber(1, logic.getBanks().size());
        if(num == -1) return;
        this.logic.getLoginPerson().createAccount(this.logic.getBanks().get(num-1));
    }

    private void showAccounts(ArrayList<BankAccount> accounts) {
        System.out.println();
        System.out.println("---------------------------Přehled účtů------------------------------");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(i+1+". "+accounts.get(i).getId()+" Zůstatek: "+accounts.get(i).getBalance()+" Banka: "+this.logic.getBankById(Integer.parseInt((accounts.get(i).getId()+"").substring(0,3))).getName());
        }
    }

    private void addMoneyToAccount() {
        System.out.println();
        System.out.println("---------------------------Přidat peníze na účet------------------------------");
        showAccounts(this.logic.getLoginPerson().getAccounts());
        System.out.println("Vyber účet, na který chceš vložit peníze. (seznam je nad touto zprávou) (exit = zpět)");
        int ucet = getNumber(1, this.logic.getLoginPerson().getAccounts().size())-1;
        if(ucet == -2) return;
        System.out.println("Kolik chceš vložit na účet? (exit = zpět)");
        int value = getNumber(1, Integer.MAX_VALUE);
        if(value == -1) return;
        boolean valid = this.logic.getLoginPerson().addMoneyToAccount(this.logic.getLoginPerson().getAccounts().get(ucet), value);
        if(!valid) System.out.println("Chyba při přidávání peněz.");
        else System.out.println("Přidáno na účet");
    }

    private void withdrawMoneyFromAccount() {
        System.out.println();
        System.out.println("---------------------------Vybrat peníze z účtu------------------------------");
        showAccounts(this.logic.getLoginPerson().getAccounts());
        System.out.println("Vyber účet, ze kterého chceš vybrat peníze.. (seznam je nad touto zprávou) (exit = zpět)");
        int ucet = getNumber(1, this.logic.getLoginPerson().getAccounts().size())-1;
        if(ucet == -2) return;
        System.out.println("Kolik chceš vybrat? (exit = zpět)");
        int value = getNumber(1, Integer.MAX_VALUE);
        if(value == -1) return;
        boolean a = this.logic.getLoginPerson().withdrawMoneyFromAccount(this.logic.getLoginPerson().getAccounts().get(ucet), value);
        if(!a) System.out.println("Nedostatek peněz na účtu");
        else System.out.println("Vybráno");
    }


    private void createBank() {
        System.out.println();
        System.out.println("-----------Založit banku-------------");
        System.out.println();
        System.out.println("Zadej název banky, kterou chceš založit: ");
        boolean a = logic.createBank(sc.nextLine());
        if(!a) System.out.println("Banku se nepovedlo založit. Tento název již nějaká banka používá");
        else System.out.println("Banka úspěšně založena.");
    }

    private void createPerson() {
        System.out.println();
        System.out.println("-----------Vytvořit osobu-------------");
        System.out.println();
        System.out.println("Zadej jméno osoby, kterou chceš vytvořit: ");
        String name = sc.nextLine();
        System.out.println("Zadej rodné číslo osoby, kterou chceš vytvořit: ");
        String pId = sc.nextLine();
        System.out.println("Muž? (y/n)");
        boolean male = sc.nextLine().equalsIgnoreCase("y");

        boolean a = logic.createPerson(name, pId, male);
        if(!a) System.out.println("Nevalidní údaje!");
        else System.out.println("Osoba byla úspěšně vytvořena");
    }

    private void showBanks() {
        System.out.println();
        System.out.println("-----------Seznam bank------------");
        for (int i = 0; i < this.logic.getBanks().size(); i++) {
            System.out.println(i+1+". "+this.logic.getBanks().get(i).getName()+" ("+this.logic.getBanks().get(i).getId()+")");
        }
    }

    private void showPeople() {
        System.out.println();
        System.out.println("-----------Seznam osob------------");
        for (int i = 0; i < this.logic.getPeople().size(); i++) {
            System.out.println(i+1+". "+this.logic.getPeople().get(i).getName()+" ("+this.logic.getPeople().get(i).getPersonalId()+")");
        }
    }

    private void transfer() {
        System.out.println();
        System.out.println("----------------Převod peněz------------------");
        showBanks();
        System.out.println("Vyber banku, u které je založen účet, ze kterého chceš poslat peníze (exit = zpět)");
        int fromBank  = getNumber(1, this.logic.getBanks().size())-1;
        if(fromBank == -2) return;
        showAccounts(this.logic.getBanks().get(fromBank).getAccounts());
        System.out.println("Vyber účet, ze kterého chceš poslat peníze (exit = zpět)");
        int fromAccount = getNumber(1, this.logic.getBanks().get(fromBank).getAccounts().size())-1;
        if(fromAccount == -2) return;

        showBanks();
        System.out.println("Vyber banku, u které je založen účet, na který chceš poslat peníze (exit = zpět)");
        int toBank  = getNumber(1, this.logic.getBanks().size())-1;
        if(toBank == -2) return;
        showAccounts(this.logic.getBanks().get(toBank).getAccounts());
        System.out.println("Vyber účet, na který chceš poslat peníze (exit = zpět)");
        int toAccount = getNumber(1, this.logic.getBanks().get(toBank).getAccounts().size())-1;
        if(toAccount == -2) return;
        System.out.println("Zadej, kolik chceš poslat peněz. (exit = zpět)");
        int money = getNumber(1, Integer.MAX_VALUE);
        if(money == -1) return;

        boolean complete = this.logic.getBanks().get(fromBank).transferMoney(this.logic.getBanks().get(fromBank).getAccounts().get(fromAccount), this.logic.getBanks().get(toBank).getAccounts().get(toAccount), money);
        if(!complete) System.out.println("Nepodařilo se převést peníze. Na účtu není dostatek peněz.");
        else System.out.println("Peníze úspěšně převedeny");
    }


    private int getNumber(int min, int max) {
        try {
            String in = sc.nextLine();
            if(in.equals("exit")) return -1;
            int num = Integer.parseInt(in);
            if(num >= min && max >= num) return num;
        } catch (Exception ignored) {}
        System.out.println("Neplatné číslo, zadej číslo od "+min+" do "+max);
        return getNumber(min, max);
    }
}
