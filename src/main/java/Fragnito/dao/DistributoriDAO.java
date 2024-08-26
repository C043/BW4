package Fragnito.dao;

import Fragnito.entities.Distributore;
import Fragnito.entities.StatoDistributore;
import Fragnito.entities.TipoDistributore;
import Fragnito.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class DistributoriDAO {
    private final EntityManager em;

    public DistributoriDAO(EntityManager em) {
        this.em = em;
    }

    public void generateNDistributors(int n) {
        for (int i = 0; i < n; i++) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            TipoDistributore randomTipo = TipoDistributore.randomTipoDistributore();
            if (randomTipo == TipoDistributore.DISTRIBUTORE_AUTOMATICO)
                em.persist(new Distributore(randomTipo, StatoDistributore.ATTIVO));
            else em.persist(new Distributore(randomTipo, null));
            transaction.commit();
            System.out.println("Distributore aggiunto con successo!");
        }
    }

    public void saveDistributore(TipoDistributore tipo, StatoDistributore stato) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(new Distributore(tipo, stato));
        transaction.commit();
        System.out.println("Distributore aggiunto con successo!");
    }

    public Distributore getDistributoreById(UUID id) {
        Distributore found = em.find(Distributore.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void deleteDistributore(UUID id) {
        Distributore found = getDistributoreById(id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("Distributore eliminato con successo!");
    }
}
