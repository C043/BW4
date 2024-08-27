package Fragnito;

import Fragnito.dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
        BigliettiDAO bd = new BigliettiDAO(em);
        ViaggiDAO vd = new ViaggiDAO(em);

        System.out.println(bd.getAbbonamentoById(UUID.fromString("2e2e7299-f4a8-49b7-83ff-7e6b15f07c64")));

//        md.save(new Mezzo(TipoMezzo.TRAM, trd.findById(UUID.fromString("09919180-db45-41ef-91ec-94971fe3e0a0"))));

        // rinnovo tessere con id tessera -> da sistemare
//        System.out.print("Inserisci l'ID della tessera da vidimare: ");
//        String tesseraIdInput = scanner.nextLine().trim();
//
//        Tessera tesseraDaControllare = td.findById(UUID.fromString(tesseraIdInput));
//
//        if (tesseraDaControllare.getDataScadenza().isBefore(LocalDate.now())) {
//            //è scaduta chiedere all'utente cosa vuole fare
//
//            System.out.println("Tessera scaduta, rinnovare? (S/N)");
//            String res = scanner.nextLine();
//
//            if (res.equalsIgnoreCase("S")) {
//                //chiamare DAO per rinnovo tessera
//                tesseraDaControllare.setDataScadenza(tesseraDaControllare.getDataScadenza().plusYears(1));
//                td.rinnovaTessera(tesseraDaControllare);
//            }
//        } else {
//            System.out.println("Tessera valida!");
//        }

        em.close();
        emf.close();
    }
}


