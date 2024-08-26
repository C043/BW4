package Fragnito.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Biglietto")
public class Biglietto extends Timbrabile {
    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;
    // Aggiungere relazione a viaggi e fare getter e setter e rifare toString() e constructor

    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, Distributore distributore, LocalDate dataVidimazione) {
        super(dataEmissione, distributore);
        this.dataVidimazione = dataVidimazione;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "dataVidimazione=" + dataVidimazione +
                "} " + super.toString();
    }
}
