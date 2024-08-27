package Fragnito;

import Fragnito.dao.*;
import Fragnito.entities.Tessera;
import Fragnito.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4-Team-5");

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        EntityManager em = emf.createEntityManager();

        DistributoriDAO dd = new DistributoriDAO(em);
        UtenteDAO ud = new UtenteDAO(em);
        TesseraDAO td = new TesseraDAO(em);
        MezziDAO md = new MezziDAO(em);
        TrattaDAO trd = new TrattaDAO(em);
        ManutenzioneDAO mand = new ManutenzioneDAO(em);

//        md.save(new Mezzo(TipoMezzo.TRAM, trd.findById(UUID.fromString("09919180-db45-41ef-91ec-94971fe3e0a0"))));


        List<Utente> utenti = ud.generaUtenti(5);

        if (utenti == null)
            return;


        List<Tessera> tessereAssociate = td.associaUtente(utenti);

        System.out.print("Inserisci l'ID della tessera da vidimare: ");
        String tesseraIdInput = scanner.nextLine().trim();

        UUID tesseraIdInserita; // per convertire il dato inserito in UUID

        try {
            tesseraIdInserita = UUID.fromString(tesseraIdInput);
        } catch (IllegalArgumentException e) {
            System.out.println("ID tessera non valido. Assicurati che sia nel formato UUID.");
            return;
        }

        Tessera tessera = em.find(Tessera.class, tesseraIdInserita); //confronta e trova la tessera con id inserito

        if (tessera.getDataScadenza().isBefore(LocalDate.now())) {
            System.out.println("La tessera di " + tessera.getUtente().getNome() + " " +
                    tessera.getUtente().getCognome() + " è scaduta il " + tessera.getDataScadenza() + ".");
            System.out.print("Vuoi rinnovare la tessera? (si/no): ");
            String risposta = scanner.nextLine().trim().toLowerCase();

            switch (risposta) {
                case "si": // rinnova la tessera
                    LocalDate nuovaDataEmissione = LocalDate.now();
                    tessera.setDataEmissione(nuovaDataEmissione);
                    tessera.setDataScadenza(nuovaDataEmissione.plusMonths(12));

                    EntityTransaction transaction = em.getTransaction();
                    transaction.begin();
                    em.merge(tessera);
                    transaction.commit();

                    System.out.println("Tessera rinnovata! Nuova data di emissione: " +
                            tessera.getDataEmissione() + ", Nuova data di scadenza: " +
                            tessera.getDataScadenza());
                    break;

                case "no":
                    System.out.println("La tessera non è stata rinnovata.");
                    break;

                default: // input non valido

                    System.out.println("Opzione non valida. Per favore, rispondi con 'si' o 'no'.");
                    break;
            }
        } else {
            System.out.println("La tessera non è scaduta. Data di scadenza: " + tessera.getDataScadenza());
        }


        em.close();
        emf.close();
    }
}


