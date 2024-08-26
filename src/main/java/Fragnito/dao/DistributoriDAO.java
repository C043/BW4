package Fragnito.dao;

import Fragnito.entities.Distributore;
import Fragnito.entities.StatoDistributore;
import Fragnito.entities.TipoDistributore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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
}
