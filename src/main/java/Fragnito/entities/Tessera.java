package Fragnito.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tessere")
public class Tessera {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "utente_id", nullable = false, unique = true) //FK
    private Utente utente;

    @OneToMany(mappedBy = "tessera")
    private List<Timbrabile> biglietti = new ArrayList<>();

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

    public List<Timbrabile> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Timbrabile> biglietti) {
        this.biglietti = biglietti;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", utente=" + utente +
                ", biglietti=" + biglietti +
                ", dataScadenza=" + dataScadenza +
                '}';
    }
}
