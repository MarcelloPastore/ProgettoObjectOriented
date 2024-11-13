package it.unimol.gioco.ui;

import it.unimol.gioco.app.board.CellaAssassino;

import java.util.List;
import java.util.Scanner;

public class GestoreMenu {
    private GiocoUI ui = new GiocoUI();

    public String sceltaNomeGiocatore() {
        Scanner scanner = new Scanner(System.in);
        ui.stampaRichiestaNomeGiocatore();
        return scanner.nextLine();
    }
    public int sceltaClasseGiocatore() {
        Scanner scanner = new Scanner(System.in);
        ui.scegliClasse1();
        return scanner.nextInt();
    }

    public int sceltaClasseAssassino() {
        Scanner scanner = new Scanner(System.in);
        ui.stampaRichiestaCellaInizialeAssassino();
        return scanner.nextInt();
    }

    public String sceltaClasseInvestigatore() {
        Scanner scanner = new Scanner(System.in);
        ui.stampaRichiestaCellaInizialeInvestigatore();
        return scanner.nextLine();
    }

    public int menuPrincipale(){
        Scanner scanner = new Scanner(System.in);
        ui.stampaMenuPrincipale();
        return scanner.nextInt();
    }

    public int[] sceltaObiettivi(){
        int[] obiettivi = new int[4];
        Scanner scanner = new Scanner(System.in);
        ui.stampaScegliObiettivi1();
        for (int i = 0; i < obiettivi.length; i++) {
            ui.stampaScegliObiettivi2();
            obiettivi[i] = scanner.nextInt();
        }
        return obiettivi;
    }
}
