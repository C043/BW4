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

        /*ud.save(new Utente("Mario", "Fragnito", LocalDate.of(1999, 5, 7), "mariofragnito@gmail.com", "1234"));

        System.out.println(ud.login("mariofragnito@gmail.com", "1234"));*/

        /*dd.generateNDistributors(10);*/


/*
        bd.save(new Abbonamento(dd.getDistributoreById(UUID.fromString("1cf7fa10-6d8c-463e-8bb1-8241a5de4fd2")), td.findById(UUID.fromString("b5a6b976-bef7-4bfe-8c83-d9c3f1548d07")), PeriodoAbbonamento.SETTIMANALE));
*/

        System.out.println(bd.isAbbonamentoValido(UUID.fromString("b5a6b976-bef7-4bfe-8c83-d9c3f1548d07"), UUID.fromString("97ff98f8-0646-4100-a343-120df9a4484f")));


        em.close();
        emf.close();
    }
}


