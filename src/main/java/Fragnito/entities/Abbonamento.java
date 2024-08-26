package Fragnito.entities;

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
}
