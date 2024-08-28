package Fragnito.dao;


import Fragnito.entities.Utente;
import Fragnito.exceptions.NotFoundException;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class UtenteDAO {

    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }


    public void generaUtenti(int n) {
        Faker faker = new Faker(new Locale("it"));

        for (int i = 0; i < n; i++) {
            Date date = faker.date().birthday(1, 20);
            LocalDate randomLocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Utente newUtente = new Utente(faker.name().firstName(), faker.name().lastName(), randomLocalDate, faker.internet().emailAddress(), faker.internet().password());
            save(newUtente);
            System.out.println("L'utente " + newUtente.getCognome() + " è stato salvato con successo!");
        }
    }

    public void save(Utente utente) {
        TesseraDAO td = new TesseraDAO(em);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(utente);
        transaction.commit();

        td.save(utente.getId());

        System.out.println("Utente e tessera creati con successo!");
    }

    public Utente findById(UUID id) {
        Utente found = em.find(Utente.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void findByIdAndDelete(UUID id) {
        Utente found = this.findById(id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("L'utente " + found.getNome() + " " + found.getCognome() + " è stato eliminato con successo!");
    }

    public Utente login(String email, String password) {
        return em.createQuery("SELECT u FROM Utente u WHERE u.email = :email AND u.password = :password", Utente.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }
}
