package Fragnito;

import Fragnito.dao.*;
import Fragnito.entities.Mezzo;
import Fragnito.enumClass.TipoMezzo;
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

        md.save(new Mezzo(TipoMezzo.TRAM, trd.findById(UUID.fromString("09919180-db45-41ef-91ec-94971fe3e0a0"))));

        em.close();
        emf.close();
    }
}
