package Fragnito.dao;

import Fragnito.entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.TransactionalException;

import java.util.UUID;

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

    public Mezzo findById(UUID id) {
        Mezzo found = em.find(Mezzo.class, id);
        if (found != null) {
            System.out.println("Veicolo di trasporto n. " + id + " trovato.");
        } else {
            System.out.println("Veicolo di trasporto n. " + id + " non trovato.");
        }
        return found;
    }

    public void deleteById(UUID id) {
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
