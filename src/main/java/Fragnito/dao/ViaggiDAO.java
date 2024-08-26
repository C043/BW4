package Fragnito.dao;

import Fragnito.entities.Mezzo;
import Fragnito.entities.Viaggio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;

public class ViaggiDAO {
    private final EntityManager em;

    public ViaggiDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo mezzo, LocalDate giorno, Integer tempoEffettivo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(new Viaggio());
    }
}
