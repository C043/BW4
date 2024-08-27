package Fragnito.dao;

import Fragnito.entities.Timbrabile;
import Fragnito.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class BigliettiDAO {
    private final EntityManager em;

    public BigliettiDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Timbrabile timbrabile) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(timbrabile);
        transaction.commit();
        System.out.println("Biglietto / abbonamento erogato con successo!");
    }

    public Timbrabile getBigliettoById(UUID id) {
        Timbrabile found = em.find(Timbrabile.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void delete(UUID id) {
        Timbrabile found = getBigliettoById(id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("Biglietto o abbonamento " + found.getId() + " eliminato con successo!");
    }
}
