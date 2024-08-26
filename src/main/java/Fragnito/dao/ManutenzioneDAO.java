package Fragnito.dao;

import Fragnito.entities.Manutenzione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.TransactionalException;

public class ManutenzioneDAO {
    EntityManager em;

    public ManutenzioneDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Manutenzione manutenzione) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(manutenzione);
            transaction.commit();
            System.out.println("Manutenzione n. " + manutenzione.getId() + " aggiunta con successo!");
        } catch (TransactionalException te) {
            System.err.println(te.getMessage());
        }
    }

    public Manutenzione findById(long id) {
        return em.find(Manutenzione.class, id);
    }

    public void deleteById(long id) {
        Manutenzione found = this.findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Manutenzione n. " + id + " rimossa dal db.");
        } else {
            System.out.println("Manutenzione n. " + id + " non trovata.");
        }
    }

}