package Fragnito.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;

public class AbbonamentoDAO {
    private EntityManager entityManager;

    public boolean isAbbonamentoValido(String numeroTessera) {
        LocalDate oggi = LocalDate.now();

        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(a) FROM Abbonamento a " +
                        "WHERE a.tessera.numero = :numeroTessera " +
                        "AND a.dataScadenza >= :oggi", Long.class);
        query.setParameter("numeroTessera", numeroTessera);
        query.setParameter("oggi", oggi);
        Long count = query.getSingleResult();
        return count > 0;
    }
}
