package Fragnito.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "biglietti_abbonamenti")
@DiscriminatorColumn(name = "tipo_biglietto")
public abstract class Timbrabile {
    @Id
    @GeneratedValue
    private UUID id;

    // Creare il many to one alle tessere e rifare i getter setter i toString e i constructor
    @ManyToOne
    @JoinColumn(name = "tessera_id", nullable = false)
    private Tessera tessera;

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

    @ManyToOne
    @JoinColumn(name = "distributore_id")
    private Distributore distributore;

    public Timbrabile() {
    }

    public Timbrabile(Distributore distributore, Tessera tessera) {
        this.dataEmissione = LocalDate.now();
        this.distributore = distributore;
        this.tessera = tessera;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public Distributore getDistributore() {
        return distributore;
    }

    public void setDistributore(Distributore distributore) {
        this.distributore = distributore;
    }

    @Override
    public String toString() {
        return "Timbrabile: " +
                "id = " + id +
                ", tessera = " + tessera.getUtente() +
                ", data emissione = " + dataEmissione +
                ", distributore = " + distributore;
    }
}
