package Fragnito.dao;

import Fragnito.entities.Viaggio;
import Fragnito.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class ViaggiDAO {
    private final EntityManager em;

    public ViaggiDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Viaggio viaggio) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(viaggio);
        transaction.commit();
        System.out.println("Viaggio " + viaggio.getId() + " salvato con successo!");
    }

    public Viaggio getViaggioById(UUID id) {
        Viaggio found = em.find(Viaggio.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void delete(UUID id) {
        Viaggio found = getViaggioById(id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("Viaggio " + found.getId() + " eliminato con successo!");
    }


}
