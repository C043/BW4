package Fragnito.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Biglietto")
public class Biglietto extends Timbrabile {
    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;

    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    public Biglietto() {
    }

    public Biglietto(Distributore distributore, Tessera tessera) {
        super(distributore, tessera);
    }

    public Viaggio getViaggio() {
        return viaggio;
    }

    public void setViaggio(Viaggio viaggio) {
        this.viaggio = viaggio;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    @Override
    public String toString() {
        return "Biglietto: " +
                "data vidimazione =" + dataVidimazione +
                ", viaggio =" + viaggio +
                " " + super.toString();
    }
}
