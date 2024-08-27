package Fragnito.dao;

import Fragnito.entities.Tessera;
import Fragnito.entities.Utente;
import Fragnito.exceptions.NotFoundException;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class TesseraDAO {

    private final EntityManager em;
    Faker faker = new Faker(new Locale("it"));

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void save(UUID id) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            UtenteDAO ud = new UtenteDAO(em);
            Utente tesseraOwner = ud.findById(id);
            Tessera tessera = new Tessera(tesseraOwner, LocalDate.now());
            em.persist(tessera);
            transaction.commit();
            System.out.println("La tessera " + tessera.getId() + " appartenente a " + tessera.getUtente().getCognome() + " è stata salvata con successo!");
        } catch (NotFoundException e) {
            System.out.println(id);
        }
    }

    public Tessera findById(UUID id) {
        Tessera found = em.find(Tessera.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void findByIdAndDelete(UUID id) {
        Tessera found = this.findById(id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("La tessera " + found.getId() + " appartenente a " + found.getUtente().getCognome() + " è stata eliminata con successo!");
    }


    public void rinnovaTessera(Tessera tessera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query updateQuery = em.createQuery("UPDATE Tessera t SET t.dataEmissione = :dataEmissione, t.dataScadenza = :dataScadenza WHERE t.id = :id")
                .setParameter("dataEmissione", tessera.getDataEmissione()).setParameter("dataScadenza", LocalDate.now().plusYears(1)).setParameter("id", tessera.getId());
        int numModifiche = updateQuery.executeUpdate();
        transaction.commit();
        if (numModifiche < 1) System.out.println("Tessera non valida!");
        else System.out.println("Tessera rinnovata con successo!");
    }


    public List<Tessera> associaUtente(List<Utente> utenti) {

        List<Tessera> tessere = new ArrayList<>();


        for (int i = 0; i < utenti.size(); i++) {
            Utente utente = utenti.get(i);
            Date date = faker.date().birthday(1, 20);
            LocalDate dataEmissione = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Tessera tessera = new Tessera(utente, dataEmissione);
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(tessera);
            transaction.commit();
            tessere.add(tessera);
            System.out.println(tessera);
        }

        return tessere;
    }

}
