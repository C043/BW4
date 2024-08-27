package Fragnito.dao;

import Fragnito.entities.Manutenzione;
import Fragnito.entities.Mezzo;
import Fragnito.enumClass.StatoMezzo;
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
        ManutenzioneDAO md = new ManutenzioneDAO(em);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(mezzo);
            transaction.commit();
            System.out.println("Veicolo di trasporto n. " + mezzo.getId() + " aggiunto con successo!");
            md.save(new Manutenzione(StatoMezzo.IN_SERVIZIO, "Inizio", findById(mezzo.getId())));
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
        EntityTransaction transaction = em.getTransaction();
        Mezzo found = this.findById(id);
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("Veicolo di trasporto n. " + id + " eliminato dal db.");
    }

    public void filterMezziAndUpdate(UUID id, StatoMezzo statoMezzo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.createQuery("UPDATE Mezzo m SET m.statoMezzo = :stato WHERE m.id = :id").setParameter("stato", statoMezzo).setParameter("id", id).executeUpdate();
        transaction.commit();
        System.out.println("Modifica avvenuta con successo!");
    }

}
