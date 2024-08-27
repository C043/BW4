package Fragnito;

import Fragnito.dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4-Team-5");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        DistributoriDAO dd = new DistributoriDAO(em);
        UtenteDAO ud = new UtenteDAO(em);
        TesseraDAO td = new TesseraDAO(em);
        MezziDAO md = new MezziDAO(em);
        TrattaDAO trd = new TrattaDAO(em);
        ManutenzioneDAO mand = new ManutenzioneDAO(em);
        BigliettiDAO bd = new BigliettiDAO(em);
        ViaggiDAO vd = new ViaggiDAO(em);

/*
        bd.save(new Abbonamento(dd.getDistributoreById(UUID.fromString("94f4b7f7-3e06-42ee-b727-ccbcc2441828")), td.findById(UUID.fromString("6a12a698-c1a7-4e7d-9627-eafa02b48aac")), PeriodoAbbonamento.SETTIMANALE));
*/

        System.out.println(bd.contaBigliettiTotali());

        em.close();
        emf.close();
    }
}
