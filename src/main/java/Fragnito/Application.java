package Fragnito;

import Fragnito.dao.*;
import Fragnito.entities.Mezzo;
import Fragnito.entities.Tratta;
import Fragnito.entities.Viaggio;
import Fragnito.enumClass.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4-Team-5");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        DistributoriDAO dd = new DistributoriDAO(em);
        UtenteDAO ud = new UtenteDAO(em);
        TesseraDAO td = new TesseraDAO(em);
        MezziDAO md = new MezziDAO(em);
        TrattaDAO trd = new TrattaDAO(em);
        ManutenzioneDAO mand = new ManutenzioneDAO(em);
        BigliettiDAO bd = new BigliettiDAO(em);
        ViaggiDAO vd = new ViaggiDAO(em);


        Tratta nuovaTratta = new Tratta("Porta Venezia", "Piazza SanBabila", 46);
        trd.save(nuovaTratta);


        Mezzo nuovoMezzo = new Mezzo(TipoMezzo.BUS, nuovaTratta);
        md.save(nuovoMezzo);


        LocalDate oggi = LocalDate.now();
        for (int i = 1; i <= 3; i++) {
            Viaggio viaggio = new Viaggio(nuovoMezzo, oggi.plusDays(i), 50 + i);
            vd.save(viaggio);
            md.incrementaNumeroGiri(nuovoMezzo.getId());
            md.aggiornaMediaTempoEffettivo(nuovoMezzo.getId(), viaggio.getTempoEffettivo());
        }
        Mezzo mezzoAggiornato = md.findById(nuovoMezzo.getId());
        System.out.println("Numero di giri effettuati dal mezzo: " + mezzoAggiornato.getNumeroGiri());
        System.out.println("Media del tempo effettivo per il mezzo: " + mezzoAggiornato.getMediaTempoEffettivo());

        em.close();
        emf.close();
    }
}
