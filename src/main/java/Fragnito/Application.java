package Fragnito;

import Fragnito.dao.DistributoriDAO;
import Fragnito.dao.TesseraDAO;
import Fragnito.dao.UtenteDAO;
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

        td.save(UUID.fromString("cbe2b2e5-1e20-4039-91c1-48bfd01b3739"));
        
        em.close();
        emf.close();
    }
}
