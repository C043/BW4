package Fragnito.dao;

import Fragnito.entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.TransactionalException;

public class MezziDAO {
    EntityManager em;

    public MezziDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo mezzo) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(mezzo);
            transaction.commit();
            System.out.println("Veicolo di trasporto n. " + mezzo.getId() + " aggiunto con successo!");
        } catch (TransactionalException te) {
            System.err.println(te.getMessage());
        }
    }

    public Mezzo findById(long id) {
        Mezzo found = 
        return em.find(Mezzo.class, id);
    }

    public void deleteById(long id) {
        Mezzo found = this.findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Veicolo di trasporto n. " + id + " eliminato dal db.");
        } else {
            System.out.println("Veicolo di trasporto n. " + id + " non trovato.");
        }
    }

}
