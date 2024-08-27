package Fragnito.dao;

import Fragnito.entities.Manutenzione;
import Fragnito.entities.Mezzo;
import Fragnito.enumClass.StatoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
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

    public void incrementaNumeroGiri(UUID id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Mezzo mezzo = findById(id);
        if (mezzo != null) {
            mezzo.setNumeroGiri(mezzo.getNumeroGiri() + 1);
            em.merge(mezzo);
            System.out.println("Numero giri aggiornato per il mezzo n. " + id);
        }
        transaction.commit();
    }

    public void aggiornaMediaTempoEffettivo(UUID mezzoId, double nuovoTempoEffettivo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Mezzo mezzo = findById(mezzoId);
        if (mezzo != null) {
            double mediaAttuale = mezzo.getMediaTempoEffettivo();
            int numeroGiri = mezzo.getNumeroGiri();
            double nuovaMedia = (mediaAttuale * numeroGiri + nuovoTempoEffettivo) / (numeroGiri + 1);
            mezzo.setMediaTempoEffettivo(nuovaMedia);
            em.merge(mezzo);
            System.out.println("Media tempo effettivo aggiornata per il mezzo n. " + mezzoId);
        }
        transaction.commit();
    }

    public long contaViaggiPerMezzo(UUID mezzoId) {
        Long count = em.createQuery("SELECT COUNT(v) FROM Viaggio v WHERE v.mezzo.id = :mezzoId", Long.class)
                .setParameter("mezzoId", mezzoId)
                .getSingleResult();
        return count;
    }

    public double calcolaTempoMedio(UUID trattaId) {
        Query query = em.createQuery("SELECT AVG(v.tempoEffettivo) FROM Viaggio v WHERE v.mezzo.tratta.id = :trattaId");
        query.setParameter("trattaId", trattaId);
        Double risultato = (Double) query.getSingleResult();
        if (risultato == null) {
            System.out.println("Nessun viaggio trovato per la tratta con ID " + trattaId);
            return 0;
        }
        return risultato;
    }

}
