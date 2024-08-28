package Fragnito.dao;

import Fragnito.entities.Mezzo;
import Fragnito.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.TransactionalException;

import java.util.UUID;

public class TrattaDAO {
    EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta tratta) {
        MezziDAO md = new MezziDAO(em);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(tratta);
            transaction.commit();
            System.out.println("Tratta n. " + tratta.getId() + " aggiunta con successo!");
            // WIP
            md.save(new Mezzo());
        } catch (TransactionalException te) {
            System.err.println(te.getMessage());
        }
    }

/*
    public void generateNTratte
*/

    public Tratta findById(UUID id) {
        Tratta tratta = em.find(Tratta.class, id);
        if (tratta == null) {
            throw new EntityNotFoundException("La Tratta con ID " + id + " non è stata trovata.");
        }
        return tratta;
    }


    public void deleteById(UUID id) {
        Tratta found = this.findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Tratta n. " + id + " eliminata dal db.");
        } else {
            System.out.println("Tratta n. " + id + " non trovata.");
        }
    }


}