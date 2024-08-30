package Fragnito.dao;

import Fragnito.entities.Abbonamento;
import Fragnito.entities.Biglietto;
import Fragnito.entities.Timbrabile;
import Fragnito.entities.Viaggio;
import Fragnito.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;
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
    }

    public Biglietto getBigliettoById(UUID id) {
        Biglietto found = em.find(Biglietto.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public boolean getAbbonamentoById(UUID id) {
        Abbonamento found = em.find(Abbonamento.class, id);
        if (found == null)
            return false;

        return found.getDataScadenza().isAfter(LocalDate.now())
                && found.getTessera().getId().equals(id);
    }

    public List<Abbonamento> getAbbonamentoByTessera(UUID tesseraId) {
        return em.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.id = :tesseraId AND a.dataScadenza > :oggi", Abbonamento.class)
                .setParameter("tesseraId", tesseraId)
                .setParameter("oggi", LocalDate.now())
                .getResultList();
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

    public Long contaVidimazioniRangeTempo(LocalDate start, LocalDate end) {
        return em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataVidimazione BETWEEN :startDate AND :endDate", Long.class)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getSingleResult();
    }

    public Number contaBigliettiTotali() {
        return ((Number) em.createQuery("SELECT COUNT(b) FROM Timbrabile b").getSingleResult()).intValue();
    }

    public Long contaBigliettiPerDistributore(UUID distributoreId) {
        return em.createQuery("SELECT COUNT(b) FROM Timbrabile b WHERE b.distributore.id = :id", Long.class)
                .setParameter("id", distributoreId)
                .getSingleResult();
    }

    public Number contaBigliettiRangeTempo(LocalDate start, LocalDate end) {
        return ((Number) em.createQuery("SELECT COUNT(b) FROM Timbrabile b WHERE b.dataEmissione BETWEEN :startDate AND :endDate").setParameter("startDate", start).setParameter("endDate", end).getSingleResult()).intValue();
    }

    public boolean isAbbonamentoValido(UUID numeroTessera, UUID abbonamentoId) {
        LocalDate oggi = LocalDate.now();

        Long query = em.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a " +
                                "WHERE a.tessera.id = :numeroTessera " +
                                "AND a.id = :abbonamentoId " +
                                "AND a.dataScadenza >= :oggi", Long.class)
                .setParameter("numeroTessera", numeroTessera)
                .setParameter("oggi", oggi)
                .setParameter("abbonamentoId", abbonamentoId)
                .getSingleResult();
        return query > 0;
    }

    public List<Biglietto> getBigliettiNonVidimati(UUID tesseraId) {
        return em.createQuery("SELECT b FROM Biglietto b WHERE b.tessera.id = :id AND b.dataVidimazione IS NULL", Biglietto.class)
                .setParameter("id", tesseraId)
                .getResultList();
    }
}
