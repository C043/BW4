package Fragnito.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tessere")
public class Tessera {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "utente_id") //FK
    private Utente utente;

    private LocalDate dataScadenza;

    public Tessera() {
    }

    public Tessera(UUID id, Utente utente, LocalDate dataScadenza) {
        this.id = id;
        this.utente = utente;
        this.dataScadenza = dataScadenza;
    }

    public UUID getId() {
        return id;
    }


    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
}
