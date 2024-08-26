package Fragnito.entities;

import Fragnito.enumClass.PeriodoAbbonamento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Abbonamento")
public class Abbonamento extends Timbrabile {
    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @Column(name = "periodo_abbonamento")
    @Enumerated(EnumType.STRING)
    private PeriodoAbbonamento periodoAbbonamento;

    public Abbonamento() {
    }

    public Abbonamento(LocalDate dataEmissione, Distributore distributore, LocalDate dataScadenza, PeriodoAbbonamento periodoAbbonamento) {
        super(dataEmissione, distributore);
        this.dataScadenza = dataScadenza;
        this.periodoAbbonamento = periodoAbbonamento;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public PeriodoAbbonamento getPeriodoAbbonamento() {
        return periodoAbbonamento;
    }

    public void setPeriodoAbbonamento(PeriodoAbbonamento periodoAbbonamento) {
        this.periodoAbbonamento = periodoAbbonamento;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "dataScadenza=" + dataScadenza +
                ", periodoAbbonamento=" + periodoAbbonamento +
                "} " + super.toString();
    }
}
