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
    // Aggiungere relazione a viaggi

}
