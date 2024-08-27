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
    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;
    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    public Tessera() {
    }

    public Tessera(Utente utente, LocalDate dataEmissione) {
        this.utente = utente;
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataEmissione.plusMonths(12);
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

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", utente=" + utente +
                ", biglietti=" + biglietti +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                '}';
    }
}

