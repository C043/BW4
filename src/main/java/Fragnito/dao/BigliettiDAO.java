package Fragnito.dao;

import Fragnito.entities.Timbrabile;
import Fragnito.entities.Viaggio;
import Fragnito.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.time.LocalDate;
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

    public void vidimaBiglietto(UUID id, Viaggio viaggio) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query updateQuery = em.createQuery("UPDATE Biglietto b SET b.dataVidimazione = :now, b.viaggio = :viaggio WHERE b.id = :id AND b.dataVidimazione IS NULL").setParameter("now", LocalDate.now()).setParameter("viaggio", viaggio).setParameter("id", id);
        int numModifiche = updateQuery.executeUpdate();
        transaction.commit();
        if (numModifiche < 1) System.out.println("Biglietto non valido!");
        else System.out.println("Biglietto vidimato con successo!");
    }

    public Number contaVidimazioniSuMezzo(UUID mezzoId) {
        return ((Number) em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.viaggio.mezzo.id = :mezzoId").setParameter("mezzoId", mezzoId).getSingleResult()).intValue();
    }

    public Number contaVidimazioniRangeTempo(LocalDate start, LocalDate end) {
        return ((Number) em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataVidimazione BETWEEN :startDate AND :endDate").setParameter("startDate", start).setParameter("endDate", end).getSingleResult()).intValue();
    }
}
