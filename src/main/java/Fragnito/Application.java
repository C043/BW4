package Fragnito;

import Fragnito.dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.UUID;

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

        bd.vidimaBiglietto(UUID.fromString("9e3fbd03-a0ad-4eda-82a7-8a457683994c"), vd.getViaggioById(UUID.fromString("9be8e175-f366-4d26-843d-3da30adb409f")));

        System.out.println(bd.contaVidimazioniSuMezzo(UUID.fromString("80282f3a-4204-4529-ac4c-ee09e1040215")));

        em.close();
        emf.close();
    }
}
