package Fragnito.dao;

import Fragnito.entities.Manutenzione;
import Fragnito.enumClass.StatoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.transaction.TransactionalException;

import java.time.LocalDate;
import java.util.UUID;

public class ManutenzioneDAO {
    EntityManager em;

    public ManutenzioneDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Manutenzione manutenzione) {
        MezziDAO md = new MezziDAO(em);
        try {
            if (manutenzione.getMezzo().getStatoMezzo() == manutenzione.getStato())
                System.out.println("Mezzo già " + manutenzione.getStato());
            else {
                updateEndTime(manutenzione.getMezzo().getId());
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                em.persist(manutenzione);
                transaction.commit();
                if (manutenzione.getStato() == StatoMezzo.IN_MANUTENZIONE) {
                    System.out.println("Cambio in manutenzione");
                    md.filterMezziAndUpdate(manutenzione.getMezzo().getId(), StatoMezzo.IN_MANUTENZIONE);
                } else {
                    System.out.println("Cambio in servizio");
                    md.filterMezziAndUpdate(manutenzione.getMezzo().getId(), StatoMezzo.IN_SERVIZIO);
                }
                System.out.println("Manutenzione n. " + manutenzione.getId() + " aggiunta con successo!");
            }
        } catch (TransactionalException te) {
            System.err.println(te.getMessage());
        }
    }

    public Manutenzione findById(UUID id) {
        return em.find(Manutenzione.class, id);
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

    public void updateEndTime(UUID id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query updateQuery = em.createQuery("UPDATE Manutenzione m SET m.dataFine = :dataFine WHERE m.mezzo.id = :id AND m.dataFine IS NULL")
                .setParameter("dataFine", LocalDate.now())
                .setParameter("id", id);
        int numUpdated = updateQuery.executeUpdate();
        transaction.commit();
        if (numUpdated > 0) System.out.println("Data di fine aggiornata con successo!");
    }

}