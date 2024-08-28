package Fragnito;

import Fragnito.dao.*;
import Fragnito.entities.Abbonamento;
import Fragnito.entities.Biglietto;
import Fragnito.entities.Distributore;
import Fragnito.entities.Utente;
import Fragnito.enumClass.PeriodoAbbonamento;
import Fragnito.exceptions.InvalidInputException;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4-Team-5");

    public static void main(String[] args) {
        Faker faker = new Faker();
        Scanner scanner = new Scanner(System.in);

        EntityManager em = emf.createEntityManager();

        DistributoriDAO dd = new DistributoriDAO(em);
        UtenteDAO ud = new UtenteDAO(em);
        TesseraDAO td = new TesseraDAO(em);
        MezziDAO md = new MezziDAO(em);
        TrattaDAO trd = new TrattaDAO(em);
        ManutenzioneDAO mand = new ManutenzioneDAO(em);
        BigliettiDAO bd = new BigliettiDAO(em);
        ViaggiDAO vd = new ViaggiDAO(em);

     /*   trd.generateNTratte(5);
        dd.generateNDistributors(5);*/


        boolean appOff = false;
        while (!appOff) {
            System.out.println("Benvenuto su EPI-Trasporti!");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1. Login");
            System.out.println("2. Sign in");
            System.out.println("3. Esci");
            System.out.println("Premi il numero corrispondente");
            String accesso = scanner.nextLine();
            switch (accesso) {
                // Login
                case "1": {
                    boolean utenteTrovato = false;
                    while (!utenteTrovato) {
                        System.out.println("Login:");
                        System.out.println("Inserisci la tua email");
                        String email = scanner.nextLine();
                        System.out.println("Inserisci la tua password");
                        String password = scanner.nextLine();
                        try {
                            Utente user = ud.login(email, password);
                            System.out.println(user);
                            utenteTrovato = true;
                            // Controllo tessera
                            boolean check = td.checkTessera(user.getTessera().getId());
                            System.out.println(check);
                            if (check) {
                                tesseraValida(user, scanner, dd, bd, td);
                            }

                        } catch (NoResultException e) {
                            System.out.println("Utente non trovato");
                        }
                    }
                    break;
                }
                // Registrazione
                case "2": {
                    System.out.println("Registrazione:");
                    System.out.println("Inserisci il tuo nome");
                    String nome = scanner.nextLine();
                    System.out.println("Inserisci il tuo cognome");
                    String cognome = scanner.nextLine();
                    boolean inputValido = false;
                    while (!inputValido) {
                        System.out.println("Inserisci la tua data di nascita in formato yyyy-mm-dd");
                        try {
                            LocalDate dataNascita = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
                            inputValido = true;
                            System.out.println("Inserisci la tua email");
                            String email = scanner.nextLine();
                            if (Objects.equals(email, "")) throw new InvalidInputException();
                            System.out.println("Inserisci la tua password");
                            String password = scanner.nextLine();
                            if (Objects.equals(password, "")) throw new InvalidInputException();
                            Utente user = new Utente(nome, cognome, dataNascita, email, password);
                            ud.save(user);
                            tesseraValida(user, scanner, dd, bd, td);
                        } catch (DateTimeParseException e) {
                            System.out.println("Input non valido, inserisci una data nel formato yyyy-mm-dd");
                        } catch (InvalidInputException e) {
                            System.out.println("Input non valido, non puoi inserire una stringa vuota");
                        }
                    }
                    break;
                }
                // Uscita
                case "3": {
                    System.out.println("Arrivederci");
                    appOff = true;
                    break;
                }
                default: {
                    System.out.println("Input non valido, inserisci il numero corrispondente");
                }
            }
        }

        em.close();
        emf.close();
    }

    private static void tesseraValida(Utente utente, Scanner scanner, DistributoriDAO dd, BigliettiDAO bd, TesseraDAO td) {
        boolean exitApp = false;
        while (!exitApp) {
            System.out.println("Cosa desideri fare?");
            System.out.println("1. Compra un biglietto (5$)");
            System.out.println("2. Compra un abbonamento");
            System.out.println("3. Viaggia");
            System.out.println("4. La tua tessera");
            System.out.println("5. Esci");
            System.out.println("Premi il numero corrispondente");
            String opzione = scanner.nextLine();
            switch (opzione) {
                case "1": {
                    System.out.println("Elenco distributori disponibili:");
                    List<Distributore> listaDistributori = dd.getAllDistributors();
                    for (int i = 0; i < listaDistributori.size(); i++) {
                        System.out.println(i + 1 + ". " + listaDistributori.get(i).getNome());
                    }
                    System.out.println("Digita il numero corrispondente");
                    try {
                        int distributore = Integer.parseInt(scanner.nextLine());
                        for (int i = 1; i <= listaDistributori.size(); i++) {
                            if (distributore == i)
                                bd.save(new Biglietto(listaDistributori.get(i - 1), utente.getTessera()));
                            else System.out.println("Distributore inesistente");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Input non valido, inserisci il numero corrispondente");
                    }
                    break;
                }
                case "2": {
                    if (td.isAbbonamentoPresent(utente.getTessera().getId()))
                        System.out.println("Abbonamento già presente");
                    else {
                        System.out.println("Elenco distributori disponibili:");
                        List<Distributore> listaDistributori = dd.getAllDistributors();
                        for (int i = 0; i < listaDistributori.size(); i++) {
                            System.out.println(i + 1 + ". " + listaDistributori.get(i).getNome());
                        }
                        System.out.println("Digita il numero corrispondente");
                        try {
                            int distributore = Integer.parseInt(scanner.nextLine());
                            System.out.println("Mensile o settimanale?");
                            System.out.println("1. Mensile (50$)");
                            System.out.println("2. settimanale (17$)");
                            System.out.println("Digita il numero corrispondente");
                            String periodicita = scanner.nextLine();
                            PeriodoAbbonamento periodo = PeriodoAbbonamento.SETTIMANALE;
                            if (Objects.equals(periodicita, "1")) periodo = PeriodoAbbonamento.MENSILE;
                            else if (!Objects.equals(periodicita, "1") && !Objects.equals(periodicita, "2"))
                                throw new InvalidInputException();
                            for (int i = 1; i <= listaDistributori.size(); i++) {
                                if (distributore == i)
                                    bd.save(new Abbonamento(listaDistributori.get(i - 1), utente.getTessera(), periodo));
                            }
                        } catch (NumberFormatException | InvalidInputException e) {
                            System.out.println("Input non valido, inserisci il numero corrispondente");
                        }
                    }
                    break;
                }
                case "4": {
                    System.out.println("Ecco la tua tessera:");
                    System.out.println(utente.getTessera());
                    utente.getTessera().getBiglietti().forEach(System.out::println);
                    break;
                }
                case "5": {
                    System.out.println("Arrivederci");
                    exitApp = true;
                    break;
                }
                default: {
                    System.out.println("Input non valido, inserisci il numero corrispondente");
                }
            }
        }
    }
}



