package Fragnito;

import Fragnito.dao.*;
import Fragnito.entities.Utente;
import Fragnito.exceptions.InvalidInputException;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
                            ud.save(new Utente(nome, cognome, dataNascita, email, password));
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
}


