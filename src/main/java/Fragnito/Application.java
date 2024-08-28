package Fragnito;

import Fragnito.dao.*;
import Fragnito.entities.*;
import Fragnito.enumClass.PeriodoAbbonamento;
import Fragnito.exceptions.EmailAlreadyExistException;
import Fragnito.exceptions.InvalidInputException;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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
                    System.out.println("Login:");
                    System.out.println("Inserisci la tua email");
                    String email = scanner.nextLine();
                    if (Objects.equals(email, "mellon")) adminMenu(scanner, bd, dd, vd);
                    else {
                        System.out.println("Inserisci la tua password");
                        String password = scanner.nextLine();
                        try {
                            Utente user = ud.login(email, password);
                            System.out.println(user);
                            // Controllo tessera
                            boolean check = td.checkTessera(user.getTessera().getId());
                            if (check) {
                                tesseraValida(user, scanner, dd, bd, td, trd, vd, ud);
                            } else {
                                System.out.println("Tessera scaduta, rinnovare? (10$)");
                                System.out.println("La tessera è richiesta per viaggiare con EPI-Trasporti.");
                                System.out.println("1. Sì");
                                System.out.println("2. Esci");
                                System.out.println("Premi il numero corrispondente");
                                String risposta = scanner.nextLine();
                                switch (risposta) {
                                    case "1": {
                                        td.rinnovaTessera(user.getTessera());
                                        tesseraValida(user, scanner, dd, bd, td, trd, vd, ud);
                                        break;
                                    }
                                    case "2": {
                                        System.out.println("Arrivederci");
                                        break;
                                    }
                                    default: {
                                        throw new InvalidInputException();
                                    }
                                }
                            }

                        } catch (NoResultException e) {
                            System.out.println("Utente non trovato");
                        } catch (InvalidInputException e) {
                            System.out.println("Input non valido, non puoi inserire una stringa vuota");
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
                            if (ud.checkUtente(email)) throw new EmailAlreadyExistException();
                            if (Objects.equals(email, "")) throw new InvalidInputException();
                            System.out.println("Inserisci la tua password");
                            String password = scanner.nextLine();
                            if (Objects.equals(password, "")) throw new InvalidInputException();
                            Utente user = new Utente(nome, cognome, dataNascita, email, password);
                            ud.save(user);
                            tesseraValida(ud.findById(user.getId()), scanner, dd, bd, td, trd, vd, ud);
                        } catch (DateTimeParseException e) {
                            System.out.println("Input non valido, inserisci una data nel formato yyyy-mm-dd");
                        } catch (InvalidInputException e) {
                            System.out.println("Input non valido, non puoi inserire una stringa vuota");
                        } catch (EmailAlreadyExistException e) {
                            System.out.println("Email già registrata, inseriscine un'altra");
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

    private static void tesseraValida(Utente utente, Scanner scanner, DistributoriDAO dd, BigliettiDAO bd, TesseraDAO td, TrattaDAO trd, ViaggiDAO vd, UtenteDAO ud) {
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
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Input non valido, inserisci il numero corrispondente");
                    }
                    break;
                }
                case "2": {
                    if (td.isAbbonamentoPresent(ud.findById(utente.getId()).getTessera().getId()))
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
                case "3": {
                    if (td.isAbbonamentoPresent(td.getTesseraByUtente(utente.getId()).getId())) {
                        viaggia(scanner, trd, vd);
                        System.out.println("Buon viaggio!");
                    } else if (!bd.getBigliettiNonVidimati(td.getTesseraByUtente(utente.getId()).getId()).isEmpty()) {
                        UUID viaggioID = viaggia(scanner, trd, vd);
                        bd.vidimaBiglietto(bd.getBigliettiNonVidimati(utente.getTessera().getId()).getFirst().getId(), vd.getViaggioById(viaggioID));
                        System.out.println("Buon viaggio!");
                    } else
                        System.out.println("Nessun biglietto o abbonamento disponibile sulla tessera, comprane uno prima");
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

    private static UUID viaggia(Scanner scanner, TrattaDAO trd, ViaggiDAO vd) {
        Random rand = new Random();
        System.out.println("Dove vuoi andare?");
        System.out.println("Elenco distributori disponibili:");
        List<Tratta> listaTratte = trd.getAllTratte();
        for (int i = 0; i < listaTratte.size(); i++) {
            System.out.println(i + 1 + ". " + listaTratte.get(i).getCapolinea());
        }
        System.out.println("Digita il numero corrispondente");

        UUID viaggioId = null;
        try {
            int tratta = Integer.parseInt(scanner.nextLine());
            for (int i = 1; i <= listaTratte.size(); i++) {
                if (tratta == i) {
                    Viaggio viaggio = new Viaggio(listaTratte.get(i - 1).getMezzi().getFirst(), LocalDate.now(), rand.nextInt(listaTratte.get(i - 1).getTempoPrevisto() - 10, listaTratte.get(i - 1).getTempoPrevisto() + 10));
                    vd.save(viaggio);
                    viaggioId = viaggio.getId();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido, inserisci il numero corrispondente");
        }
        return viaggioId;
    }

    private static void adminMenu(Scanner scanner, BigliettiDAO bd, DistributoriDAO dd, ViaggiDAO vd) {
        boolean quitAdmin = false;
        while (!quitAdmin) {
            System.out.println("Benvenuto admin");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1. Visualizza il numero di biglietti emessi in totale");
            System.out.println("2. Visualizza il numero di biglietti emessi in un range di tempo");
            System.out.println("3. Visualizza tutti i biglietti emessi da un distributore");
            System.out.println("4. Controlla l'abbonamento di un passeggero");
            System.out.println("5. Visualizza il numero di tutti i biglietti vidimati su un mezzo");
            System.out.println("6. Visualizza il numero di biglietti vidimati in un range di tempo");
            System.out.println("7. Visualizza il numero di viaggi di un mezzo");
            System.out.println("8. Calcola il tempo medio effettivo di percorrenza di un mezzo");
            System.out.println("9. Esci");
            System.out.println("Digita il numero corrispondente");
            String opzione = scanner.nextLine();
            try {
                switch (opzione) {
                    case "1": {
                        System.out.println("Il numero di biglietti e abbonamenti emessi in totale è:");
                        System.out.println(bd.contaBigliettiTotali());
                        break;
                    }
                    case "2": {
                        try {
                            System.out.println("Inserire l'inizio del range in formato yyyy-mm-dd");
                            LocalDate inizio = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
                            System.out.println("Inserisci la fine del range in formato yyyy-mm-dd");
                            LocalDate fine = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
                            System.out.println("Il numero di biglietti emessi dal " + inizio + " al " + fine + " è: " + bd.contaBigliettiRangeTempo(inizio, fine));
                        } catch (DateTimeParseException e) {
                            System.out.println("Input non valido, inserisci una data nel formato yyyy-mm-dd");
                        }
                        break;
                    }
                    case "3": {
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
                                    System.out.println("Il numero di biglietti emessi dal distributore " + listaDistributori.get(i - 1).getNome() + " è: " + bd.contaBigliettiPerDistributore(listaDistributori.get(i - 1).getId()));
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido, inserisci il numero corrispondente");
                        }
                        break;
                    }
                    case "4": {
                        try {
                            System.out.println("Inserisci l'id della tessera");
                            String tesseraId = scanner.nextLine();
                            System.out.println("Inserisci l'id dell'abbonamento");
                            String abbonamentoId = scanner.nextLine();
                            if (bd.isAbbonamentoValido(UUID.fromString(abbonamentoId), UUID.fromString(tesseraId))) {
                                System.out.println("L'abbonamento è valido");
                            } else {
                                System.out.println("L'abbonamento non è valido");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Id non validi");
                        }
                        break;
                    }
                    case "5": {
                        System.out.println("Inserisci l'id del mezzo");
                        try {
                            String mezzoId = scanner.nextLine();
                            System.out.println("Il numero di biglietti vidimati sul mezzo con id " + mezzoId + " è: " + bd.contaVidimazioniSuMezzo(UUID.fromString(mezzoId)));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Id non valido");
                        }
                        break;
                    }
                    case "6": {
                        try {
                            System.out.println("Inserire l'inizio del range in formato yyyy-mm-dd");
                            LocalDate inizio = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
                            System.out.println("Inserisci la fine del range in formato yyyy-mm-dd");
                            LocalDate fine = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
                            System.out.println("Il numero di biglietti vidimati dal " + inizio + " al " + fine + " è: " + bd.contaVidimazioniRangeTempo(inizio, fine));
                        } catch (DateTimeParseException e) {
                            System.out.println("Input non valido, inserisci una data nel formato yyyy-mm-dd");
                        }
                        break;
                    }
                    case "7": {
                        System.out.println("Inserisci l'id del mezzo");
                        try {
                            String mezzoId = scanner.nextLine();
                            System.out.println("Il numero di viaggi del mezzo " + mezzoId + " è: " + vd.contaViaggiPerMezzo(UUID.fromString(mezzoId)));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Id non valido");
                        }
                        break;
                    }
                    case "8": {
                        System.out.println("Inserisci l'id del mezzo");
                        try {
                            String mezzoId = scanner.nextLine();
                            System.out.println("La media dei viaggi effettivi del mezzo " + mezzoId + " è: " + vd.calcolaTempoMedio(UUID.fromString(mezzoId)));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Id non valido");
                        }
                        break;
                    }
                    case "9": {
                        System.out.println("Arrivederci admin!");
                        quitAdmin = true;
                        break;
                    }
                    default: {
                        throw new InvalidInputException();
                    }
                }
            } catch (InvalidInputException e) {
                System.out.println("Input non valido, inserisci il numero corrispondente");
            }
        }
    }
}



