package Fragnito.dao;

import Fragnito.entities.Distributore;
import Fragnito.enumClass.StatoDistributore;
import Fragnito.enumClass.TipoDistributore;
import Fragnito.exceptions.NotFoundException;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.UUID;

public class DistributoriDAO {
    private final EntityManager em;

    public DistributoriDAO(EntityManager em) {
        this.em = em;
    }

    public void generateNDistributors(int n) {
        Faker faker = new Faker();
        for (int i = 0; i < n; i++) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            TipoDistributore randomTipo = TipoDistributore.randomTipoDistributore();
            if (randomTipo == TipoDistributore.DISTRIBUTORE_AUTOMATICO)
                em.persist(new Distributore(faker.address().fullAddress(), randomTipo, StatoDistributore.ATTIVO));
            else em.persist(new Distributore(faker.address().fullAddress(), randomTipo, null));
            transaction.commit();
            System.out.println("Distributore aggiunto con successo!");
        }
    }

    public void saveDistributore(Distributore distributore) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(distributore);
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

    public void updateStatoDistributore(UUID id, StatoDistributore nuovoStato) {
        Distributore distributore = getDistributoreById(id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        distributore.setStato(nuovoStato);
        em.merge(distributore);
        transaction.commit();
        System.out.println("Stato del distributore aggiornato con successo!");
    }

    public List<Distributore> getAllDistributors() {
        return em.createQuery("SELECT d FROM Distributore d WHERE d.stato = :stato OR d.stato IS NULL", Distributore.class)
                .setParameter("stato", StatoDistributore.ATTIVO)
                .getResultList();
    }
}
