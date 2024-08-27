package Fragnito.dao;


import Fragnito.entities.Utente;
import Fragnito.exceptions.NotFoundException;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class UtenteDAO {

    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }


    public List<Utente> generaUtenti(int n) {
        Faker faker = new Faker(new Locale("it"));

        List<Utente> utenti = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Date date = faker.date().birthday(1, 20);
            LocalDate randomLocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Utente newUtente = new Utente(faker.name().firstName(), faker.name().lastName(), randomLocalDate);

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(newUtente);
            transaction.commit();
            System.out.println("L'utente " + newUtente.getCognome() + " è stato salvato con successo!");

            utenti.add(newUtente);
        }


        return utenti;
    }

   /* public void save(Utente utente){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.
    }*/

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

}
