package Fragnito.dao;

import Fragnito.entities.Manutenzione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.TransactionalException;

import java.util.UUID;

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

    public Manutenzione findById(UUID id) {
        Manutenzione found = em.find(Manutenzione.class, id);
        if (found != null) {
            System.out.println("Manutenzione n. " + id + " trovata.");
        } else {
            System.out.println("Manutenzione n. " + id + " non trovata.");
        }
        return found;
    }

    public void deleteById(UUID id) {
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