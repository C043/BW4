package Fragnito.dao;

import Fragnito.entities.Tessera;
import Fragnito.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class TesseraDAO {

    private final EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tessera tessera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(tessera);
        transaction.commit();
        System.out.println("La tessera \"" + tessera.getId() + " appartenente a " + tessera.getUtente() + "\" è stata salvata con successo!");
    }

    public Tessera findById(UUID id) {
        Tessera found = em.find(Tessera.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void findByIdAndDelete(UUID id) {
        Tessera found = this.findById(id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("La tessera \"" + found.getId() + " appartenente a " + found.getUtente() + "\" è stata eliminata con successo!");
    }

}
