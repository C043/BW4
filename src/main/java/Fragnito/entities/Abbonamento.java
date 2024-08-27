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

    public Abbonamento(Distributore distributore, Tessera tessera, PeriodoAbbonamento periodoAbbonamento) {
        super(distributore, tessera);
        this.periodoAbbonamento = periodoAbbonamento;
        switch (periodoAbbonamento) {
            case MENSILE -> this.dataScadenza = LocalDate.now().plusMonths(1);
            case SETTIMANALE -> this.dataScadenza = LocalDate.now().plusWeeks(1);
        }
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
